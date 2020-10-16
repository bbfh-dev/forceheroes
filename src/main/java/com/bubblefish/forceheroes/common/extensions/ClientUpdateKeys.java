package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.ClientForceHeroes;
import com.bubblefish.forceheroes.ForceHeroes;
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
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            //we're not connected to any server. Reset the keys and do nothing
            slowMotion = false;
            speedForce = false;
            return;
        }

        boolean hasUpdated = false;

        if (ClientForceHeroes.keySlowMotion.wasPressed()) {
            hasUpdated = true;
            slowMotion = !slowMotion;
        }
        if (ClientForceHeroes.keySpeedForce.wasPressed()) {
            hasUpdated = true;
            speedForce = !speedForce;
        }

        if (hasUpdated) {
            sendKeys();
        }
    }

    @Environment(EnvType.CLIENT)
    public static void sendKeys() {
        try {
            KeyUpdatePacket packet = new KeyUpdatePacket(slowMotion, speedForce);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            packet.write(buf);
            ClientSidePacketRegistry.INSTANCE.sendToServer(ForceHeroes.KEY_UPDATE_PACKET_ID, buf);
        } catch (IOException e) {
            System.out.println("error whilst sending packet");
            e.printStackTrace();
        }
    }
}
