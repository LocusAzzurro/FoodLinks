package org.mineplugin.locusazzurro.foodlinks.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class EffectorItem extends Item {

    private static int DEFAULT_DURATION = 10 * 20;
    private Consumer<Player> effects;

    public EffectorItem(Consumer<Player> effects) {
        super(new Properties());
        this.effects = effects;
    }

    public void applyEffect(Player player){
        effects.accept(player);
    }

    public static EffectorItem withEffects(MobEffect... effects){
        return new EffectorItem(player -> {
            for (MobEffect effect : effects){
                player.addEffect(new MobEffectInstance(effect, DEFAULT_DURATION, 0, true, true));
            }
        });
    }

}
