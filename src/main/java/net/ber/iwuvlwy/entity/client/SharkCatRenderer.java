package net.ber.iwuvlwy.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.entity.custom.SharkCatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SharkCatRenderer extends GeoEntityRenderer<SharkCatEntity> {
    public SharkCatRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager, new SharkCatModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SharkCatEntity animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "textures/entity/sharkcat.png");
    }

    @Override
    public void render(SharkCatEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()){
            poseStack.scale(.4f, .4f, .4f);
        }

        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }
}
