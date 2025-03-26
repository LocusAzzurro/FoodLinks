package org.mineplugin.locusazzurro.foodlinks.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;
import org.mineplugin.locusazzurro.foodlinks.recipe.EffectorRecipe;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, FoodLinks.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FoodLinks.MODID);

    public static RegistryObject<RecipeType<EffectorRecipe>> EFFECTOR = register("effector");
    public static final RegistryObject<RecipeSerializer<EffectorRecipe>> EFFECTOR_SERIALIZER =
            RECIPE_SERIALIZERS.register("effector", () -> EffectorRecipe.Serializer.INSTANCE);

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name) {
        return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return new ResourceLocation(FoodLinks.MODID, name).toString();
            }
        });
    }


}
