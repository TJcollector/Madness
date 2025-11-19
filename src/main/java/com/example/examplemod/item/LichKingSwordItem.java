package com.example.examplemod.item;

import com.example.examplemod.entity.CustomSnowman;
import com.example.examplemod.register.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class LichKingSwordItem extends SwordItem {

    public LichKingSwordItem() {
        super(Tiers.NETHERITE, 25, -2.4F, new Item.Properties().durability(500).tab(CreativeModeTab.TAB_COMBAT));
        MinecraftForge.EVENT_BUS.register(this);
    }

    int det = 0;

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player)) return;

        boolean isHolding = player.getMainHandItem().equals(stack) || player.getOffhandItem().equals(stack);

        if (isHolding && !level.isClientSide) {
            // Remove hostility from mobs targeting the player
            level.getEntitiesOfClass(net.minecraft.world.entity.Mob.class, player.getBoundingBox().inflate(32)).forEach(mob -> {
                if (mob.getTarget() == player) {
                    mob.setTarget(null);
                }
            });

            // Buff effects
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 3, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1, true, false));
            if (det == 10) {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 4, true, false));
            }
            if (det==20){
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 4, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 4, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2, true, false));
            }
            if (player.getHealth() < player.getMaxHealth()) player.heal(0.25F);

            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
                serverLevel.setWeatherParameters(0, 6000, true, true);
            }

            // Remove old frost minions
            level.getEntitiesOfClass(SnowGolem.class, player.getBoundingBox().inflate(32)).forEach(minion -> {
                if (minion.getCustomName() != null && "Frost Minion".equals(minion.getCustomName().getString())) {
                    if (minion.tickCount > 1200) {
                        minion.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
                    }
                }
            });
        } else {
            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
                serverLevel.setWeatherParameters(6000, 0, false, false);
            }
        }

        // Client-side particles
        if (isHolding && level.isClientSide) {
            int souls = stack.getOrCreateTag().getInt("SoulCount");
            var particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            if (souls >= 50 && souls < 100) particle = ParticleTypes.FLAME;
            else if (souls >= 100 && souls<200) particle = ParticleTypes.SOUL_FIRE_FLAME;
            else if (souls >= 200) particle = ParticleTypes.SOUL;

            Vec3 handPos = player.position().add(0, 0, 0);
            for (int i = 0; i < 5; i++) {
                double dx = (level.random.nextDouble() - 0.5) * 0.1;
                double dz = (level.random.nextDouble() - 0.5) * 0.1;
                level.addParticle(particle, handPos.x, handPos.y, handPos.z, dx, 0.02, dz);
            }

            if (!particle.equals(ParticleTypes.CAMPFIRE_COSY_SMOKE)) {
                double time = player.tickCount * 0.2;
                double radius = 0.5;
                double x = player.getX() + Math.cos(time) * radius;
                double y = player.getY() + 2;
                double z = player.getZ() + Math.sin(time) * radius;
                level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        int souls = stack.getOrCreateTag().getInt("SoulCount");

        // Summon Frost Minions
        if (!level.isClientSide && player.isShiftKeyDown()) {
            for (int i = 0; i < 3; i++) {
                CustomSnowman snowman = ModEntities.CUSTOM_SNOWMAN.get().create(level);
                if (snowman != null) {
                    snowman.setCustomName(new TextComponent("Frost Minion"));
                    snowman.setCustomNameVisible(true);
                    snowman.setPersistenceRequired();
                    snowman.setPumpkin(false);
                    snowman.setPos(
                            player.getX() + level.random.nextDouble() * 2 - 1,
                            player.getY() + 1.5,
                            player.getZ() + level.random.nextDouble() * 2 - 1
                    );

                    if (level instanceof ServerLevel serverLevel) {
                        snowman.finalizeSpawn(serverLevel,
                                serverLevel.getCurrentDifficultyAt(snowman.blockPosition()),
                                MobSpawnType.MOB_SUMMONED, null, null);
                    }
                    level.addFreshEntity(snowman);
                }
            }
        }
        // Horizontal Lightning Zap (100+ souls)
        else if (!level.isClientSide && !player.isShiftKeyDown() && souls >= 100) {
            Vec3 look = player.getLookAngle().normalize();
            Vec3 pos = player.position().add(0, 1.5, 0);

            for (int i = 1; i <= 20; i++) {
                Vec3 zapPos = pos.add(look.scale(i));
                ((ServerLevel) level).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        zapPos.x, zapPos.y, zapPos.z,
                        20, 0.3, 0.3, 0.3, 0.05);

                for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class,
                        new AABB(zapPos, zapPos).inflate(2.0))) {
                    if (target != player) {
                        target.hurt(DamageSource.LIGHTNING_BOLT, 20.0F);
                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3));
                        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));

                        // Necrotic explosion if killed
                        if (!target.isAlive()) {
                            spawnNecroticExplosion(level, target.blockPosition(), player);
                        }
                    }
                }
            }
            level.playSound(null, player.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                    SoundSource.PLAYERS, 3.0F, 1.0F);
            stack.hurtAndBreak(10, player, (p) -> p.broadcastBreakEvent(hand));
        }
        // Frost Slash (<100 souls)
        else if (!level.isClientSide && !player.isShiftKeyDown()) {
            Vec3 look = player.getLookAngle().normalize().scale(1.5);
            Vec3 pos = player.position().add(0, 1.5, 0);

            for (int i = 1; i <= 10; i++) {
                Vec3 slashPos = pos.add(look.scale(i));

                ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE,
                        slashPos.x, slashPos.y, slashPos.z,
                        10, 0.2, 0.2, 0.2, 0.01);

                BlockPos bp = new BlockPos(slashPos.x, slashPos.y - 1, slashPos.z);
                if (level.getBlockState(bp).getFluidState().is(Fluids.WATER)) {
                    level.setBlockAndUpdate(bp, Blocks.ICE.defaultBlockState());
                } else if (level.getBlockState(bp).isSolidRender(level, bp) && level.isEmptyBlock(bp.above())) {
                    level.setBlockAndUpdate(bp.above(), Blocks.SNOW.defaultBlockState());
                }

                for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class,
                        new AABB(slashPos, slashPos).inflate(1.0))) {
                    if (target != player) {
                        target.hurt(DamageSource.playerAttack(player), 6.0F);
                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));

                        // Necrotic explosion if killed
                        if (!target.isAlive()) {
                            spawnNecroticExplosion(level, target.blockPosition(), player);
                        }
                    }
                }
            }
            stack.hurtAndBreak(5, player, (p) -> p.broadcastBreakEvent(hand));
            level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP,
                    SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    // Helper: necrotic explosion on kills
    private void spawnNecroticExplosion(Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;

            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    1, 0, 0, 0, 0.0);

            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    150, 1.5, 1.5, 1.5, 0.05);

            serverLevel.sendParticles(ParticleTypes.SOUL,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    100, 2.0, 2.0, 2.0, 0.05);

            for (LivingEntity nearby : level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(pos).inflate(4))) {
                if (nearby != player && nearby.isAlive()) {
                    nearby.hurt(DamageSource.MAGIC, 4.0F);
                    nearby.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                }
            }

            level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 0.6F);
        }
    }

    @SubscribeEvent
    public void onMobKilled(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LichKingSwordItem)) return;

        if (event.getEntityLiving().getHealth() - event.getAmount() <= 0) {
            spawnNecroticExplosion(player.level, event.getEntityLiving().blockPosition(), player);

            // Soul counter and enchantments
            int souls = stack.getOrCreateTag().getInt("SoulCount") + 1;
            stack.getOrCreateTag().putInt("SoulCount", souls);
            stack.getEnchantmentTags().clear();

            // Soul-based enchantments (same as original code)
            if (souls<5){
                player.displayClientMessage(new TextComponent("§7The sword rumbling (" + souls + " souls)"), true);
            }
            else if (souls >= 5 && souls < 10) {
                stack.enchant(Enchantments.SHARPNESS, 1);
                player.displayClientMessage(new TextComponent("§7The sword whispers… (" + souls + " souls)"), true);
            } else if (souls >= 10 && souls < 15) {
                stack.enchant(Enchantments.SHARPNESS, 2);
                stack.enchant(Enchantments.UNBREAKING, 1);
                player.displayClientMessage(new TextComponent("§7The sword grows sharper… (" + souls + " souls)"), true);
            } else if (souls >= 15 && souls < 20) {
                stack.enchant(Enchantments.SHARPNESS, 3);
                stack.enchant(Enchantments.UNBREAKING, 1);
                stack.enchant(Enchantments.MOB_LOOTING, 1);
                player.displayClientMessage(new TextComponent("§7The blade hungers for more… (" + souls + " souls)"), true);
            } else if (souls >= 20 && souls < 25) {
                stack.enchant(Enchantments.SHARPNESS, 3);
                stack.enchant(Enchantments.UNBREAKING, 2);
                stack.enchant(Enchantments.MOB_LOOTING, 2);
                player.displayClientMessage(new TextComponent("§7Power courses through the blade… (" + souls + " souls)"), true);
            } else if (souls >= 25 && souls < 30) {
                stack.enchant(Enchantments.SHARPNESS, 4);
                stack.enchant(Enchantments.UNBREAKING, 2);
                stack.enchant(Enchantments.MOB_LOOTING, 3);
                player.displayClientMessage(new TextComponent("§7The sword thirsts… (" + souls + " souls)"), true);
            } else if (souls >= 30 && souls < 35) {
                stack.enchant(Enchantments.SHARPNESS, 5);
                stack.enchant(Enchantments.UNBREAKING, 3);
                stack.enchant(Enchantments.MOB_LOOTING, 4);
                player.displayClientMessage(new TextComponent("§7The sword radiates power… (" + souls + " souls)"), true);
            } else if (souls >= 35 && souls < 40) {
                stack.enchant(Enchantments.SHARPNESS, 6);
                stack.enchant(Enchantments.UNBREAKING, 3);
                stack.enchant(Enchantments.MOB_LOOTING, 4);
                player.displayClientMessage(new TextComponent("§7The blade surges with energy… (" + souls + " souls)"), true);
            } else if (souls >= 40 && souls < 45) {
                stack.enchant(Enchantments.SHARPNESS, 7);
                stack.enchant(Enchantments.UNBREAKING, 3);
                stack.enchant(Enchantments.MOB_LOOTING, 4);
                stack.enchant(Enchantments.FIRE_ASPECT, 1);
                player.displayClientMessage(new TextComponent("§7The sword burns with fire… (" + souls + " souls)"), true);
            } else if (souls >= 45 && souls < 50) {
                stack.enchant(Enchantments.SHARPNESS, 8);
                stack.enchant(Enchantments.UNBREAKING, 3);
                stack.enchant(Enchantments.MOB_LOOTING, 5);
                stack.enchant(Enchantments.FIRE_ASPECT, 2);
                player.displayClientMessage(new TextComponent("§7The blade blazes… (" + souls + " souls)"), true);
            } else if (souls >= 50 && souls < 100) {
                stack.enchant(Enchantments.SHARPNESS, 10);
                stack.enchant(Enchantments.UNBREAKING, 3);
                stack.enchant(Enchantments.MOB_LOOTING, 5);
                stack.enchant(Enchantments.FIRE_ASPECT, 2);
                stack.enchant(Enchantments.MENDING, 1);
                player.displayClientMessage(new TextComponent("§bThe Lich King’s power grows… (" + souls + " souls)"), true);
            } else if (souls >= 100 && souls<200) {
                stack.enchant(Enchantments.SHARPNESS, 10);
                stack.enchant(Enchantments.UNBREAKING, 3);
                stack.enchant(Enchantments.MOB_LOOTING, 5);
                stack.enchant(Enchantments.FIRE_ASPECT, 2);
                stack.enchant(Enchantments.MENDING, 1);
                stack.enchant(Enchantments.SWEEPING_EDGE, 5);
                player.displayClientMessage(new TextComponent("§cThe blade is hungering! (" + souls + " souls)"), true);

                det = 10;
            }
            else if (souls >= 200) {
                stack.enchant(Enchantments.SHARPNESS, 20);
                stack.enchant(Enchantments.UNBREAKING, 5);
                stack.enchant(Enchantments.MOB_LOOTING, 10);
                stack.enchant(Enchantments.FIRE_ASPECT, 4);
                stack.enchant(Enchantments.MENDING, 1);
                stack.enchant(Enchantments.SWEEPING_EDGE, 10);
                if (souls == 200) {
                    player.displayClientMessage(new TextComponent("§cThe blade reached full potential! (" + souls + " souls)"), true);
                }
                det = 20;
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.NETHERITE_INGOT
                || repair.getItem() == Items.DIAMOND
                || repair.getItem() == Items.SOUL_SAND
                || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int souls = stack.getOrCreateTag().getInt("SoulCount");
        tooltip.add(new TextComponent("§bSouls Collected: §f" + souls));

        if (souls < 5) tooltip.add(new TextComponent("§7A blade waiting for its first few victim..."));
        else if (souls < 10) tooltip.add(new TextComponent("§7The blade tastes blood for the first time (5+ souls)"));
        else if (souls < 15) tooltip.add(new TextComponent("§7The blade hungers for more... (10+ souls)"));
        else if (souls < 20) tooltip.add(new TextComponent("§7The Lich King’s will strengthens (15+ souls)"));
        else if (souls < 25) tooltip.add(new TextComponent("§7The blade radiates power (20+ souls)"));
        else if (souls < 30) tooltip.add(new TextComponent("§7Souls empower the blade (25+ souls)"));
        else if (souls < 35) tooltip.add(new TextComponent("§7The Lich King’s wrath stirs (30+ souls)"));
        else if (souls < 40) tooltip.add(new TextComponent("§bThe blade burns with fiery souls (35+ souls)"));
        else if (souls < 45) tooltip.add(new TextComponent("§bThe blade hungers for devastation (40+ souls)"));
        else if (souls < 50) tooltip.add(new TextComponent("§bThe Lich King’s power grows... (45+ souls)"));
        else if (souls < 100) tooltip.add(new TextComponent("§aThe blade hums with overwhelming power (50+ souls)"));
        else tooltip.add(new TextComponent("§cThe Lich King’s wrath is unleashed (100+ souls)"));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrCreateTag().getInt("SoulCount") >= 0;
    }
}

