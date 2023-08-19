package net.ber.iwuvlwy.item.client;

import net.ber.iwuvlwy.item.custom.BuildersWandItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BuildersWandItemRenderer extends GeoItemRenderer<BuildersWandItem> {
    public BuildersWandItemRenderer() {
        super(new BuildersWandItemModel());
    }
}
