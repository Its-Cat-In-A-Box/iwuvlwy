package net.ber.iwuvlwy.item.custom;

import ca.weblite.objc.Client;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import net.ber.iwuvlwy.IWuvLwy;
import net.ber.iwuvlwy.channel.PacketHandler;
import net.ber.iwuvlwy.channel.packet.ImageC2SPacket;
import net.ber.iwuvlwy.item.client.InstaCameraItemRenderer;
import net.ber.iwuvlwy.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.main.Main;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.example.registry.SoundRegistry;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.ClientUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

public class InstaCameraItem extends Item implements GeoItem {
    private static final RawAnimation TAKEPHOTO_ANIM = RawAnimation.begin().thenPlay("takephoto");

    public InstaCameraItem(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state ->PlayState.STOP)
                .triggerableAnim("takephoto", TAKEPHOTO_ANIM)
                .setSoundKeyframeHandler(state -> {
                    Player player = ClientUtils.getClientPlayer();
                    if(player != null)
                        player.playSound(ModSounds.TAKE_PHOTO_SOUND.get(), 1, 1);
                })

        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel)
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pUsedHand), serverLevel), "controller", "takephoto");
        //InstaCameraFilmItem instaCameraFilmItem = new InstaCameraFilmItem(new Properties());
        UUID imgUUID = UUID.randomUUID();
        //if (pPlayer.getInventory().contains(instaCameraFilmItem.getDefaultInstance())){
        NativeImage img = Screenshot.takeScreenshot(Minecraft.getInstance().getMainRenderTarget());
        try {
            byte[] bytes = img.asByteArray();
            PacketHandler.sendToServer(new ImageC2SPacket(bytes, imgUUID.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        //}
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer){
        consumer.accept(new IClientItemExtensions() {
            private InstaCameraItemRenderer renderer = null;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null)
                    this.renderer = new InstaCameraItemRenderer();

                return this.renderer;

            }
        });
    }
}
