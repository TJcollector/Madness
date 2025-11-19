
package com.example.examplemod.client.renderer;
import com.example.examplemod.entity.ShadowSteve;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ShadowSteveRenderer extends MobRenderer<ShadowSteve, HumanoidModel<ShadowSteve>> {

    private static final ResourceLocation SHADOW_STEVE_TEXTURE =
            new ResourceLocation("ultimatebow", "textures/entity/shadowsteve.png");

    public ShadowSteveRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.8F);

        // ✅ Armor layer
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))
        ));

        // ✅ Weapons/items in hand
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowSteve entity) {
        return SHADOW_STEVE_TEXTURE;
    }

    @Override
    protected void scale(ShadowSteve entity, PoseStack poseStack, float partialTickTime) {
        float scale = 1.5f; // make ShadowSteve bigger
        poseStack.scale(scale, scale, scale);
        super.scale(entity, poseStack, partialTickTime);
    }
}





