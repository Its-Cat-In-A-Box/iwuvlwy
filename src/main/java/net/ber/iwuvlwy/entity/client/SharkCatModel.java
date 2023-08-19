package net.ber.iwuvlwy.entity.client;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.entity.custom.SharkCatEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.DataTicket;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SharkCatModel extends GeoModel<SharkCatEntity> {
    @Override
    public ResourceLocation getModelResource(SharkCatEntity animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "geo/sharkcat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SharkCatEntity animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "texture/entity/sharkcat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SharkCatEntity animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "animations/sharkcat.animation.json");
    }

    @Override
    public void setCustomAnimations(SharkCatEntity animatable, long instanceId, AnimationState<SharkCatEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null){
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
