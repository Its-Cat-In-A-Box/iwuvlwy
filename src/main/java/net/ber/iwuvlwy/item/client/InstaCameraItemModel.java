package net.ber.iwuvlwy.item.client;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.item.custom.BuildersWandItem;
import net.ber.iwuvlwy.item.custom.InstaCameraItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InstaCameraItemModel extends GeoModel<InstaCameraItem> {
    @Override
    public ResourceLocation getModelResource(InstaCameraItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "geo/instacamera.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InstaCameraItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "textures/item/instacamera.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InstaCameraItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "animations/instacamera.animation.json");
    }
}
