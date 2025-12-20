package org.mineplugin.locusazzurro.foodlinks.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.mineplugin.locusazzurro.foodlinks.client.ActiveFoodsOverlay;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerGui(RegisterGuiOverlaysEvent event){
        event.registerAboveAll("active_foods", ActiveFoodsOverlay.GUI_OVERLAY);
    }

}
