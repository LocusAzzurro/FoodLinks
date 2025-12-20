package org.mineplugin.locusazzurro.foodlinks.client;

import net.minecraft.world.item.ItemStack;
import org.mineplugin.locusazzurro.foodlinks.food.EffectorTags;

public record EffectorColors(int agiColor, int strColor, int defColor, int utiColor) {

    public static EffectorColors itemColors(ItemStack item){
        int agiColor = 0, strColor = 0, defColor = 0, utiColor = 0;
        if (item.is(EffectorTags.SPEED_EFFECTORS)){
            agiColor = 0xFF33EBFF;
        }
        if (item.is(EffectorTags.JUMP_EFFECTORS)){
            agiColor = 0xFFFDFF84;
        }
        if (item.is(EffectorTags.SLOWNESS_EFFECTORS)){
            agiColor = 0xFF8BAFE0;
        }

        if (item.is(EffectorTags.STRENGTH_EFFECTORS_1)){
            strColor = 0xFFFFD700;
        }
        if (item.is(EffectorTags.WEAKNESS_EFFECTORS_1)){
            strColor = 0xFF484D48;
        }
        if (item.is(EffectorTags.HASTE_EFFECTORS_1)){
            strColor = 0xFFD9C043;
        }
        if (item.is(EffectorTags.FATIGUE_EFFECTORS_1)){
            strColor = 0xFF4A4217;
        }

        if (item.is(EffectorTags.RESISTANCE_EFFECTORS_1)){
            defColor = 0xFFCD5CAB;
        }
        if (item.is(EffectorTags.REGENERATION_EFFECTORS_1)){
            defColor = 0xFF8f45ed;
        }
        if (item.is(EffectorTags.HEALTH_EFFECTORS_1)){
            defColor = 0xFFF87D23;
        }

        if (item.is(EffectorTags.NIGHT_VISION_EFFECTORS)){
            utiColor = 0xFFC2FF66;
        }
        if (item.is(EffectorTags.WATER_BREATHING_EFFECTORS)){
            utiColor = 0xFF98DAC0;
        }
        if (item.is(EffectorTags.FIRE_RESISTANCE_EFFECTORS)){
            utiColor = 0xFFFF9900;
        }
        return new EffectorColors(agiColor, strColor, defColor, utiColor);
    }


}
