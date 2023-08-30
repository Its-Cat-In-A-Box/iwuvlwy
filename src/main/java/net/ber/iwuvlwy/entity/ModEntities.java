package net.ber.iwuvlwy.entity;

import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.entity.custom.CapybaraEntity;
import net.ber.iwuvlwy.entity.custom.SharkCatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, IWuvLwy.MODID);

    public static final RegistryObject<EntityType<SharkCatEntity>> SHARKCAT =
            ENTITY_TYPES.register("sharkcat", () -> EntityType.Builder.of(SharkCatEntity::new, MobCategory.CREATURE)
                    .sized(.4f, 0.5f)
                    .build(new ResourceLocation(IWuvLwy.MODID, "sharkcat").toString()));
    public static final RegistryObject<EntityType<CapybaraEntity>> CAPYBARA =
            ENTITY_TYPES.register("capybara", () -> EntityType.Builder.of(CapybaraEntity::new, MobCategory.CREATURE)
                    .sized(.4f, .5f)
                    .build(new ResourceLocation(IWuvLwy.MODID, "capybara").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
