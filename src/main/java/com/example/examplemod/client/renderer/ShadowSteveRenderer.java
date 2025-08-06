package com.example.examplemod.client.renderer;

import com.example.examplemod.entity.ShadowSteve;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelLayers;

public class ShadowSteveRenderer extends MobRenderer<ShadowSteve, PlayerModel<ShadowSteve>> {

    private static final ResourceLocation SHADOW_STEVE_TEXTURE =
            new ResourceLocation("ultimatebow", "textures/entity/drowned.png"); // your texture path here

    public ShadowSteveRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER),false), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowSteve entity) {
        return SHADOW_STEVE_TEXTURE;
    }
}
//package com.example.examplemod.client.renderer;
//
//import com.example.examplemod.entity.ShadowSteve;
//import net.minecraft.client.model.PlayerModel;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
//import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
//import net.minecraft.resources.ResourceLocation;
//
//public class ShadowSteveRenderer extends MobRenderer<ShadowSteve, PlayerModel<ShadowSteve>> {
//
//    private static final ResourceLocation SHADOW_STEVE_TEXTURE =
//            new ResourceLocation("ultimatebow", "textures/entity/shadowsteve.png");
//
//    public ShadowSteveRenderer(EntityRendererProvider.Context context) {
//        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
//
//        // ✅ Add armor rendering layer
//        this.addLayer(new HumanoidArmorLayer<>(
//                this,
//                new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR), false),
//                new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR), false)
//        ));
//
//        // ✅ Add held item rendering layer (e.g. sword)
//        this.addLayer(new ItemInHandLayer<>(this));
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(ShadowSteve entity) {
//        return SHADOW_STEVE_TEXTURE;
//    }
//}
