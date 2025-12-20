package org.mineplugin.locusazzurro.foodlinks.food;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public void addCharges(int charges){
        this.charges += charges;
    }

    public Item getFoodItem() {
        return foodItem;
    }

    public static ActiveFoodEntry readFromBuffer(FriendlyByteBuf buf){
        ItemStack itemStack = buf.readItem();
        int charges = buf.readInt();
        return new ActiveFoodEntry(itemStack.getItem(), charges);
    }

    public static void writeToBuffer(FriendlyByteBuf buf, ActiveFoodEntry foodEntry){
        buf.writeItem(foodEntry.foodItem.getDefaultInstance());
        buf.writeInt(foodEntry.charges);
    }

    @Override
    public int compareTo(@NotNull ActiveFoodEntry o) {
        return Integer.compare(charges, o.charges);
    }
}
