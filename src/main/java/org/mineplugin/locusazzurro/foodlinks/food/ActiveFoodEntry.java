package org.mineplugin.locusazzurro.foodlinks.food;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class ActiveFoodEntry implements Comparable<ActiveFoodEntry>{

    private int charges;
    private final Item foodItem;

    public ActiveFoodEntry(Item foodItem, int charges){
        this.charges = charges;
        this.foodItem = foodItem;
    }

    public void tickCharge(){
        this.charges--;
    }

    public int getCharges() {
        return charges;
    }

    public void addCharges(int charges){
        this.charges += charges;
    }

    public Item getFoodItem() {
        return foodItem;
    }

    @Override
    public int compareTo(@NotNull ActiveFoodEntry o) {
        return Integer.compare(charges, o.charges);
    }
}
