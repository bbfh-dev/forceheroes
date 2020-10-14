package com.bubblefish.forceheroes.mixins;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderTickCounter.class)
public interface RenderTickCounterMixin {
    @Accessor float getTickTime();
    @Accessor void setTickTime(float tickTime);
}
