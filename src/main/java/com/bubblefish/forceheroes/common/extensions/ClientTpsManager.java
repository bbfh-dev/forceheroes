package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.mixins.MinecraftClientMixin;
import com.bubblefish.forceheroes.mixins.RenderTickCounterMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public class ClientTpsManager {
    private static RenderTickCounterMixin rtc = null;

    public static void changeTps(float tps) {
        if (rtc == null) {
            MinecraftClientMixin client = (MinecraftClientMixin) MinecraftClient.getInstance();
            rtc = (RenderTickCounterMixin) client.getRenderTickCounter();
        }
        if (rtc.getTickTime() != 1000f / tps) {
            rtc.setTickTime(1000f / tps);
        }
    }
}
