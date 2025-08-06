////package com.example.examplemod.entity;
////
////import net.minecraft.world.damagesource.DamageSource;
////import net.minecraft.world.entity.EntityType;
////import net.minecraft.world.entity.LivingEntity;
////import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
////import net.minecraft.world.entity.ai.targeting.TargetingConditions;
////import net.minecraft.world.entity.monster.Zombie;
////import net.minecraft.world.entity.player.Player;
////import net.minecraft.world.level.Level;
////import net.minecraft.world.level.block.Block;
////import net.minecraft.world.level.block.Blocks;
////
////import java.util.UUID;
////
////public class UndeadMinion extends Zombie {
////
////    private UUID summonerUuid;
////    private boolean customGoalsAdded = false;
////
////    public UndeadMinion(EntityType<? extends Zombie> type, Level level) {
////        super(type, level);
////    }
////
////    public void setSummonerUuid(UUID uuid) {
////        this.summonerUuid = uuid;
////    }
////
////    public UUID getSummonerUuid() {
////        return summonerUuid;
////    }
////
////    @Override
////    public void tick() {
////        super.tick();
////
////        if (!this.level.isClientSide && summonerUuid != null && !customGoalsAdded) {
////            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true
////            ));
////
////            customGoalsAdded = true;
////        }
////
////        // Defensive: remove summoner as target if somehow still set
////        if (this.getTarget() instanceof Player player && player.getUUID().equals(summonerUuid)) {
////            this.setTarget(null);
////        }
////        // ❄️ Auto-despawn after 30 seconds (600 ticks)
////        if (!this.level.isClientSide && this.tickCount > 600) {
////            this.level.levelEvent(2001, this.blockPosition(), Block.getId(Blocks.SOUL_SAND.defaultBlockState())); // soul explosion
////            this.remove(RemovalReason.DISCARDED);
////        }
////    }
////
////    @Override
////    public boolean hurt(DamageSource source, float amount) {
////        if (source.getEntity() instanceof Player player
////                && player.getUUID().equals(summonerUuid)) {
////            return false;
////        }
////        return super.hurt(source, amount);
////    }
////}
//// UndeadMinion.java
//
//
//
////package com.example.examplemod.entity;
////
////import net.minecraft.world.entity.EntityType;
////import net.minecraft.world.entity.MobSpawnType;
////import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
////import net.minecraft.world.entity.monster.Zombie;
////import net.minecraft.world.entity.player.Player;
////import net.minecraft.world.level.Level;
////
////import java.util.UUID;
////
////public class UndeadMinion extends Zombie {
////    private UUID summonerUuid;
////
////    public UndeadMinion(EntityType<? extends Zombie> type, Level level) {
////        super(type, level);
////    }
////
////    public void setSummonerUUID(UUID uuid) {
////        this.summonerUuid = uuid;
////    }
////
////    public UUID getSummonerUUID() {
////        return this.summonerUuid;
////    }
////
////    @Override
////    protected void registerGoals() {
////        super.registerGoals();
////
////        this.targetSelector.addGoal(2,
////                new NearestAttackableTargetGoal<>(this, Player.class, true, player -> {
////                    return this.summonerUuid == null || !player.getUUID().equals(this.summonerUuid);
////                }));
////    }
////}
//
//
//
//
//
//
//
////package com.example.examplemod.entity;
////
////import net.minecraft.world.damagesource.DamageSource;
////import net.minecraft.world.entity.EntityType;
////import net.minecraft.world.entity.LivingEntity;
////import net.minecraft.world.entity.ai.goal.*;
////import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
////import net.minecraft.world.entity.monster.Monster;
////import net.minecraft.world.entity.monster.Zombie;
////import net.minecraft.world.entity.player.Player;
////import net.minecraft.world.level.Level;
////
////import java.util.UUID;
////
////public class UndeadMinion extends Monster {
////    private UUID summonerUuid;
////
////    public UndeadMinion(EntityType<? extends Monster> type, Level level) {
////        super(type, level);
////    }
////
////    public void setSummonerUuid(UUID uuid) {
////        this.summonerUuid = uuid;
////    }
////
////    public UUID getSummonerUuid() {
////        return this.summonerUuid;
////    }
////
////    @Override
////    protected void registerGoals() {
////        // Add custom behavior here. For example:
////        this.goalSelector.addGoal(1, new FloatGoal(this));
////        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
////        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
////        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
////        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
////
////        // Attack all hostile mobs, but NOT players or the summoner
////        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true,
////                target -> !(target instanceof Player)));
////    }
////
////    @Override
////    public boolean hurt(DamageSource source, float amount) {
////        if (source.getEntity() instanceof Player player && player.getUUID().equals(this.summonerUuid)) {
////            return false;
////        }
////        return super.hurt(source, amount);
////    }
////}
////
////package com.example.examplemod.entity;
////
////import net.minecraft.world.entity.EntityType;
////import net.minecraft.world.entity.Mob;
////import net.minecraft.world.entity.MobType;
////import net.minecraft.world.entity.ai.goal.FloatGoal;
////import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
////import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
////import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
////import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
////import net.minecraft.world.entity.monster.Monster;
////import net.minecraft.world.entity.player.Player;
////import net.minecraft.world.level.Level;
////
////import java.util.UUID;
////
////public class UndeadMinion extends Monster {
////
////    private UUID summonerUuid;
////
////    public UndeadMinion(EntityType<? extends Monster> type, Level level) {
////        super(type, level);
////        this.xpReward = 0;
////    }
////
////    public void setSummonerUuid(UUID uuid) {
////        this.summonerUuid = uuid;
////    }
////
////    public UUID getSummonerUuid() {
////        return this.summonerUuid;
////    }
////
////    @Override
////    protected void registerGoals() {
////        this.goalSelector.addGoal(0, new FloatGoal(this));
////        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
////        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
////        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
////
////        // Attack all players EXCEPT the summoner
////        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, player ->
////                !player.getUUID().equals(this.summonerUuid)));
////    }
////
////    @Override
////    public MobType getMobType() {
////        return MobType.UNDEAD;
////    }
////
////    @Override
////    public boolean isPersistenceRequired() {
////        return true;
////    }
////}
//
//
//
//package com.example.examplemod.entity;
//
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.*;
//import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
//import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
//import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import java.util.UUID;
//
//public class UndeadMinion extends Monster {
//
//    private UUID summonerUuid;
//
//    public UndeadMinion(EntityType<? extends Monster> type, Level level) {
//        super(type, level);
//    }
//
//    public void setSummonerUuid(UUID uuid) {
//        this.summonerUuid = uuid;
//    }
//
//    public UUID getSummonerUuid() {
//        return this.summonerUuid;
//    }
//
//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
//        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
//        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
//
//        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, target ->
//                !(target.getUUID().equals(this.getSummonerUuid()))
//        ));
//
////        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, target -> true));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
//
//
//
//    }
//
//    @Override
//    public boolean hurt(DamageSource source, float amount) {
//        // Prevent summoner from damaging their own minion
//        if (source.getEntity() instanceof Player player) {
//            if (player.getUUID().equals(this.summonerUuid)) {
//                return false;
//            }
//        }
//        return super.hurt(source, amount);
//    }
//
//    @Override
//    public boolean canAttack(LivingEntity target) {
//        if (target instanceof Player player) {
//            return summonerUuid == null || !player.getUUID().equals(summonerUuid);
//        }
//        return super.canAttack(target);
//    }
//}
