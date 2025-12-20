package org.mineplugin.locusazzurro.foodlinks.network;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.mineplugin.locusazzurro.foodlinks.client.ClientActiveFoodsData;
import org.mineplugin.locusazzurro.foodlinks.food.ActiveFoodEntry;

import java.util.List;
import java.util.function.Supplier;

public class ActiveFoodsS2CPacket {

    private final List<ActiveFoodEntry> foods;

    ActiveFoodsS2CPacket(FriendlyByteBuf buf) {
        this.foods = buf.readCollection(NonNullList::createWithCapacity, ActiveFoodEntry::readFromBuffer);
    }

    public ActiveFoodsS2CPacket(List<ActiveFoodEntry> foods) {
        this.foods = foods;
    }

    void encode(FriendlyByteBuf buf) {
        buf.writeCollection(this.foods, ActiveFoodEntry::writeToBuffer);
    }

    void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientActiveFoodsData.setFoods(foods)));
    }
}
