//package com.example.examplemod.client;
//
//import com.example.examplemod.entity.UndeadMinion;
//import net.minecraft.client.model.HumanoidModel;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
//import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
//import net.minecraft.resources.ResourceLocation;
//
//public class UndeadMinionRenderer extends MobRenderer<UndeadMinion, HumanoidModel<UndeadMinion>> {
//
//    private static final ResourceLocation TEXTURE = new ResourceLocation("ultimatebow", "textures/entity/godzilla.png");
//
//    public UndeadMinionRenderer(EntityRendererProvider.Context context) {
//        super(context,
//                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)),
//                0.5f);
//
//        this.addLayer(new HumanoidArmorLayer<>(this,
//                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
//                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))));
//
//        this.addLayer(new ItemInHandLayer<>(this));
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(UndeadMinion entity) {
//        return TEXTURE;
//    }
//}
