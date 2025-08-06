package com.example.examplemod.client;

//import com.example.examplemod.entity.ShadowSteve;
import com.example.examplemod.register.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.example.examplemod.client.renderer.ShadowSteveRenderer;
@Mod.EventBusSubscriber(modid = "ultimatebow", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @Mod.EventBusSubscriber(modid = "ultimatebow", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            // Use vanilla SnowGolem renderer
            event.registerEntityRenderer(ModEntities.CUSTOM_SNOWMAN.get(), SnowGolemRenderer::new);

            // Register Shadow Steve renderer, using vanilla MobRenderer (Steve model)
            event.registerEntityRenderer(ModEntities.SHADOW_STEVE.get(), ShadowSteveRenderer::new);
        }
    }

}
