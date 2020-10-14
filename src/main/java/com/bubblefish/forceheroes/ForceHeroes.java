package com.bubblefish.forceheroes;

import com.bubblefish.forceheroes.common.extensions.IncreaseStepHeight;
import com.bubblefish.forceheroes.item.TheFlashArmor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ForceHeroes implements ModInitializer {
    public static final String MOD_ID = "forceheroes"; //Creating main project MOD_ID constant
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "group"),
            () -> new ItemStack(TheFlashArmor.THE_FLASH_CHESTPLATE)); //Creating main item group
    public static final KeyBinding keySlowMotion = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.forceheroes.slowMotion", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "category.forceheroes.controls")); //Registering new Keybinding for slowmo mode
    public static final KeyBinding keySpeedForce = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.forceheroes.speedForce", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.forceheroes.controls")); //Registering new Keybinding for speed force mode

    @Override
    public void onInitialize() {
        TheFlashArmor.init(); //Registry The Flash Armor
        IncreaseStepHeight increaseStepHeight = new IncreaseStepHeight(); //Increase Step Height when player has speed force
        ClientTickEvents.END_CLIENT_TICK.register(increaseStepHeight); //Increase Step Height when player has speed force
    }
}
