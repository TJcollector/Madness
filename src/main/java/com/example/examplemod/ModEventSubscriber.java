//package com.example.examplemod;
//
//import com.example.examplemod.entity.CustomSnowman;
//import com.example.examplemod.entity.ShadowSteve;
//import com.example.examplemod.register.ModEntities;
//import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = UltimateBow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class ModEventSubscriber {
//    @SubscribeEvent
//    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
//        event.put(ModEntities.CUSTOM_SNOWMAN.get(), CustomSnowman.createAttributes().build());
//        event.put(ModEntities.SHADOW_STEVE.get(), ShadowSteve.createAttributes().build());
//    }
//}
