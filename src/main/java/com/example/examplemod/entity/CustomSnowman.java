package com.example.examplemod.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
public class CustomSnowman extends SnowGolem {


    public static AttributeSupplier.Builder createAttributes() {
        return SnowGolem.createAttributes(); // Inherit snow golem's attributes
    }

    public CustomSnowman(EntityType<? extends SnowGolem> type, Level level) {
        super(type, level);
        this.setPumpkin(false); // Optional: remove pumpkin
    }

    @Override
    public void aiStep() {
        // Remove water damage behavior
        super.aiStep();
        // But skip the line: this.hurt(DamageSource.DROWN, 1.0F);
    }

    @Override
    public boolean isInWaterRainOrBubble() {
        return false; // prevents weather/water damage
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.DROWN || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE || source == DamageSource.WITHER) {
            return false; // ignore these damage types
        }
        return super.hurt(source, amount);
    }


}
