package net.ber.iwuvlwy.item;

import net.ber.iwuvlwy.IWuvLwy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IWuvLwy.MODID);

    public static final RegistryObject<CreativeModeTab> LWY_TAB = CREATIVE_MODE_TABS.register("lwy_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HEART.get()))
                    .title(Component.translatable("creativetab.lwy_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.HEART.get());
                        pOutput.accept(ModItems.BUILDERS_WAND.get());
                        pOutput.accept(ModItems.INSTANT_CAMERA.get());
                        pOutput.accept(ModItems.SHARKCAT_SPAWNEGG.get());
                        pOutput.accept(ModItems.CAPYBARA_SPAWNEGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
