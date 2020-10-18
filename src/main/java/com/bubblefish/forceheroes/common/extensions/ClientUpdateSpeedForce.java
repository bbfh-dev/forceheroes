package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.ClientForceHeroes;
import com.bubblefish.forceheroes.ForceHeroes;
import com.bubblefish.forceheroes.common.mixins.SpeedforceTracker;
import com.bubblefish.forceheroes.common.mixins.SliderVars;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

import java.io.IOException;

public class ClientUpdateSpeedForce implements ClientTickEvents.EndTick {
    public static boolean slowMotion = false;
    public static boolean speedForce = false;
    public static boolean armorEquipped = false;

    @Override
    @Environment(EnvType.CLIENT)
    public void onEndTick(MinecraftClient minecraftClient) {
        if (minecraftClient.getNetworkHandler() == null) {
            //we're not connected to any server. Reset the keys and do nothing
            slowMotion = false;
            speedForce = false;
            armorEquipped = false;
            return;
        }

        boolean hasUpdated = false;

        boolean slowmoPressed = ClientForceHeroes.keySlowMotion.wasPressed();

        if (slowmoPressed) {

            hasUpdated = true;
            slowMotion = !slowMotion;
        }

        if (armorEquipped) {
            if (slowmoPressed || SliderVars.bEdited) {
                if (slowMotion) {
                    ClientTpsManager.changeTps((float) (20 - ((SliderVars.varB * 10 + 1) * 1.7F)));
                } else {
                    ClientTpsManager.changeTps(20F);
                }
            }
        } else {
            ClientTpsManager.changeTps(20F);
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
        if (minecraftClient.player instanceof SpeedforceTracker) {
            SpeedforceTracker speedforceTracker = (SpeedforceTracker)minecraftClient.player;
            speedforceTracker.setSlowMotion(slowMotion);
            speedforceTracker.setSpeedForce(speedForce);
            speedforceTracker.setSpeedAmount(SliderVars.varA);
            speedforceTracker.setSlowmoAmount(SliderVars.varB);
        }
    }

    public static void changeEquippedStatus (boolean value) {
        armorEquipped = value;
    }

    @Environment(EnvType.CLIENT)
    public static void sendKeys() {
        try {
            SpeedforceUpdatePacket packet = new SpeedforceUpdatePacket(slowMotion, speedForce, SliderVars.varA, SliderVars.varB);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            packet.write(buf);
            ClientSidePacketRegistry.INSTANCE.sendToServer(ForceHeroes.SPEEDFORCE_UPDATE_PACKET_ID, buf);
        } catch (IOException e) {
            System.out.println("error whilst sending packet");
            e.printStackTrace();
        }
    }
}
