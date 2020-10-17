package com.bubblefish.forceheroes.mixins;

import com.bubblefish.forceheroes.common.extensions.ClientTpsManager;
import com.bubblefish.forceheroes.common.mixins.KeyTracker;
import com.bubblefish.forceheroes.common.mixins.SliderVars;
import com.bubblefish.forceheroes.item.TheFlashArmor;
import com.bubblefish.forceheroes.common.extensions.IncreaseStepHeight;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements KeyTracker {
    @Unique
    private boolean slowMotion = false;
    @Unique
    private boolean speedForce = false;
    @Unique
    private double speedAmount;
    @Unique
    private double slowmoAmount;


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        updateSpeedForce();
    }

    private void updateSpeedForce() {
        try {
            ItemStack equippedHelmet = this.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack equippedChestplate = this.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack equippedLeggings = this.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack equippedBoots = this.getEquippedStack(EquipmentSlot.FEET);
            if (equippedHelmet.getItem() == TheFlashArmor.THE_FLASH_HELMET && equippedChestplate.getItem() == TheFlashArmor.THE_FLASH_CHESTPLATE && equippedLeggings.getItem() == TheFlashArmor.THE_FLASH_LEGGINGS && equippedBoots.getItem() == TheFlashArmor.THE_FLASH_BOOTS) {
                if (speedForce) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, (int) ((SliderVars.varA * 10 + 1) * 45), false, false, false));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 1, 1, false, false, false));
                    IncreaseStepHeight.autoJumpState = 0;
                } else {
                    IncreaseStepHeight.autoJumpState = 1;
                }
            } else {
                IncreaseStepHeight.autoJumpState = 1;
            }
        } catch (AbstractMethodError ignore) {}
    }

    @Override
    public void setSlowMotion(boolean v) {
        slowMotion = v;
    }

    @Override
    public void setSpeedForce(boolean v) {
        speedForce = v;
    }

    @Override
    public void setSpeedAmount(double d) {
        speedAmount = d;
    }

    @Override
    public void setSlowmoAmount(double d) {
        slowmoAmount = d;
    }
}
