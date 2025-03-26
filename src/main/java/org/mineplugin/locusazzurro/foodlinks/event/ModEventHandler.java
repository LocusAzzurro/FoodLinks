package org.mineplugin.locusazzurro.foodlinks.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;
import org.mineplugin.locusazzurro.foodlinks.food.PlayerActiveFoods;
import org.mineplugin.locusazzurro.foodlinks.capability.PlayerActiveFoodsProvider;


@Mod.EventBusSubscriber(modid = FoodLinks.MODID)
public class ModEventHandler {

    public static final ResourceLocation ACTIVE_FOODS_RL = new ResourceLocation(FoodLinks.MODID, "active_foods");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof Player player){
            if (!player.getCapability(PlayerActiveFoodsProvider.PLAYER_ACTIVE_FOODS).isPresent()){
                event.addCapability(ACTIVE_FOODS_RL, new PlayerActiveFoodsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if (event.isWasDeath()){
            event.getOriginal().getCapability(PlayerActiveFoodsProvider.PLAYER_ACTIVE_FOODS).ifPresent(oldCap ->
                    event.getEntity().getCapability(PlayerActiveFoodsProvider.PLAYER_ACTIVE_FOODS).ifPresent(newCap ->
                            newCap.copyFrom(oldCap)));
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(PlayerActiveFoods.class);
    }

    @SubscribeEvent
    public static void powerHeal(LivingHealEvent event){
        if (event.getEntity() instanceof Player pPlayer){
            boolean naturalRegen = pPlayer.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
            if (!naturalRegen) return;
            float maxHealth = pPlayer.getMaxHealth();
            if (maxHealth < 20) return;
            float healAmount = event.getAmount();
            float healBonus = maxHealth / 20;

            FoodData foodData = pPlayer.getFoodData();
            float saturationLevel = foodData.getSaturationLevel();
            int foodLevel = foodData.getFoodLevel();

            if (saturationLevel > 0.0F && pPlayer.isHurt() && foodLevel >= 20) {
                float f = Math.min(saturationLevel, 6.0F);
                healAmount = (f / 6.0F) * healBonus;
                foodData.addExhaustion(f);
            } else if (foodLevel >= 18 && pPlayer.isHurt()) {
                healAmount = healBonus;
                foodData.addExhaustion(6.0F);
            }

            event.setAmount(healAmount);
        }
    }


}
