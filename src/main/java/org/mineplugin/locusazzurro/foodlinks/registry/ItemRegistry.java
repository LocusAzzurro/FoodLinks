package org.mineplugin.locusazzurro.foodlinks.registry;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;
import org.mineplugin.locusazzurro.foodlinks.item.ClearFoodListItem;
import org.mineplugin.locusazzurro.foodlinks.item.EffectorItem;

import java.util.function.Supplier;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FoodLinks.MODID);

    public static final RegistryObject<Item> DEBUG_CLEAR_SLOTS = registerItem("clear_slots", ClearFoodListItem::new);
    public static final RegistryObject<Item> WILDCARD_ITEM = registerItem("wildcard");
    public static final RegistryObject<Item> EFFECTOR_SPEED = registerItem("effector_speed", () -> EffectorItem.withEffects(MobEffects.MOVEMENT_SPEED));
    public static final RegistryObject<Item> EFFECTOR_HASTE = registerItem("effector_haste", () -> EffectorItem.withEffects(MobEffects.DIG_SPEED));
    public static final RegistryObject<Item> EFFECTOR_REGENERATION = registerItem("effector_regeneration", () -> EffectorItem.withEffects(MobEffects.REGENERATION));
    public static final RegistryObject<Item> EFFECTOR_STRENGTH = registerItem("effector_strength", () -> EffectorItem.withEffects(MobEffects.DAMAGE_BOOST));
    public static final RegistryObject<Item> EFFECTOR_RESISTANCE = registerItem("effector_resistance", () -> EffectorItem.withEffects(MobEffects.DAMAGE_RESISTANCE));
    public static final RegistryObject<Item> EFFECTOR_NIGHT_VISION = registerItem("effector_night_vision", () -> EffectorItem.withEffects(MobEffects.NIGHT_VISION));
    public static final RegistryObject<Item> EFFECTOR_WATER_BREATHING = registerItem("effector_water_breathing", () -> EffectorItem.withEffects(MobEffects.WATER_BREATHING));

    private static RegistryObject<Item> registerItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

}
