package com.example.examplemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ShadowCursedEffect extends MobEffect {
    public ShadowCursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x5A007A); // Dark purple color
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // This could apply other effects if you want
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false; // no periodic effect logic, purely visual/status
    }
}
