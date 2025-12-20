package org.mineplugin.locusazzurro.foodlinks.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.foodlinks.food.PlayerActiveFoods;
import org.mineplugin.locusazzurro.foodlinks.registry.ItemRegistry;
import org.mineplugin.locusazzurro.foodlinks.registry.RecipeRegistry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EffectorRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    protected final String group;
    protected List<Ingredient> ingredients;
    protected final ItemStack result;
    protected final boolean isSimple;

    private static final Ingredient ING_ANY = Ingredient.of(ItemRegistry.WILDCARD_ITEM.get());
    private static final Predicate<ItemStack> ANY = i -> true;
    private static final List<Ingredient> BLANK_CRITERIA_ING = List.of(ING_ANY, ING_ANY, ING_ANY, ING_ANY, ING_ANY);
    private static final List<Predicate<ItemStack>> BLANK_CRITERIA = List.of(ANY, ANY, ANY, ANY, ANY);
    private static final List<ItemStack> BLANK_ITEMS = List.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
    private static final int MAX_SLOTS = PlayerActiveFoods.MAX_COUNT;

    public EffectorRecipe(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.group = pGroup;
        this.ingredients = new ArrayList<>(BLANK_CRITERIA_ING);
        for (int i = 0; i < pIngredients.size(); i++){
            this.ingredients.set(i, pIngredients.get(i));
        }
        this.result = pResult;
        this.isSimple = pIngredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {

        var input = new ArrayList<>(BLANK_ITEMS);
        var checks = new ArrayList<>(BLANK_CRITERIA);

        for (int i = 0; i < pContainer.getContainerSize(); i++){
            input.set(i, pContainer.getItem(i));
        }

        for (int j = 0; j < MAX_SLOTS; j++){
            checks.set(j, predicateFromIngredient(this.ingredients.get(j)));
        }

        return RecipeMatcher.findMatches(input, checks) != null;
    }

    private Predicate<ItemStack> predicateFromIngredient(Ingredient ingredient){
        return itemStack -> {
            if (ingredient.isEmpty())
                return false;
            if (Arrays.stream(ingredient.getItems()).anyMatch(i -> i.is(ItemRegistry.WILDCARD_ITEM.get())))
                return true;
            else return ingredient.test(itemStack);
        };
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.EFFECTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.EFFECTOR.get();
    }

    public static class Serializer implements RecipeSerializer<EffectorRecipe>{

        public static final Serializer INSTANCE = new Serializer();

        protected Serializer() {}

        @Override
        public EffectorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            if (!pJson.has("result"))
                throw new JsonSyntaxException("Missing result, expected to find a string or object");

            String group = GsonHelper.getAsString(pJson, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "items"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for effector recipe");
            } else if (nonnulllist.size() > PlayerActiveFoods.MAX_COUNT) {
                throw new JsonParseException("Too many ingredients, the max is " + PlayerActiveFoods.MAX_COUNT);
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
                return new EffectorRecipe(pRecipeId, group, itemstack, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i), false);
                nonnulllist.add(ingredient);
            }
            return nonnulllist;
        }

        @Override
        public @Nullable EffectorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            nonnulllist.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

            ItemStack itemstack = pBuffer.readItem();
            return new EffectorRecipe(pRecipeId, s, itemstack, nonnulllist);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, EffectorRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeVarInt(pRecipe.ingredients.size());

            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
