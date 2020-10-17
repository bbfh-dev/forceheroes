package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.ClientForceHeroes;
import com.bubblefish.forceheroes.ForceHeroes;
import com.bubblefish.forceheroes.common.mixins.KeyTracker;
import com.bubblefish.forceheroes.common.mixins.SliderVars;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

import java.io.IOException;

public class ClientUpdateKeys implements ClientTickEvents.EndTick {
    public static boolean slowMotion = false;
    public static boolean speedForce = false;

    @Override
    @Environment(EnvType.CLIENT)
    public void onEndTick(MinecraftClient minecraftClient) {
        if (minecraftClient.getNetworkHandler() == null) {
            //we're not connected to any server. Reset the keys and do nothing
            slowMotion = false;
            speedForce = false;
            return;
        }

        boolean hasUpdated = false;

        if (ClientForceHeroes.keySlowMotion.wasPressed()) {
            hasUpdated = true;
            slowMotion = !slowMotion;

            if (slowMotion) {
                ClientTpsManager.changeTps((float) (20 - ((SliderVars.varB * 10 + 1) * 1.7F)));
            } else {
                ClientTpsManager.changeTps(20F);
            }
        }
        if (ClientForceHeroes.keySpeedForce.wasPressed()) {
            hasUpdated = true;
            speedForce = !speedForce;
        }
        if (SliderVars.aEdited || SliderVars.bEdited) {
            SliderVars.aEdited = false;
            SliderVars.bEdited = false;
            hasUpdated = true;
        }

        if (hasUpdated) {
            sendKeys();
        }

        //make sure the local player entity has the right values synced.
        if (minecraftClient.player instanceof KeyTracker) {
            KeyTracker keyTracker = (KeyTracker)minecraftClient.player;
            keyTracker.setSlowMotion(slowMotion);
            keyTracker.setSpeedForce(speedForce);
            keyTracker.setSpeedAmount(SliderVars.varA);
            keyTracker.setSlowmoAmount(SliderVars.varB);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void sendKeys() {
        try {
            KeyUpdatePacket packet = new KeyUpdatePacket(slowMotion, speedForce, SliderVars.varA, SliderVars.varB);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            packet.write(buf);
            ClientSidePacketRegistry.INSTANCE.sendToServer(ForceHeroes.KEY_UPDATE_PACKET_ID, buf);
        } catch (IOException e) {
            System.out.println("error whilst sending packet");
            e.printStackTrace();
        }
    }
}
