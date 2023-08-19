package net.ber.iwuvlwy.item.client;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.item.custom.BuildersWandItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BuildersWandItemModel extends GeoModel<BuildersWandItem> {
    @Override
    public ResourceLocation getModelResource(BuildersWandItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "geo/builders_wand.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(BuildersWandItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "textures/item/builders_wand.png");
    }
    @Override
    public ResourceLocation getAnimationResource(BuildersWandItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "animations/builders_wand.animation.json");
    }
}
