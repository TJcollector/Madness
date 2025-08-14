package com.example.examplemod.entity;

import com.example.examplemod.effect.ModEffects;
import com.example.examplemod.util.ShadowCurseHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class BroodMother extends Spider {
    public BroodMother(EntityType<? extends Spider> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }
    private final ServerBossEvent bossBar = new ServerBossEvent(
            new TextComponent("SHADOW BROOD MOTHER"), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
    // Optional: Override attributes if you want it tougher / faster
    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)  // custom health
                .add(Attributes.ATTACK_DAMAGE, 4.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }
    private boolean hasAnnouncedSpawn = false;
    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide && !hasAnnouncedSpawn) {
            hasAnnouncedSpawn = true;
            announceToNearbyPlayers("§5BroodMother has emerged from the void...");
        }
        bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        if (!level.isClientSide && tickCount % 300 == 0) { // summon a zombie every 10 seconds
            CaveSpider cavespider = EntityType.CAVE_SPIDER.create(level);
            if (cavespider != null) {
                cavespider.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                level.addFreshEntity(cavespider);
            }
        }

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));

        // Wander around when idle
        this.goalSelector.addGoal(5, new net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal(this, 1.0D));

        // Look around randomly
        this.goalSelector.addGoal(6, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));
    }



    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        this.spawnAtLocation(ForgeRegistries.ITEMS.getValue(new ResourceLocation("ultimatebow", "greatsword")));
        //this.spawnAtLocation(ForgeRegistries.ITEMS.getValue(new ResourceLocation("ultimatebow", "sspe")));
        // Bonus loot
        this.spawnAtLocation(new ItemStack(Items.NETHERITE_INGOT, 3));
        this.spawnAtLocation(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 2));
        this.spawnAtLocation(new ItemStack(Items.DIAMOND, 5));
//        this.spawnAtLocation(new ItemStack(Items.EXPERIENCE_BOTTLE, 16));
        //this.spawnAtLocation(new ItemStack(Items.NETHER_STAR));

        // Rare armor piece
//        ItemStack helm = new ItemStack(Items.NETHERITE_HELMET);
//        ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
//        ItemStack leg = new ItemStack(Items.NETHERITE_LEGGINGS);
//        ItemStack boots = new ItemStack(Items.NETHERITE_BOOTS);
//        ItemStack pick = new ItemStack(Items.NETHERITE_PICKAXE);
//        helm.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
//        helm.enchant(Enchantments.UNBREAKING, 5);
//        helm.setHoverName(new TextComponent("§6Void Helm"));
//        chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
//        chest.enchant(Enchantments.UNBREAKING, 5);
//        chest.setHoverName(new TextComponent("§6Void Chest plate"));
//        leg.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
//        leg.enchant(Enchantments.UNBREAKING, 5);
//        leg.setHoverName(new TextComponent("§6Void Leggings"));
//        boots.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
//        boots.enchant(Enchantments.UNBREAKING, 5);
//        boots.setHoverName(new TextComponent("§6Void Boots"));
//        pick.enchant(Enchantments.BLOCK_EFFICIENCY,10);
//        pick.enchant(Enchantments.UNBREAKING, 5);
//        pick.enchant(Enchantments.BLOCK_FORTUNE, 5);
//        pick.setHoverName(new TextComponent("§6Void Pickaxe"));
//        this.spawnAtLocation(helm);
//        this.spawnAtLocation(chest);
//        this.spawnAtLocation(leg);
//        this.spawnAtLocation(boots);
//        this.spawnAtLocation(pick);

    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossBar.removePlayer(player);
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PARROT_IMITATE_WITHER, 0.15F, 1.0F);
    }
    @Override
    public void die(DamageSource cause) {
        super.die(cause);


        if (!level.isClientSide) {

            //apply cursed
            if (cause.getEntity() instanceof ServerPlayer player) {
                ShadowCurseHandler.setCursedPlayer(player.getUUID());
                player.addEffect(new MobEffectInstance(ModEffects.SHADOW_CURSE.get(), Integer.MAX_VALUE, 0, false, true));
                player.sendMessage(new TextComponent("§cYou have been cursed by the Shadow Frost..."), player.getUUID());
            }
            announceToNearbyPlayers("§8Brood Mother has been defeated... §7But the frost shall rise again.");



            // Particle effects
            ((ServerLevel) level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY() + 1, getZ(), 80, 0.6, 0.6, 0.6, 0.05);
            ((ServerLevel) level).sendParticles(ParticleTypes.ASH, getX(), getY() + 1, getZ(), 60, 0.5, 0.5, 0.5, 0.02);
            ((ServerLevel) level).sendParticles(ParticleTypes.EXPLOSION, getX(), getY() + 1, getZ(), 20, 0.3, 0.3, 0.3, 0.0);

            // Sound effects
            level.playSound(null, blockPosition(), SoundEvents.WITHER_DEATH, SoundSource.HOSTILE, 2.5F, 0.7F);
            level.playSound(null, blockPosition(), SoundEvents.ENDER_DRAGON_DEATH, SoundSource.HOSTILE, 2.0F, 1.0F);
            level.playSound(null, blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.5F, 0.5F);

            // Summon soul remnants
            for (int i = 0; i < 5; i++) {
                AreaEffectCloud soulCloud = new AreaEffectCloud(level, getX(), getY(), getZ());
                soulCloud.setRadius(1.5F);
                soulCloud.setDuration(60);
                soulCloud.setParticle(ParticleTypes.SOUL);
                soulCloud.setFixedColor(0x5500AA); // deep purple
                level.addFreshEntity(soulCloud);
            }
        }
    }

    private void announceToNearbyPlayers(String message) {
        double radius = 32.0D;
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(radius));
        for (ServerPlayer player : players) {
            player.sendMessage(new TextComponent(message), this.getUUID());
        }
    }
}
