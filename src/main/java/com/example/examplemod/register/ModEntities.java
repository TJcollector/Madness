
package com.example.examplemod.register;

import com.example.examplemod.UltimateBow;
import com.example.examplemod.entity.BroodMother;
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

    public static final RegistryObject<EntityType<BroodMother>> BROOD_MOTHER =
            ENTITIES.register("broodmother", () ->
                    EntityType.Builder.of(BroodMother::new, MobCategory.MONSTER)
                            .sized(4.0f, 2.5f) // bigger than normal spider (default is about 1.4x0.9)
                            .clientTrackingRange(10)
                            .build("broodmother"));

  

}
