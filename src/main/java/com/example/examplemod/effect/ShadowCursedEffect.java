//package com.example.examplemod.effect;
//
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.entity.LivingEntity;
//
//public class ShadowCursedEffect extends MobEffect {
//    public ShadowCursedEffect() {
//        super(MobEffectCategory.HARMFUL, 0x5A007A); // Dark purple color
//    }
//
//    @Override
//    public void applyEffectTick(LivingEntity entity, int amplifier) {
//        // This could apply other effects if you want
//    }
//
//    @Override
//    public boolean isDurationEffectTick(int duration, int amplifier) {
//        return false; // no periodic effect logic, purely visual/status
//    }
//}

//
//package com.example.examplemod.effect;
//
//import com.example.examplemod.util.ShadowCurseHandler;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.core.particles.ParticleTypes;
//
//public class ShadowCursedEffect extends MobEffect {
//
//    public ShadowCursedEffect() {
//        super(MobEffectCategory.HARMFUL, 0x5A007A); // Dark purple color
//    }
//
//    @Override
//    public void applyEffectTick(LivingEntity entity, int amplifier) {
//        if (!entity.level.isClientSide) {
//            // Deal small damage per tick
//            entity.hurt(net.minecraft.world.damagesource.DamageSource.MAGIC, 0.5F);
//
//            // Spawn subtle soul particles around the player
//            if (entity instanceof Player) {
//                ((ServerLevel) entity.level).sendParticles(
//                        ParticleTypes.ASH,
//                        entity.getX(), entity.getY() + 1, entity.getZ(),
//                        1, 0.1, 0.1, 0.1, 0.0
//                );
//            }
//        }
//    }
//
//    @Override
//    public boolean isDurationEffectTick(int duration, int amplifier) {
//        // Tick logic runs every game tick
//        return true;
//    }
//}



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

