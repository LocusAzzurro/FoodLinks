package org.mineplugin.locusazzurro.foodlinks.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;

public class ModPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FoodLinks.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {

        INSTANCE.messageBuilder(ActiveFoodsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ActiveFoodsS2CPacket::new)
                .encoder(ActiveFoodsS2CPacket::encode)
                .consumerMainThread(ActiveFoodsS2CPacket::handle)
                .add();

    }

    public static <P> void sendToPlayer(P message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}