package org.mineplugin.locusazzurro.foodlinks.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.mineplugin.locusazzurro.foodlinks.capability.PlayerActiveFoodsProvider;
import org.mineplugin.locusazzurro.foodlinks.food.PlayerActiveFoods;
import org.mineplugin.locusazzurro.foodlinks.network.ActiveFoodsS2CPacket;
import org.mineplugin.locusazzurro.foodlinks.network.ModPacketHandler;

public class ClearFoodListItem extends Item {

    public ClearFoodListItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getCapability(PlayerActiveFoodsProvider.PLAYER_ACTIVE_FOODS).ifPresent(cap -> {
            cap.clearFoodList();
            cap.clearEffects();
            if (!pLevel.isClientSide) ModPacketHandler.sendToPlayer(new ActiveFoodsS2CPacket(cap.getFoods()), (ServerPlayer) pPlayer);
        });
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
