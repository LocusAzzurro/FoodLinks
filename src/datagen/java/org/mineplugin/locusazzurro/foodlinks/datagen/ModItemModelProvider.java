package org.mineplugin.locusazzurro.foodlinks.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;
import org.mineplugin.locusazzurro.foodlinks.registry.ItemRegistry;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FoodLinks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemRegistry.ITEMS.getEntries().stream()
                .forEach(this::generatedItem);
    }

    private ItemModelBuilder generatedItem(RegistryObject<Item> item){
        return singleTexture(name(item.get()), mcLoc("item/generated"), "layer0", modLoc("item/" + name(item.get())));
    }

    private static String name(Item item){
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

}
