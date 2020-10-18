package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.common.mixins.SpeedforceTracker;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.io.IOException;

public class SpeedforceUpdatePacket {
    private boolean slowMotion;
    private boolean speedForce;
    private double speedAmount;
    private double slowmoAmount;

    public SpeedforceUpdatePacket(boolean slowMotion, boolean speedForce, double speedAmount, double slowmoAmount) {
        this.slowMotion = slowMotion;
        this.speedForce = speedForce;
        this.speedAmount = speedAmount;
        this.slowmoAmount = slowmoAmount;
    }

    public SpeedforceUpdatePacket() {

    }

    public void read(PacketByteBuf buf) throws IOException {
        slowMotion = buf.readBoolean();
        speedForce = buf.readBoolean();
        speedAmount = buf.readDouble();
        slowmoAmount = buf.readDouble();
    }

    public void write(PacketByteBuf buf) throws IOException {
        buf.writeBoolean(slowMotion);
        buf.writeBoolean(speedForce);
        buf.writeDouble(speedAmount);
        buf.writeDouble(slowmoAmount);
    }

    public static void onRecieve(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        try {
            SpeedforceUpdatePacket packet = new SpeedforceUpdatePacket();
            packet.read(packetByteBuf);

            PlayerEntity player = packetContext.getPlayer();
            if (player instanceof SpeedforceTracker) {
                SpeedforceTracker speedforceTracker = (SpeedforceTracker)player;
                speedforceTracker.setSlowMotion(packet.slowMotion);
                speedforceTracker.setSpeedForce(packet.speedForce);
                speedforceTracker.setSpeedAmount(packet.speedAmount);
                speedforceTracker.setSlowmoAmount(packet.slowmoAmount);
            } else {
                System.out.println("[ForceHeroes] Impossible situation. Player isn't implementing KeyTracker. The Mixin might not have applied or this entity isn't of type ServerPlayerEntity");
            }
        } catch (IOException e) {
            System.out.println("[ForceHeroes] invalid packet recieved");
            e.printStackTrace();
        }
    }
}
