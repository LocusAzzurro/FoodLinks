package org.mineplugin.locusazzurro.foodlinks.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.mineplugin.locusazzurro.foodlinks.FoodLinks;
import org.mineplugin.locusazzurro.foodlinks.food.ActiveFoodEntry;

import java.util.List;

public class ActiveFoodsOverlay {

    private static final ResourceLocation SLOT_EMPTY = new ResourceLocation(FoodLinks.MODID,"textures/gui/effector_slot_empty.png");
    private static final ResourceLocation SLOT_FILLED = new ResourceLocation(FoodLinks.MODID,"textures/gui/effector_slot_filled.png");
    private static final int SLOT_W = 6;
    private static final int SLOT_H = 16;

    public static final IGuiOverlay GUI_OVERLAY = (((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Font font = Minecraft.getInstance().font;
        List<ActiveFoodEntry> foods = ClientActiveFoodsData.getFoods();
        if (foods.isEmpty()) return;

        //starting location at corner
        int x = 32;
        int y = screenHeight - 8;

        RenderSystem.setShader(GameRenderer::getPositionShader);
        for (int i = 0; i < foods.size(); i++){
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            ItemStack item = foods.get(i).getFoodItem().getDefaultInstance();
            int yOffset = y - (i + 1) * 18;

            guiGraphics.renderFakeItem(item, x, yOffset);

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            guiGraphics.blit(SLOT_EMPTY, x + 18, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);
            guiGraphics.blit(SLOT_EMPTY, x + 18 + 7, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);
            guiGraphics.blit(SLOT_EMPTY, x + 18 + 14, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);
            guiGraphics.blit(SLOT_EMPTY, x + 18 + 21, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);

            EffectorColors colors = EffectorColors.itemColors(item);

            int agiColor = colors.agiColor();
            RenderSystem.setShaderColor(red(agiColor), green(agiColor), blue(agiColor), alpha(agiColor));
            if (alpha(agiColor) > 0) guiGraphics.blit(SLOT_FILLED, x + 18, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);

            int strColor = colors.strColor();
            RenderSystem.setShaderColor(red(strColor), green(strColor), blue(strColor), alpha(strColor));
            if (alpha(strColor) > 0) guiGraphics.blit(SLOT_FILLED, x + 18 + 7, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);

            int defColor = colors.defColor();
            RenderSystem.setShaderColor(red(defColor), green(defColor), blue(defColor), alpha(defColor));
            if (alpha(defColor) > 0) guiGraphics.blit(SLOT_FILLED, x + 18 + 14, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);

            int utiColor = colors.utiColor();
            RenderSystem.setShaderColor(red(utiColor), green(utiColor), blue(utiColor), alpha(utiColor));
            if (alpha(utiColor) > 0) guiGraphics.blit(SLOT_FILLED, x + 18 + 21, yOffset, 0, 0, SLOT_W, SLOT_H, SLOT_W, SLOT_H);

        }

    }));

    private static float red(int colorPacked) {return FastColor.ARGB32.red(colorPacked) / 255.0f;}
    private static float green(int colorPacked) {return FastColor.ARGB32.green(colorPacked) / 255.0f;}
    private static float blue(int colorPacked) {return FastColor.ARGB32.blue(colorPacked) / 255.0f;}
    private static float alpha(int colorPacked) {return FastColor.ARGB32.alpha(colorPacked) / 255.0f;}


}
