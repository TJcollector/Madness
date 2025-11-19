

package com.example.examplemod.effect;

import com.example.examplemod.util.ShadowCurseHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;

public class ShadowCursedEffect extends MobEffect {

    public ShadowCursedEffect() {
        super(MobEffectCategory.HARMFUL, 0x5A007A);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Run every tick
    }
}

