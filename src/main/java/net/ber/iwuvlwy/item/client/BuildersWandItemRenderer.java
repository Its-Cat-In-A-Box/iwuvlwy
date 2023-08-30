package net.ber.iwuvlwy.item.client;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.item.custom.BuildersWandItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BuildersWandItemRenderer extends GeoItemRenderer<BuildersWandItem> {
    public BuildersWandItemRenderer() {
        super(new BuildersWandItemModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BuildersWandItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "textures/item/builders_wand.png");
    }
}
