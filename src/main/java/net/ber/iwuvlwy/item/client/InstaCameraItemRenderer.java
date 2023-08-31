package net.ber.iwuvlwy.item.client;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.item.custom.BuildersWandItem;
import net.ber.iwuvlwy.item.custom.InstaCameraItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class InstaCameraItemRenderer extends GeoItemRenderer<InstaCameraItem> {
    public InstaCameraItemRenderer(){
        super(new InstaCameraItemModel());
    }
    @Override
    public ResourceLocation getTextureLocation(InstaCameraItem animatable) {
        return new ResourceLocation(IWuvLwy.MODID, "textures/item/instacamera.png");
    }
}
