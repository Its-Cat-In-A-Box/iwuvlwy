package net.ber.iwuvlwy.datagen;

import net.ber.iwuvlwy.IWuvLwy;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IWuvLwy.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
