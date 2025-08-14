package com.example.examplemod.register;

import com.example.examplemod.UltimateBow;
import com.example.examplemod.entity.BroodMother;
import com.example.examplemod.entity.CustomSnowman;
import com.example.examplemod.entity.ShadowSteve;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UltimateBow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {
    @SubscribeEvent
    public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CUSTOM_SNOWMAN.get(), CustomSnowman.createAttributes().build());
        event.put(ModEntities.SHADOW_STEVE.get(), ShadowSteve.createAttributes().build());
        event.put(ModEntities.BROOD_MOTHER.get(), BroodMother.createAttributes().build());
    }
}
