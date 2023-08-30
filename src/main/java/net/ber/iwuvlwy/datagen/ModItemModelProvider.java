package net.ber.iwuvlwy.datagen;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IWuvLwy.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.SHARKCAT_SPAWNEGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CAPYBARA_SPAWNEGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
