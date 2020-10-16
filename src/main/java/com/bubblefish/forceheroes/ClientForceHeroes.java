package com.bubblefish.forceheroes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientForceHeroes implements ClientModInitializer {
    public static final KeyBinding keySlowMotion = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.forceheroes.slowMotion", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "category.forceheroes.controls")); //Registering new Keybinding for slowmo mode
    public static final KeyBinding keySpeedForce = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.forceheroes.speedForce", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.forceheroes.controls")); //Registering new Keybinding for speed force mode

    @Override
    public void onInitializeClient() {
    }
}
