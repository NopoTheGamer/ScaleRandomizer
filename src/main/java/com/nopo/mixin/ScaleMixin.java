package com.nopo.mixin;

import com.nopo.ScaleRandomizer;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.HappyGhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {EnderDragonEntity.class, HappyGhastEntity.class, ShulkerEntity.class})
public class ScaleMixin {

	@Inject(at = @At("HEAD"), method = "clampScale", cancellable = true)
	private void clampScale(float scale, CallbackInfoReturnable<Float> cir) {
		if (ScaleRandomizer.config.uncapScale) cir.setReturnValue(scale);
	}
}