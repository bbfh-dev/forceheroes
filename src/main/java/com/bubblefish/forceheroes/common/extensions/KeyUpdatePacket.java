package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.common.mixins.KeyTracker;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.io.IOException;

public class KeyUpdatePacket {
    private boolean slowMotion;
    private boolean speedForce;

    public KeyUpdatePacket(boolean slowMotion, boolean speedForce) {
        this.slowMotion = slowMotion;
        this.speedForce = speedForce;
    }

    public KeyUpdatePacket() {

    }

    public void read(PacketByteBuf buf) throws IOException {
        slowMotion = buf.readBoolean();
        speedForce = buf.readBoolean();
    }

    public void write(PacketByteBuf buf) throws IOException {
        buf.writeBoolean(slowMotion);
        buf.writeBoolean(speedForce);
    }

    public static void onRecieve(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        try {
            KeyUpdatePacket packet = new KeyUpdatePacket();
            packet.read(packetByteBuf);

            PlayerEntity player = packetContext.getPlayer();
            if (player instanceof KeyTracker) {
                KeyTracker keyTracker = (KeyTracker)player;
                keyTracker.setSlowMotion(packet.slowMotion);
                keyTracker.setSpeedForce(packet.speedForce);
            } else {
                System.out.println("[ForceHeroes] Impossible situation. Player isn't implementing KeyTracker. The Mixin might not have applied or this entity isn't of type ServerPlayerEntity");
            }
        } catch (IOException e) {
            System.out.println("[ForceHeroes] invalid packet recieved");
            e.printStackTrace();
        }
    }
}
