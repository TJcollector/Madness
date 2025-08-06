package com.example.examplemod.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SnowGolemMinionHandler {
    public SnowGolemMinionHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onSnowballImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Snowball snowball)) return;
        if (!(snowball.getOwner() instanceof CustomSnowman)) return;

        if (event.getRayTraceResult() instanceof EntityHitResult hitResult) {
            if (hitResult.getEntity() instanceof LivingEntity target) {
                // Apply slowness and weakness
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));

                // Apply custom damage
                target.hurt(DamageSource.thrown(snowball, snowball.getOwner()), 4.0F);
            }
        }
    }
    @SubscribeEvent
    public static void onSnowmanDamaged(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof SnowGolem) {
            DamageSource source = event.getSource();
            if (source == DamageSource.DROWN || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE) {
                event.setCanceled(true);
            }
        }
    }
}
