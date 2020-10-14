package com.bubblefish.forceheroes.common.extensions;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class IncreaseStepHeight implements ClientTickEvents.EndTick {

    public static int autoJumpState = -1; //0 StepUp, 1 None, 2 Minecraft
    public static boolean firstRun = true;
    private MinecraftClient minecraftClient;

    @Override
    public void onEndTick(MinecraftClient client) {
        ClientPlayerEntity playerEntity;
        minecraftClient = client;
        playerEntity = client.player;

        if (playerEntity == null) return;

        if(playerEntity.isSneaking()) {
            playerEntity.stepHeight = 0.6F;
        } else if (autoJumpState == 0 && playerEntity.stepHeight < 1.0F) {
            playerEntity.stepHeight = 1.25F;
        } else if (autoJumpState == 1 && playerEntity.stepHeight >= 1.0F) {
            playerEntity.stepHeight = 0.6F;
        } else if (autoJumpState == 2 && playerEntity.stepHeight >= 1.0F) {
            playerEntity.stepHeight = 0.6F;
        } autoJump();

        if (firstRun && autoJumpState != -1) {
            firstRun = false;
        }
    }

    private void autoJump() {
        boolean b = minecraftClient.options.autoJump;
        if(autoJumpState < 2 && b) {
            minecraftClient.options.autoJump=false;
        } else if(autoJumpState == 2 && !b) {
            minecraftClient.options.autoJump=true;
        }
    }
}
