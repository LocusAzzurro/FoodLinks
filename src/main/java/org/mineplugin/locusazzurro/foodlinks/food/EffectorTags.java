package org.mineplugin.locusazzurro.foodlinks.food;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;

@SuppressWarnings("unused")
public class EffectorTags {

    // AGI
    public static final TagKey<Item> SPEED_EFFECTORS = createTag("speed_effectors");
    public static final TagKey<Item> JUMP_EFFECTORS = createTag("jump_effectors");
    public static final TagKey<Item> SLOWNESS_EFFECTORS = createTag("slowness_effectors");

    // STR
    public static final TagKey<Item> STRENGTH_EFFECTORS_1 = createTag("strength_effectors_1");
    public static final TagKey<Item> STRENGTH_EFFECTORS_2 = createTag("strength_effectors_2");
    public static final TagKey<Item> WEAKNESS_EFFECTORS_1 = createTag("weakness_effectors_1");
    public static final TagKey<Item> WEAKNESS_EFFECTORS_2 = createTag("weakness_effectors_2");
    public static final TagKey<Item> HASTE_EFFECTORS_1 = createTag("haste_effectors_1");
    public static final TagKey<Item> HASTE_EFFECTORS_2 = createTag("haste_effectors_2");
    public static final TagKey<Item> FATIGUE_EFFECTORS_1 = createTag("fatigue_effectors_1");
    public static final TagKey<Item> FATIGUE_EFFECTORS_2 = createTag("fatigue_effectors_2");

    // DEF
    public static final TagKey<Item> RESISTANCE_EFFECTORS_1 = createTag("resistance_effectors_1");
    public static final TagKey<Item> RESISTANCE_EFFECTORS_2 = createTag("resistance_effectors_2");
    public static final TagKey<Item> REGENERATION_EFFECTORS_1 = createTag("regeneration_effectors_1");
    public static final TagKey<Item> REGENERATION_EFFECTORS_2 = createTag("regeneration_effectors_2");
    public static final TagKey<Item> HEALTH_EFFECTORS_1 = createTag("health_effectors_1");
    public static final TagKey<Item> HEALTH_EFFECTORS_2 = createTag("health_effectors_2");

    // UTI
    public static final TagKey<Item> NIGHT_VISION_EFFECTORS = createTag("night_vision_effectors");
    public static final TagKey<Item> WATER_BREATHING_EFFECTORS = createTag("water_breathing_effectors");
    public static final TagKey<Item> FIRE_RESISTANCE_EFFECTORS = createTag("fire_resistance_effectors");

    private static TagKey<Item> createTag(String path){
        return ItemTags.create(new ResourceLocation(FoodLinks.MODID, path));
    }

}
