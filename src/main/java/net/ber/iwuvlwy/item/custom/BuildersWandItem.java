package net.ber.iwuvlwy.item.custom;

import net.ber.iwuvlwy.item.client.BuildersWandItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BuildersWandItem extends Item implements GeoItem {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public BuildersWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            BlockPos blockClicked = pContext.getClickedPos();
            BlockState state = pContext.getLevel().getBlockState(blockClicked);
            Player player = pContext.getPlayer();
            assert player != null;
            Direction blockFace = pContext.getClickedFace();
            List<BlockPos> connectedBlocks = new ArrayList<>();
            if (blockFace.getAxis() == Direction.Axis.Y) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        BlockPos toTest;
                        if (pContext.getLevel().getBlockState(toTest = new BlockPos(blockClicked.getX() + x, blockClicked.getY(), blockClicked.getZ() + z)).is(state.getBlock())) {
                            connectedBlocks.add(toTest);
                        }
                    }
                }

            } else if (blockFace.getAxis() == Direction.Axis.X) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        BlockPos toTest;
                        if (pContext.getLevel().getBlockState(toTest = new BlockPos(blockClicked.getX(), blockClicked.getY() + y, blockClicked.getZ() + z)).is(state.getBlock())) {
                            connectedBlocks.add(toTest);
                        }
                    }
                }

            } else {
                for (int y = -1; y < 2; y++) {
                    for (int x = -1; x < 2; x++) {
                        BlockPos toTest;
                        if (pContext.getLevel().getBlockState(toTest = new BlockPos(blockClicked.getX() + x, blockClicked.getY() + y, blockClicked.getZ())).is(state.getBlock())) {
                            connectedBlocks.add(toTest);
                        }
                    }
                }
            }

            for (BlockPos conBlock : connectedBlocks) {
                BlockPos newBlockPos = new BlockPos(conBlock.getX() + blockFace.getStepX(), conBlock.getY() + blockFace.getStepY(), conBlock.getZ() + blockFace.getStepZ());
                if (player.getInventory().contains(new ItemStack(state.getBlock()))) {
                    pContext.getLevel().setBlock(newBlockPos, state, 1);
                    player.getInventory().removeItem(player.getInventory().findSlotMatchingItem(new ItemStack(state.getBlock())), 1);
                } else {
                    player.sendSystemMessage(Component.literal("No blocks in inventory to place!"));
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("activate", Animation.LoopType.PLAY_ONCE).then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BuildersWandItemRenderer renderer = null;
            // Don't instantiate until ready. This prevents race conditions breaking things
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new BuildersWandItemRenderer();

                return this.renderer;
            }
        });
    }
}
