package net.ber.iwuvlwy;

import com.mojang.logging.LogUtils;
import net.ber.iwuvlwy.channel.PacketHandler;
import net.ber.iwuvlwy.entity.ModEntities;
import net.ber.iwuvlwy.entity.client.CapybaraRenderer;
import net.ber.iwuvlwy.entity.client.SharkCatRenderer;
import net.ber.iwuvlwy.item.ModCreativeModTabs;
import net.ber.iwuvlwy.item.ModItems;
import net.ber.iwuvlwy.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IWuvLwy.MODID)
public class IWuvLwy{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "iwuvlwy";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public IWuvLwy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);

        ModSounds.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.register();


    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws IOException {
        if (!Files.exists(Path.of(String.valueOf(event.getServer().getServerDirectory()), MODID, "img"))){
            Files.createDirectories(
                    Path.of(String.valueOf(event.getServer().getServerDirectory()), MODID, "img"));
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SHARKCAT.get(), SharkCatRenderer::new);
            EntityRenderers.register(ModEntities.CAPYBARA.get(), CapybaraRenderer::new);
        }
    }


}
