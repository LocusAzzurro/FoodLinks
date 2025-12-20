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

    // AGI
    public static final RegistryObject<Item> EFFECTOR_SPEED_1 = registerItem("effector_speed_1", () -> EffectorItem.withEffect(MobEffects.MOVEMENT_SPEED, 0));
    public static final RegistryObject<Item> EFFECTOR_SPEED_2 = registerItem("effector_speed_2", () -> EffectorItem.withEffect(MobEffects.MOVEMENT_SPEED, 1));
    public static final RegistryObject<Item> EFFECTOR_JUMP_1 = registerItem("effector_jump_1", () -> EffectorItem.withEffect(MobEffects.JUMP, 0));
    public static final RegistryObject<Item> EFFECTOR_JUMP_2 = registerItem("effector_jump_2", () -> EffectorItem.withEffect(MobEffects.JUMP, 1));
    public static final RegistryObject<Item> EFFECTOR_SLOWNESS_1 = registerItem("effector_slowness_1", () -> EffectorItem.withEffect(MobEffects.MOVEMENT_SLOWDOWN, 0));
    public static final RegistryObject<Item> EFFECTOR_SLOWNESS_2 = registerItem("effector_slowness_2", () -> EffectorItem.withEffect(MobEffects.MOVEMENT_SLOWDOWN, 1));

    // STR
    public static final RegistryObject<Item> EFFECTOR_STRENGTH_1 = registerItem("effector_strength_1", () -> EffectorItem.withEffect(MobEffects.DAMAGE_BOOST, 0));
    public static final RegistryObject<Item> EFFECTOR_STRENGTH_2 = registerItem("effector_strength_2", () -> EffectorItem.withEffect(MobEffects.DAMAGE_BOOST, 1));
    public static final RegistryObject<Item> EFFECTOR_WEAKNESS_1 = registerItem("effector_weakness_1", () -> EffectorItem.withEffect(MobEffects.WEAKNESS, 0));
    public static final RegistryObject<Item> EFFECTOR_WEAKNESS_2 = registerItem("effector_weakness_2", () -> EffectorItem.withEffect(MobEffects.WEAKNESS, 1));
    public static final RegistryObject<Item> EFFECTOR_HASTE_1 = registerItem("effector_haste_1", () -> EffectorItem.withEffect(MobEffects.DIG_SPEED, 0));
    public static final RegistryObject<Item> EFFECTOR_HASTE_2 = registerItem("effector_haste_2", () -> EffectorItem.withEffect(MobEffects.DIG_SPEED, 1));
    public static final RegistryObject<Item> EFFECTOR_FATIGUE_1 = registerItem("effector_fatigue_1", () -> EffectorItem.withEffect(MobEffects.DIG_SLOWDOWN, 0));
    public static final RegistryObject<Item> EFFECTOR_FATIGUE_2 = registerItem("effector_fatigue_2", () -> EffectorItem.withEffect(MobEffects.DIG_SLOWDOWN, 1));

    // DEF
    public static final RegistryObject<Item> EFFECTOR_RESISTANCE_1 = registerItem("effector_resistance_1", () -> EffectorItem.withEffect(MobEffects.DAMAGE_RESISTANCE, 0));
    public static final RegistryObject<Item> EFFECTOR_RESISTANCE_2 = registerItem("effector_resistance_2", () -> EffectorItem.withEffect(MobEffects.DAMAGE_RESISTANCE, 1));
    public static final RegistryObject<Item> EFFECTOR_REGENERATION_1 = registerItem("effector_regeneration_1", () -> EffectorItem.withEffect(MobEffects.REGENERATION, 0));
    public static final RegistryObject<Item> EFFECTOR_REGENERATION_2 = registerItem("effector_regeneration_2", () -> EffectorItem.withEffect(MobEffects.REGENERATION, 1));
    public static final RegistryObject<Item> EFFECTOR_HEALTH_1 = registerItem("effector_health_1", () -> EffectorItem.withEffect(MobEffects.HEALTH_BOOST, 0));
    public static final RegistryObject<Item> EFFECTOR_HEALTH_2 = registerItem("effector_health_2", () -> EffectorItem.withEffect(MobEffects.HEALTH_BOOST, 1));

    // UTI
    public static final RegistryObject<Item> EFFECTOR_NIGHT_VISION = registerItem("effector_night_vision", () -> EffectorItem.withEffects(MobEffects.NIGHT_VISION));
    public static final RegistryObject<Item> EFFECTOR_WATER_BREATHING = registerItem("effector_water_breathing", () -> EffectorItem.withEffects(MobEffects.WATER_BREATHING));
    public static final RegistryObject<Item> EFFECTOR_FIRE_RESISTANCE = registerItem("effector_fire_resistance", () -> EffectorItem.withEffects(MobEffects.FIRE_RESISTANCE));

    private static RegistryObject<Item> registerItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

}
