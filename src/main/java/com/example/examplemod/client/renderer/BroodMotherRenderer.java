package com.example.examplemod.client.renderer;

import com.example.examplemod.entity.BroodMother;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;

public class BroodMotherRenderer extends MobRenderer<BroodMother, SpiderModel<BroodMother>> {

    private static final ResourceLocation BROOD_MOTHER_TEXTURE =
            new ResourceLocation("ultimatebow", "textures/entity/brood_mother.png");

    public BroodMotherRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 4.0f); // Adjust shadow size if needed
    }

    @Override
    public ResourceLocation getTextureLocation(BroodMother entity) {
        return BROOD_MOTHER_TEXTURE;
    }

    @Override
    protected void scale(BroodMother entity, PoseStack poseStack, float partialTickTime) {
        float scale = 10f;  // Make her big and scary!
        poseStack.scale(scale, scale, scale);
        super.scale(entity, poseStack, partialTickTime);
    }
}
