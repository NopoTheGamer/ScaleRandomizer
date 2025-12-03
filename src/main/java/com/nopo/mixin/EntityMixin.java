package com.nopo.mixin;

import com.nopo.ScaleRandomizer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EntityMixin {

    @Unique
    final int scalemod$INTERVAL = ScaleRandomizer.config.scaleTimer;
    @Unique
    int scalemod$timer = scalemod$INTERVAL;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        doScale();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.getEntityWorld().isClient()) {
            scalemod$timer--;
            if (scalemod$timer <= 0) {
                scalemod$timer = scalemod$INTERVAL;
                doScale();
            }
        }
    }

    @Unique
    public void doScale() {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.getEntityWorld().isClient()) {
            if (!ScaleRandomizer.config.shouldEffectPlayers && entity instanceof PlayerEntity) return;
            EntityAttributeInstance scaleAttribute = entity.getAttributeInstance(EntityAttributes.SCALE);
            if (scaleAttribute != null) {
                Random r = entity.getRandom();
                double random;
                if (r.nextFloat() < ScaleRandomizer.config.fullRangeChance) {
                    random = 0.0625 + r.nextDouble() * (16 - 0.0625);
                } else {
                    random = 0.25 + r.nextDouble() * (1.75 - 0.25);
                }
                scaleAttribute.setBaseValue(random);
                EntityAttributeInstance entitySwingAttribute = entity.getAttributeInstance(EntityAttributes.ENTITY_INTERACTION_RANGE);
                if (entitySwingAttribute != null) {
                    entitySwingAttribute.setBaseValue(2 + random);
                }
                EntityAttributeInstance blockSwingAttribute = entity.getAttributeInstance(EntityAttributes.BLOCK_INTERACTION_RANGE);
                if (blockSwingAttribute != null) {
                    blockSwingAttribute.setBaseValue(3.5 + random);
                }

                entity.calculateDimensions();
            }
        }
    }
}
