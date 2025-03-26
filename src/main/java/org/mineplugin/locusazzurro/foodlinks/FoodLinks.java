package org.mineplugin.locusazzurro.foodlinks;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mineplugin.locusazzurro.foodlinks.registry.ItemRegistry;
import org.mineplugin.locusazzurro.foodlinks.registry.RecipeRegistry;
import org.slf4j.Logger;

@Mod(FoodLinks.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoodLinks {
    public static final String MODID = "foodlinks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FoodLinks() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(bus);
        RecipeRegistry.RECIPE_TYPES.register(bus);
        RecipeRegistry.RECIPE_SERIALIZERS.register(bus);

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {

    }
}
