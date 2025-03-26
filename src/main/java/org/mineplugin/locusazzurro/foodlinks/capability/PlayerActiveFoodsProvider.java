package org.mineplugin.locusazzurro.foodlinks.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.foodlinks.food.PlayerActiveFoods;

public class PlayerActiveFoodsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerActiveFoods> PLAYER_ACTIVE_FOODS = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerActiveFoods activeFoods = null;
    private final LazyOptional<PlayerActiveFoods> optional = LazyOptional.of(this::getActiveFoodsInstance);

    private PlayerActiveFoods getActiveFoodsInstance(){
        if (activeFoods == null)
            this.activeFoods = new PlayerActiveFoods();
        return activeFoods;
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(PLAYER_ACTIVE_FOODS))
            return optional.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getActiveFoodsInstance().saveNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getActiveFoodsInstance().loadNBT(nbt);
    }
}
