package org.mineplugin.locusazzurro.foodlinks.datagen;

import net.minecraft.data.PackOutput;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FoodLinks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEntrypoint {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fh = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, fh));
    }
}