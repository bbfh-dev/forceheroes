package com.bubblefish.forceheroes.common.extensions;

import com.bubblefish.forceheroes.mixins.MinecraftClientMixin;
import com.bubblefish.forceheroes.mixins.RenderTickCounterMixin;
import net.minecraft.client.MinecraftClient;

public class TpsManager {
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
