package com.example.examplemod.client.renderer;

import com.example.examplemod.UltimateBow;
import com.example.examplemod.effect.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UltimateBow.MODID, value = Dist.CLIENT)
public class EffectIconRenderer {

    private static final ResourceLocation SHADOW_CURSE_ICON =
            new ResourceLocation(UltimateBow.MODID, "textures/mob_effect/shadow_curse.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        // Check if player has the Shadow Curse effect
        for (MobEffectInstance effect : mc.player.getActiveEffects()) {
            if (effect.getEffect() == ModEffects.SHADOW_CURSE.get()) {
                // Draw the icon on HUD
                RenderSystem.setShaderTexture(0, SHADOW_CURSE_ICON);
                GuiComponent.blit(event.getMatrixStack(), 10, 10, 0, 0, 18, 18, 18, 18);
            }
        }
    }
}
