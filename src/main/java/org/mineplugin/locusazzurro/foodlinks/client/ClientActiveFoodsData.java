package org.mineplugin.locusazzurro.foodlinks.client;

import org.mineplugin.locusazzurro.foodlinks.food.ActiveFoodEntry;

import java.util.List;

public class ClientActiveFoodsData {

    private static List<ActiveFoodEntry> foods = List.of();

    public static List<ActiveFoodEntry> getFoods() {
        return foods;
    }

    public static void setFoods(List<ActiveFoodEntry> foods) {
        ClientActiveFoodsData.foods = foods;
    }
}
