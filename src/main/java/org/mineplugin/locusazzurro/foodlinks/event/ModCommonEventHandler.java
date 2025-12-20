package org.mineplugin.locusazzurro.foodlinks.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.mineplugin.locusazzurro.foodlinks.network.ModPacketHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEventHandler {

    @SubscribeEvent
    public static void registerPacketHandlers(FMLCommonSetupEvent event) {
        event.enqueueWork(ModPacketHandler::register);
    }
}
