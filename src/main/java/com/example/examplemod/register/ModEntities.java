//package com.example.examplemod.register;
//
//import com.example.examplemod.UltimateBow;
//import com.example.examplemod.entity.UndeadMinion;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.MobCategory;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//
//public class ModEntities {
//    // Create DeferredRegister for EntityTypes using ForgeRegistries.ENTITY_TYPES and modid
//    public static final DeferredRegister<EntityType<?>> ENTITIES =
//            DeferredRegister.create(ForgeRegistries.ENTITIES, UltimateBow.MODID);
//
//    // Register UndeadMinion entity type
//    public static final RegistryObject<EntityType<UndeadMinion>> UNDEAD_MINION =
//            ENTITIES.register("undead_minion", () ->
//                    EntityType.Builder.<UndeadMinion>of(UndeadMinion::new, MobCategory.MONSTER)
//                            .sized(0.6f, 1.95f)
//                            .clientTrackingRange(10)
//                            .build("undead_minion"));
//
//}
package com.example.examplemod.register;

import com.example.examplemod.UltimateBow;
import com.example.examplemod.entity.CustomSnowman;
import com.example.examplemod.entity.ShadowSteve;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, UltimateBow.MODID);

    public static final RegistryObject<EntityType<CustomSnowman>> CUSTOM_SNOWMAN =
            ENTITIES.register("custom_snowman", () ->
                    EntityType.Builder.of(CustomSnowman::new, MobCategory.MISC)
                            .sized(0.7f, 1.9f) // Size same as snow golem
                            .clientTrackingRange(10)
                            .build("custom_snowman"));

    public static final RegistryObject<EntityType<ShadowSteve>> SHADOW_STEVE =
            ENTITIES.register("shadow_steve", () ->
                    EntityType.Builder.<ShadowSteve>of(ShadowSteve::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .clientTrackingRange(10)
                            .build("shadow_steve"));
//    @Mod.EventBusSubscriber(modid = UltimateBow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    // ✅ Static inner class for registering attributes
//    public static class ModEntityAttributes {
//        @SubscribeEvent
//        public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
//            event.put(ModEntities.CUSTOM_SNOWMAN.get(), CustomSnowman.createAttributes().build());
//            event.put(ModEntities.SHADOW_STEVE.get(), ShadowSteve.createAttributes().build());
//        }
//    }

}
