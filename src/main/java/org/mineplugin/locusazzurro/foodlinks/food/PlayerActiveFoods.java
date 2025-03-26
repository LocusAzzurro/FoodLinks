package org.mineplugin.locusazzurro.foodlinks.food;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.mineplugin.locusazzurro.foodlinks.capability.PlayerActiveFoodsProvider;
import org.mineplugin.locusazzurro.foodlinks.item.EffectorItem;
import org.mineplugin.locusazzurro.foodlinks.recipe.EffectorRecipe;
import org.mineplugin.locusazzurro.foodlinks.registry.ItemRegistry;
import org.mineplugin.locusazzurro.foodlinks.registry.RecipeRegistry;

import java.util.*;

@Mod.EventBusSubscriber
public class PlayerActiveFoods {

    private int slotCount;
    private List<ActiveFoodEntry> foods;
    private Set<Item> activeEffects;
    public static final int MIN_COUNT = 3;
    public static final int MAX_COUNT = 9;

    public PlayerActiveFoods(){
        slotCount = MIN_COUNT;
        foods = new ArrayList<>(MAX_COUNT);
        activeEffects = new HashSet<>(MAX_COUNT);
    }

    public int getSlotCount(){
        return slotCount;
    }

    public void addSlotCount(){
        this.slotCount = Math.min(MAX_COUNT, ++slotCount);
    }

    public void subSlotCount(){
        this.slotCount = Math.max(MIN_COUNT, --slotCount);
    }

    @SuppressWarnings("ConstantConditions")
    public void addFoodEntry(ItemStack item, Player player){
        Item addItem = item.getItem();
        int chargeValue = addItem.getFoodProperties(item, player).getNutrition() * 3;
        for (ActiveFoodEntry entry : this.foods){
            if (item.is(entry.getFoodItem())){
                entry.addCharges(chargeValue);
                return;
            }
        }
        if (foods.size() >= slotCount){
            Collections.sort(foods);
            while (foods.size() >= slotCount){
                foods.remove(0);
            }
        }
        foods.add(new ActiveFoodEntry(addItem, chargeValue));
        tickEffects(player, player.level());
    }

    public boolean addEffect(Item item, Player player){
        return this.activeEffects.add(item);
    }

    public boolean removeEffect(Item item, Player player){
        return this.activeEffects.remove(item);
    }

    public boolean hasFoodInList(){
        return !foods.isEmpty();
    }

    public void clearFoodList(){
        this.foods.clear();
    }

    public void clearEffects(){
        this.activeEffects.clear();
    }

    public void tickCharges(Player player, Level level) {
        if (!level.isClientSide()) {
            for (var itr = this.foods.iterator(); itr.hasNext(); ) {
                ActiveFoodEntry entry = itr.next();
                entry.tickCharge();
                if (entry.getCharges() <= 0) {
                    itr.remove();
                }
            }
        }
    }

    public void tickEffects(Player player, Level level) {
        if (!level.isClientSide()) {

            ItemStack[] itemStacks = new ItemStack[foods.size()];
            for (int i = 0; i < foods.size(); i++){
                itemStacks[i] = foods.get(i).getFoodItem().getDefaultInstance();
            }

            List<EffectorRecipe> recipes = level.getRecipeManager().getRecipesFor(RecipeRegistry.EFFECTOR.get(), new SimpleContainer(itemStacks), level);
            Set<Item> newEffectorItems = Set.copyOf(recipes.stream().map(recipe -> recipe.getResultItem(level.registryAccess()).getItem()).toList());

            boolean isEffectFade = !newEffectorItems.containsAll(activeEffects);
            boolean isEffectApply = !activeEffects.containsAll(newEffectorItems);

            if (isEffectFade) player.sendSystemMessage(Component.translatable("foodlinks.message.effect_fade"));
            if (isEffectApply) player.sendSystemMessage(Component.translatable("foodlinks.message.effect_apply"));

            this.activeEffects = newEffectorItems;

            this.activeEffects.forEach(effect -> {
                if (effect instanceof EffectorItem effectorItem) effectorItem.applyEffect(player);
            });
        }
    }


    @SubscribeEvent
    public static void onConsumeFood(LivingEntityUseItemEvent.Finish event){
        ItemStack item = event.getItem();
        if (item.isEdible() && event.getEntity() instanceof Player player){
            player.getCapability(PlayerActiveFoodsProvider.PLAYER_ACTIVE_FOODS)
                    .ifPresent(cap -> {
                        cap.addFoodEntry(item, player);
                        cap.tickEffects(player, player.level());
                    });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        Level level = event.player.level();
        FoodData foodData = player.getFoodData();
        boolean foodChanged = foodData.getLastFoodLevel() > foodData.getFoodLevel();
        boolean effectTick = level.getGameTime() % 200 == 0;

        if (foodChanged || effectTick){
            player.getCapability(PlayerActiveFoodsProvider.PLAYER_ACTIVE_FOODS).ifPresent(cap -> {
                if (cap.hasFoodInList()){
                    cap.tickCharges(player, level);
                    if (effectTick)
                        cap.tickEffects(player, level);
                }
            });
        }
    }

    public void copyFrom(PlayerActiveFoods other){
        this.slotCount = other.slotCount;
        this.foods = List.copyOf(other.foods);
    }

    public void saveNBT(CompoundTag tag){
        tag.putInt("SlotCount", this.slotCount);
        ListTag listTag = new ListTag();
        for (ActiveFoodEntry food : this.foods){
            CompoundTag entryTag = new CompoundTag();
            entryTag.putString("Food", getItemName(food.getFoodItem()));
            entryTag.putInt("Charges", food.getCharges());
            listTag.add(entryTag);
        }
        tag.put("Foods", listTag);
        ListTag listTag2 = new ListTag();
        for (Item effector : activeEffects){
            StringTag name = StringTag.valueOf(getItemName(effector));
            listTag2.add(name);
        }
        tag.put("Effects", listTag2);
    }

    public void loadNBT(CompoundTag tag){
        this.slotCount = tag.getInt("SlotCount");
        ListTag foods = tag.getList("Foods", 10);
        List<ActiveFoodEntry> foodList = new ArrayList<>();
        for (int i = 0; i < foods.size(); i++){
            CompoundTag foodEntry = foods.getCompound(i);
            foodList.add(new ActiveFoodEntry(parseItem(foodEntry.getString("Food")), foodEntry.getInt("Charges")));
        }
        this.foods = foodList;
        ListTag effectList = tag.getList("Effects", 8);
        Set<Item> effectItems = new HashSet<>();
        for (int j = 0; j < effectList.size(); j++){
            Item item = parseItem(effectList.getString(j));
            effectItems.add(item);
        }
        this.activeEffects = effectItems;
    }

    private String getItemName(Item item){
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString();
    }

    private Item parseItem(String name){
        return ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(name));
    }

}
