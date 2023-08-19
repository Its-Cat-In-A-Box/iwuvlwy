package net.ber.iwuvlwy.entity.custom;

import net.ber.iwuvlwy.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Objects;

public class SharkCatEntity extends TamableAnimal implements GeoEntity{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final double TEMPT_SPEED_MOD = .6D;
    public static final double WALK_SPEED_MOD = .4D;
    public static final double SPRINT_SPEED_MOD = 1.33D;
    private static final Ingredient TEMPT_INGRIDIENT = Ingredient.of(Items.COD, Items.SALMON);
    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(SharkCatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(SharkCatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(SharkCatEntity.class, EntityDataSerializers.INT);
    private float lieDownAmount;
    private float lieDownAmountO;
    private float lieDownAmountTail;
    private float lieDownAmountOTail;
    private float relaxStateOneAmount;
    private float relaxStateOneAmountO;
    private Level level;
    public static AttributeSupplier setAttributes(){
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16D)
                .add(Attributes.MOVEMENT_SPEED, WALK_SPEED_MOD).build();

    }
    private static final EntityDataAccessor<Boolean> SITTING  =
            SynchedEntityData.defineId(SharkCatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLEEPSITTING  =
            SynchedEntityData.defineId(SharkCatEntity.class, EntityDataSerializers.BOOLEAN);
    public SharkCatEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.level = pLevel;
    }

    @Override
    protected void registerGoals() {
        TemptGoal temptGoal = new SCatTemptGoal(this, 0.6D, TEMPT_INGRIDIENT, false);
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, temptGoal);
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, .8D, 10F, 2F, false));
        this.goalSelector.addGoal(2, new relaxOnOwnerGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, .5D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    static class relaxOnOwnerGoal extends Goal{
        private final SharkCatEntity sCat;
        @Nullable
        private Player owner;
        @Nullable
        private BlockPos goalPos;
        private int onBedTicks;

        public relaxOnOwnerGoal(SharkCatEntity sCat){
            this.sCat = sCat;
        }

        public boolean canUse(){
            if (!this.sCat.isTame()){ return false;}
            else if (this.sCat.isOrderedToSit()){ return false;}
            else {
                LivingEntity entity = this.sCat.getOwner();
                if (entity instanceof Player){
                    this.owner = (Player) entity;
                    if (!owner.isSleeping()){ return false;}
                    if (this.sCat.distanceToSqr(this.owner) > 100.D){ return false;}

                    BlockPos bedPos = this.owner.blockPosition();
                    BlockState bedState = this.sCat.level().getBlockState(bedPos);
                    if (bedState.is(BlockTags.BEDS)){
                        this.goalPos = bedState.getOptionalValue(BedBlock.FACING).map((p_28209_) -> bedPos.relative(p_28209_.getOpposite())).orElseGet(()-> new BlockPos(bedPos));
                        return !this.spaceIsOccupied();
                    }
                }
                return false;

            }

        }
        private boolean spaceIsOccupied(){
            for (SharkCatEntity sCat : this.sCat.level().getEntitiesOfClass(SharkCatEntity.class, (new AABB(this.goalPos)).inflate(2.0D))){
                if (sCat != this.sCat && sCat.isSitting()){
                    return true;
                }
            }
            return false;
        }

        public boolean canContinueToUse(){
            return this.sCat.isTame() && !this.sCat.isOrderedToSit() && this.owner != null && this.owner.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
        }

        public void start(){
            if (this.goalPos != null){
                this.sCat.setInSittingPose(false);
                this.sCat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
            }
        }

        public void tick() {
            if (this.owner != null && this.goalPos != null) {
                this.sCat.setInSittingPose(false);
                this.sCat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
                if (this.sCat.distanceToSqr(this.owner) < 2.5D) {
                    ++this.onBedTicks;
                    if (this.onBedTicks > this.adjustedTickDelay(16)) {
                        this.sCat.setSitting(true);

                    } else {
                        this.sCat.lookAt(this.owner, 45.0F, 45.0F);
                    }
                } else{
                    this.sCat.setSitting(false);
                }
            }


        }
    }



    static class SCatTemptGoal extends TemptGoal{
        @Nullable
        private Player selectedPlayer;
        private final SharkCatEntity sCat;

        public SCatTemptGoal(SharkCatEntity sCat, double pSpeedModifier, Ingredient pItems, boolean pCanScare){
            super(sCat, pSpeedModifier, pItems, pCanScare);
            this.sCat = sCat;
        }

        public void tick(){
            super.tick();
            if (this.selectedPlayer == null && this.mob.getRandom().nextInt(this.adjustedTickDelay(600)) == 0){
                this.selectedPlayer = this.player;
            } else if (this.mob.getRandom().nextInt(this.adjustedTickDelay(500)) == 0){
                this.selectedPlayer = null;
            }
        }

        protected boolean canScare(){
            return (this.selectedPlayer == null || !this.selectedPlayer.equals(this.player)) && super.canScare();
        }

        public boolean canUse(){ return super.canUse() && !this.sCat.isTame();}

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.SHARKCAT.get().create(pLevel);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SharkCatEntity> sharkCatEntityAnimationState) {
        if (sharkCatEntityAnimationState.isMoving()){
            sharkCatEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isOrderedToSit()){
            sharkCatEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("loaf", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        if (this.isSitting()){
            sharkCatEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("loaf_idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        sharkCatEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setSitting(pCompound.getBoolean("isSitting"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isSitting", this.isSitting());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(SLEEPSITTING, false);
    }
    public void setSleepSitting(boolean sitting){
        this.entityData.set(SLEEPSITTING, sitting);
        setSitting(sitting);
    }
    public void setSitting(boolean sitting){
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting(){
        return this.entityData.get(SITTING);
    }
    public boolean isSleepSitting(){ return this.entityData.get(SLEEPSITTING);}

    @Override
    public Team getTeam() {
        return super.getTeam();
    }

    public boolean canBeLeashed(Player player){
        return true;
    }

    @Override
    public void setTame(boolean pTamed) {
        super.setTame(pTamed);

    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.COD;

        if (item == itemForTaming && !isTame()){
            if (this.level.isClientSide){
                return InteractionResult.CONSUME;
            } else {
                if (!pPlayer.getAbilities().instabuild){
                    itemStack.shrink(1);
                }

                if (!ForgeEventFactory.onAnimalTame(this, pPlayer)){
                    if (!this.level.isClientSide){
                        super.tame(pPlayer);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        this.level.broadcastEntityEvent(this, (byte)7);
                        setSitting(true);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        if (isTame() && !this.level.isClientSide && pHand == InteractionHand.MAIN_HAND){
            setSitting(!isSitting());
            return InteractionResult.SUCCESS;
        }
        if (itemStack.getItem() == itemForTaming){
            return InteractionResult.PASS;
        }

        return super.mobInteract(pPlayer, pHand);

    }

}