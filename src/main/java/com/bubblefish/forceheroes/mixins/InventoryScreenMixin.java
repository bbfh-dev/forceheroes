package com.bubblefish.forceheroes.mixins;

import com.bubblefish.forceheroes.ForceHeroes;
import com.bubblefish.forceheroes.common.mixins.InventoryScreenSliderA;
import com.bubblefish.forceheroes.common.mixins.InventoryScreenSliderB;
import com.bubblefish.forceheroes.common.mixins.SliderVars;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
    private static final Identifier BACKGROUND_ABILITY = new Identifier(ForceHeroes.MOD_ID, "textures/gui/inventory.png");
//    private static final InventoryScreenSlider inventoryScreenSlider = new InventoryScreenSlider(0, 0, 120, 20, Text.of(""), 10.0F);
    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(at = @At("TAIL"), method = "drawBackground")
    private void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(BACKGROUND_ABILITY);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i + 178, j, 0, 0, 120, 32);
        this.drawTexture(matrices, i + 178, j + 34, 0, 0, 120, 32);
    }

    @Inject(at = @At("TAIL"), method = "drawForeground")
    private void drawForeground(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo info) {
        this.textRenderer.draw(matrices, new TranslatableText("gui.forceheroes.inventory.speed"), (float)this.titleX + 86.0F, (float)this.titleY, 4210752);
        this.textRenderer.draw(matrices, new TranslatableText("gui.forceheroes.inventory.slowmo"), (float)this.titleX + 86.0F, (float)this.titleY + 34, 4210752);
    }


    @Inject(method = "init", at = @At("TAIL"))
    void init(CallbackInfo info) {
        this.addButton(new InventoryScreenSliderA(this.x + 183, this.y + 12, 110, 20, Text.of(""), SliderVars.varA));
        this.addButton(new InventoryScreenSliderB(this.x + 183, this.y + 46, 110, 20, Text.of(""), SliderVars.varB));
    }

}
