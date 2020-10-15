package com.bubblefish.forceheroes.mixins;

import com.bubblefish.forceheroes.common.extensions.TpsManager;
import com.bubblefish.forceheroes.common.mixins.SliderVars;
import com.bubblefish.forceheroes.item.TheFlashArmor;
import com.bubblefish.forceheroes.keybinds.keySlowMotion;
import com.bubblefish.forceheroes.keybinds.keySpeedForce;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        keySlowMotion.update();
        keySpeedForce.update();
        updateSpeedForce();
    }

    private void updateSpeedForce() {
        try {
            ItemStack equippedHelmet = this.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack equippedChestplate = this.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack equippedLeggings = this.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack equippedBoots = this.getEquippedStack(EquipmentSlot.FEET);
            if (equippedHelmet.getItem() == TheFlashArmor.THE_FLASH_HELMET && equippedChestplate.getItem() == TheFlashArmor.THE_FLASH_CHESTPLATE && equippedLeggings.getItem() == TheFlashArmor.THE_FLASH_LEGGINGS && equippedBoots.getItem() == TheFlashArmor.THE_FLASH_BOOTS) {
                if (keySpeedForce.speedForce) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, (int) ((SliderVars.varA * 10 + 1) * 45), false, false, false));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 1, 1, false, false, false));
                    IncreaseStepHeight.autoJumpState = 0;
                } else {
                    IncreaseStepHeight.autoJumpState = 1;
                }

                if (keySlowMotion.slowMotion) {
                    TpsManager.changeTps((float) (20 - ((SliderVars.varB * 10 + 1) * 1.7F)));
                } else {
                    TpsManager.changeTps(20F);
                }
            } else {
                IncreaseStepHeight.autoJumpState = 1;
                TpsManager.changeTps(20F);
            }
        } catch (AbstractMethodError ignore) {}
    }
}
