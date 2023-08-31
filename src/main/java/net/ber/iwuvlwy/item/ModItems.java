package net.ber.iwuvlwy.item;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.entity.ModEntities;
import net.ber.iwuvlwy.item.custom.BuildersWandItem;
import net.ber.iwuvlwy.item.custom.InstaCameraItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IWuvLwy.MODID);


    public static final RegistryObject<Item> HEART = ITEMS.register("heart", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHARKCAT_SPAWNEGG = ITEMS.register("sharkcat_spawnegg", () ->
            new ForgeSpawnEggItem(ModEntities.SHARKCAT, 0x224B5B, 0xFFFFFF, new Item.Properties()));
    public static final RegistryObject<Item> CAPYBARA_SPAWNEGG = ITEMS.register("capybara_spawnegg", () ->
            new ForgeSpawnEggItem(ModEntities.CAPYBARA, 0x92853C, 0xD8BE2F, new Item.Properties()));


    public static final RegistryObject<Item> BUILDERS_WAND = ITEMS.register("builders_wand", () -> new BuildersWandItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INSTANT_CAMERA = ITEMS.register("instant_camera", () -> new InstaCameraItem(new Item.Properties().stacksTo(1)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
