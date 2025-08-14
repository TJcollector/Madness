//package com.example.examplemod.item;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.Vec3;
//import net.minecraft.network.chat.Component;
//
//public class LichKingSwordItem extends SwordItem {
//
//    public LichKingSwordItem() {
//        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(2048).tab(net.minecraft.world.item.CreativeModeTab.TAB_COMBAT));
//    }
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        stack.enchant(Enchantments.SHARPNESS, 10);
//        stack.enchant(Enchantments.UNBREAKING, 5);
//        stack.enchant(Enchantments.MOB_LOOTING, 5);
//        stack.enchant(Enchantments.MENDING, 1);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
//        if (!(entity instanceof Player player)) return;
//
//        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
//
//        if (isHolding) {
//            // Apply potion effects
//            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 3, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, true, false));
//
//            // Change weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(0, 6000, true, true);
//            }
//
//            // Freeze water under player
//            BlockPos pos = player.blockPosition();
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.WATER)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.ICE.defaultBlockState());
//            }
//
//            // 🔥 Despawn undead minions after 30 seconds
//            level.getEntitiesOfClass(Zombie.class, player.getBoundingBox().inflate(32)).forEach(zombie -> {
//                if (zombie.getCustomName() != null && "Undead Minion".equals(zombie.getCustomName().getString())) {
//                    long summonTime = zombie.getPersistentData().getLong("summonTime");
//                    if (level.getGameTime() - summonTime > 600) { // 30 seconds
//                        zombie.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
//                    }
//                }
//            });
//
//        } else {
//            // Revert weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(6000, 0, false, false);
//            }
//
//            // Unfreeze ice under player
//            BlockPos pos = player.blockPosition();
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
//            }
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!level.isClientSide && player.isShiftKeyDown()) {
//            for (int i = 0; i < 3; i++) {
//                Zombie ally = new Zombie(level);
//                ally.setPos(player.getX() + level.random.nextDouble() * 2 - 1,
//                        player.getY() + 1.0,
//                        player.getZ() + level.random.nextDouble() * 2 - 1);
//
//                ally.setCustomName(new TextComponent("Undead Minion"));
//                ally.setCustomNameVisible(true);
//                ally.setPersistenceRequired(); // Prevent despawn by default
//
//                ally.setTarget(findNearestHostile(level, ally));
//                // ✅ Initialize AI and rendering
//                if (level instanceof ServerLevel serverLevel) {
//                    ally.finalizeSpawn(serverLevel,
//                            level.getCurrentDifficultyAt(ally.blockPosition()),
//                            net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED,
//                            null, null);
//                }
//                level.addFreshEntity(ally);
//
//                // Schedule removal after 30 seconds
//                ally.getPersistentData().putLong("summonTime", level.getGameTime());
//            }
//            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
//    }
//    private LivingEntity findNearestHostile(Level level, Zombie ally) {
//        return level.getEntitiesOfClass(LivingEntity.class, ally.getBoundingBox().inflate(16), e ->
//                        e != ally && e.isAlive() && e instanceof net.minecraft.world.entity.monster.Monster)
//                .stream().findFirst().orElse(null);
//    }
//
//
//    @Override
//    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
//        return
//                repair.getItem() == Items.NETHERITE_INGOT
//                        || repair.getItem() == Items.DIAMOND
//                        || repair.getItem() == Items.SOUL_SAND
//                        || super.isValidRepairItem(toRepair, repair);
//    }
//}
////
//package com.example.examplemod.item;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//
//public class LichKingSwordItem extends SwordItem {
//
//    public LichKingSwordItem() {
//        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(2048).tab(CreativeModeTab.TAB_COMBAT));
//    }
//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        stack.enchant(Enchantments.SHARPNESS, 10);
//        stack.enchant(Enchantments.UNBREAKING, 5);
//        stack.enchant(Enchantments.MOB_LOOTING, 5);
//        stack.enchant(Enchantments.MENDING, 1);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
//        if (!(entity instanceof Player player)) return;
//
//        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
//
//        BlockPos pos = player.blockPosition();
//
//        if (isHolding) {
//            // Apply potion effects
//            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 3, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, true, false));
//
//            // Change weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(0, 6000, true, true);
//            }
//
//            // Freeze water under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.WATER)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.ICE.defaultBlockState());
//            }
//
//            // Despawn undead minions after 30 seconds
//            level.getEntitiesOfClass(Zombie.class, player.getBoundingBox().inflate(32)).forEach(zombie -> {
//                if (zombie.getCustomName() != null && "Undead Minion".equals(zombie.getCustomName().getString())) {
//                    long summonTime = zombie.getPersistentData().getLong("summonTime");
//                    long age = level.getGameTime() - summonTime;
//                    if (age > 600) {
//                        System.out.println("Removing undead: " + zombie.getCustomName() + " | Age: " + age);
//                        zombie.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
//                    }
//                }
//            });
//
//        } else {
//            // Revert weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(6000, 0, false, false);
//            }
//
//            // Unfreeze ice under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
//            }
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!level.isClientSide && player.isShiftKeyDown()) {
//            for (int i = 0; i < 3; i++) {
//                Zombie ally = new Zombie(level);
//
//                double x = player.getX() + level.random.nextDouble() * 2 - 1;
//                double y = player.getY() + 1.5; // Raised to avoid terrain collision
//                double z = player.getZ() + level.random.nextDouble() * 2 - 1;
//                ally.setPos(x, y, z);
//
//                ally.setCustomName(new TextComponent("Undead Minion"));
//                ally.setCustomNameVisible(true);
//                ally.setPersistenceRequired();
//                ally.setNoAi(false); // Ensure AI is active
//                ally.setCanPickUpLoot(false);
//
//                if (level instanceof ServerLevel serverLevel) {
//                    ally.finalizeSpawn(serverLevel,
//                            level.getCurrentDifficultyAt(ally.blockPosition()),
//                            net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED,
//                            null, null);
//                }
//
//                level.addFreshEntity(ally);
//                ally.getPersistentData().putLong("summonTime", level.getGameTime());
//
//                // Delay targeting until after spawn
//                LivingEntity target = findNearestHostile(level, ally.position());
//                if (target != null) {
//                    ally.setTarget(target);
//                }
//
//                System.out.println("Summoned undead at " + ally.position());
//            }
//
//            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
//    }
//
//    private LivingEntity findNearestHostile(Level level, Vec3 center) {
//        return level.getEntitiesOfClass(LivingEntity.class,
//                        new AABB(center.subtract(16, 4, 16), center.add(16, 4, 16)),
//                        e -> e.isAlive() && e instanceof net.minecraft.world.entity.monster.Monster)
//                .stream().findFirst().orElse(null);
//    }
//
//    @Override
//    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
//        return repair.getItem() == Items.NETHERITE_INGOT
//                || repair.getItem() == Items.DIAMOND
//                || repair.getItem() == Items.SOUL_SAND
//                || super.isValidRepairItem(toRepair, repair);
//    }
//}
//package com.example.examplemod.item;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//
//public class LichKingSwordItem extends SwordItem {
//
//    public LichKingSwordItem() {
//        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(2048).tab(CreativeModeTab.TAB_COMBAT));
//    }
//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        stack.enchant(Enchantments.SHARPNESS, 10);
//        stack.enchant(Enchantments.UNBREAKING, 5);
//        stack.enchant(Enchantments.MOB_LOOTING, 5);
//        stack.enchant(Enchantments.MENDING, 1);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
//        if (!(entity instanceof Player player)) return;
//
//        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
//        BlockPos pos = player.blockPosition();
//
//        if (isHolding) {
//            // Apply potion effects
//            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 3, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, true, false));
//
//            // Change weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(0, 6000, true, true);
//            }
//
//            // Freeze water under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.WATER)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.ICE.defaultBlockState());
//            }
//
//            // Despawn undead minions after 30 seconds using tickCount
//            level.getEntitiesOfClass(Zombie.class, player.getBoundingBox().inflate(32)).forEach(zombie -> {
//                if (zombie.getCustomName() != null && "Undead Minion".equals(zombie.getCustomName().getString())) {
//                    if (zombie.tickCount > 600) {
//                        System.out.println("Removing undead: " + zombie.getCustomName() + " | tickCount: " + zombie.tickCount);
//                        zombie.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
//                    }
//                }
//            });
//
//        } else {
//            // Revert weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(6000, 0, false, false);
//            }
//
//            // Unfreeze ice under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
//            }
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!level.isClientSide && player.isShiftKeyDown()) {
//            for (int i = 0; i < 3; i++) {
//                Zombie ally = new Zombie(level);
//
//                double x = player.getX() + level.random.nextDouble() * 2 - 1;
//                double y = player.getY() + 1.5; // Raised to avoid terrain collision
//                double z = player.getZ() + level.random.nextDouble() * 2 - 1;
//                ally.setPos(x, y, z);
//
//                ally.setCustomName(new TextComponent("Undead Minion"));
//                ally.setCustomNameVisible(true);
//                ally.setPersistenceRequired();
//                ally.setNoAi(false);
//                ally.setCanPickUpLoot(false);
//
//                if (level instanceof ServerLevel serverLevel) {
//                    ally.finalizeSpawn(serverLevel,
//                            level.getCurrentDifficultyAt(ally.blockPosition()),
//                            net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED,
//                            null, null);
//                }
//
//                level.addFreshEntity(ally);
//
//                // Delay targeting until after spawn
//                LivingEntity target = findNearestHostile(level, ally.position());
//                if (target != null) {
//                    ally.setTarget(target);
//                }
//
//                System.out.println("Summoned undead at " + ally.position());
//            }
//
//            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
//    }
//
//    private LivingEntity findNearestHostile(Level level, Vec3 center) {
//        return level.getEntitiesOfClass(LivingEntity.class,
//                        new AABB(center.subtract(16, 4, 16), center.add(16, 4, 16)),
//                        e -> e.isAlive() && e instanceof net.minecraft.world.entity.monster.Monster)
//                .stream().findFirst().orElse(null);
//    }
//
//    @Override
//    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
//        return repair.getItem() == Items.NETHERITE_INGOT
//                || repair.getItem() == Items.DIAMOND
//                || repair.getItem() == Items.SOUL_SAND
//                || super.isValidRepairItem(toRepair, repair);
//    }
//}






//package com.example.examplemod.item;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.scores.PlayerTeam;
//import net.minecraft.world.scores.Scoreboard;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//
//public class LichKingSwordItem extends SwordItem {
//
//    public LichKingSwordItem() {
//        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(2048).tab(CreativeModeTab.TAB_COMBAT));
//    }
//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        stack.enchant(Enchantments.SHARPNESS, 10);
//        stack.enchant(Enchantments.UNBREAKING, 5);
//        stack.enchant(Enchantments.MOB_LOOTING, 5);
//        stack.enchant(Enchantments.MENDING, 1);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
//        if (!(entity instanceof Player player)) return;
//
//        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
//        BlockPos pos = player.blockPosition();
//
//        if (isHolding) {
//            // Apply potion effects
//            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 3, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, true, false));
//
//            // Change weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(0, 6000, true, true);
//            }
//
//            // Freeze water under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.WATER)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.ICE.defaultBlockState());
//            }
//
//            // Despawn undead minions after 30 seconds using tickCount
//            level.getEntitiesOfClass(Zombie.class, player.getBoundingBox().inflate(32)).forEach(zombie -> {
//                if (zombie.getCustomName() != null && "Undead Minion".equals(zombie.getCustomName().getString())) {
//                    if (zombie.tickCount > 600) {
//                        System.out.println("Removing undead: " + zombie.getCustomName() + " | tickCount: " + zombie.tickCount);
//                        zombie.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
//                    }
//                }
//            });
//
//        } else {
//            // Revert weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(6000, 0, false, false);
//            }
//
//            // Unfreeze ice under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
//            }
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!level.isClientSide && player.isShiftKeyDown()) {
//            Scoreboard scoreboard = level.getScoreboard();
//            PlayerTeam team = scoreboard.getPlayerTeam("undead_minions");
//
//            if (team == null) {
//                team = scoreboard.addPlayerTeam("undead_minions");
//                team.setAllowFriendlyFire(true);
//                team.setSeeFriendlyInvisibles(true);
//            }
//
//            for (int i = 0; i < 3; i++) {
//                Zombie ally = new Zombie(level);
//
//                double x = player.getX() + level.random.nextDouble() * 2 - 1;
//                double y = player.getY() + 1.5;
//                double z = player.getZ() + level.random.nextDouble() * 2 - 1;
//                ally.setPos(x, y, z);
//
//                ally.setCustomName(new TextComponent("Undead Minion"));
//                ally.setCustomNameVisible(true);
//                ally.setPersistenceRequired();
//                ally.setNoAi(false);
//                ally.setCanPickUpLoot(false);
//
//                if (level instanceof ServerLevel serverLevel) {
//                    ally.finalizeSpawn(serverLevel,
//                            level.getCurrentDifficultyAt(ally.blockPosition()),
//                            net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED,
//                            null, null);
//                }
//
//                level.addFreshEntity(ally);
//
//                // Add to team to prevent friendly fire
//                scoreboard.addPlayerToTeam(ally.getStringUUID(), team);
//
//                // Delay targeting until after spawn
//                LivingEntity target = findNearestHostile(level, ally.position());
//                if (target != null) {
//                    ally.setTarget(target);
//                }
//
//                System.out.println("Summoned undead at " + ally.position());
//            }
//
//            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
//    }
//
//    private LivingEntity findNearestHostile(Level level, Vec3 center) {
//        return level.getEntitiesOfClass(LivingEntity.class,
//                        new AABB(center.subtract(16, 4, 16), center.add(16, 4, 16)),
//                        e -> e.isAlive() && e instanceof net.minecraft.world.entity.monster.Monster)
//                .stream().findFirst().orElse(null);
//    }
//
//    @Override
//    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
//        return repair.getItem() == Items.NETHERITE_INGOT
//                || repair.getItem() == Items.DIAMOND
//                || repair.getItem() == Items.SOUL_SAND
//                || super.isValidRepairItem(toRepair, repair);
//    }
//}


//package com.example.examplemod.item;
//
//import com.example.examplemod.entity.UndeadMinion;
//import com.example.examplemod.register.ModEntities;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class LichKingSwordItem extends SwordItem {
//
//    public LichKingSwordItem() {
//        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(2048).tab(CreativeModeTab.TAB_COMBAT));
//        MinecraftForge.EVENT_BUS.register(this); // Register the damage handler
//    }
//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        stack.enchant(Enchantments.SHARPNESS, 10);
//        stack.enchant(Enchantments.UNBREAKING, 5);
//        stack.enchant(Enchantments.MOB_LOOTING, 5);
//        stack.enchant(Enchantments.MENDING, 1);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
//        if (!(entity instanceof Player player)) return;
//
//        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
//        BlockPos pos = player.blockPosition();
//
//        if (isHolding) {
//            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 3, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, true, false));
//
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(0, 6000, true, true);
//            }
//
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.WATER)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.ICE.defaultBlockState());
//            }
//
//            level.getEntitiesOfClass(Zombie.class, player.getBoundingBox().inflate(32)).forEach(zombie -> {
//                if (zombie.getCustomName() != null && "Undead Minion".equals(zombie.getCustomName().getString())) {
//                    if (zombie.tickCount > 600) {
//                        System.out.println("Removing undead: " + zombie.getCustomName() + " | tickCount: " + zombie.tickCount);
//                        zombie.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
//                    }
//                }
//            });
//
//        } else {
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(6000, 0, false, false);
//            }
//
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
//            }
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!level.isClientSide && player.isShiftKeyDown()) {
//            for (int i = 0; i < 3; i++) {
//                UndeadMinion minion = ModEntities.UNDEAD_MINION.get().create(level);
//                if (minion != null) {
//                    minion.setSummonerUUID(player.getUUID());
//                    minion.setCustomName(new TextComponent("Undead Minion"));
//                    minion.setCustomNameVisible(true);
//                    minion.setPersistenceRequired();
//
//                    double x = player.getX() + level.random.nextDouble() * 2 - 1;
//                    double y = player.getY() + 1.5;
//                    double z = player.getZ() + level.random.nextDouble() * 2 - 1;
//                    minion.setPos(x, y, z);
//
//                    if (level instanceof ServerLevel serverLevel) {
//                        minion.finalizeSpawn(serverLevel,
//                                serverLevel.getCurrentDifficultyAt(minion.blockPosition()),
//                                MobSpawnType.MOB_SUMMONED, null, null);
//                    }
//                    level.addFreshEntity(minion);
//                }
//            }
//
//            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
//    }
//
//
//    private LivingEntity findNearestHostileExcluding(Level level, Vec3 center, Player summoner) {
//        return level.getEntitiesOfClass(LivingEntity.class,
//                        new AABB(center.subtract(16, 4, 16), center.add(16, 4, 16)),
//                        e -> e.isAlive()
//                                && e instanceof net.minecraft.world.entity.monster.Monster
//                                && !e.getUUID().equals(summoner.getUUID()))
//                .stream().findFirst().orElse(null);
//    }
//
//    @Override
//    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
//        return repair.getItem() == Items.NETHERITE_INGOT
//                || repair.getItem() == Items.DIAMOND
//                || repair.getItem() == Items.SOUL_SAND
//                || super.isValidRepairItem(toRepair, repair);
//    }
//
//    @SubscribeEvent
//    public void onLivingHurt(LivingHurtEvent event) {
//        if (event.getSource().getEntity() instanceof Zombie zombie
//                && zombie.getCustomName() != null
//                && "Undead Minion".equals(zombie.getCustomName().getString())
//                && event.getEntity() instanceof Player) {
//
//            // Prevent damage to the player from their own minion
//            event.setCanceled(true);
//        }
//    }
//}

//
//package com.example.examplemod.item;
//
//import com.example.examplemod.entity.UndeadMinion;
//import com.example.examplemod.register.ModEntities;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//import java.util.UUID;
//
//public class LichKingSwordItem extends SwordItem {
//
//    public LichKingSwordItem() {
//        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(2048).tab(CreativeModeTab.TAB_COMBAT));
//        MinecraftForge.EVENT_BUS.register(this); // Register the damage handler event listener
//    }
//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        stack.enchant(Enchantments.SHARPNESS, 10);
//        stack.enchant(Enchantments.UNBREAKING, 5);
//        stack.enchant(Enchantments.MOB_LOOTING, 5);
//        stack.enchant(Enchantments.MENDING, 1);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
//        if (!(entity instanceof Player player)) return;
//
//        boolean isHolding = player.getMainHandItem().equals(stack) || player.getOffhandItem().equals(stack);
//        BlockPos pos = player.blockPosition();
//
//        if (isHolding && !level.isClientSide) {
//            level.getEntitiesOfClass(net.minecraft.world.entity.Mob.class, player.getBoundingBox().inflate(32)).forEach(mob -> {
//                if (mob.getTarget() == player) {
//                    mob.setTarget(null);
//                }
//            });
//            // Apply buffs
//            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 120, 0, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 3, true, false));
//            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, true, false));
//
//            // Change weather to storm/thunder
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(0, 6000, true, true);
//            }
//
//            // Freeze water under player
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.WATER)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.ICE.defaultBlockState());
//            }
//
//            // Remove undead minions after 30 seconds (600 ticks)
////            level.getEntitiesOfClass(Zombie.class, player.getBoundingBox().inflate(32)).forEach(zombie -> {
////                if (zombie.getCustomName() != null && "Undead Minion".equals(zombie.getCustomName().getString())) {
////                    if (zombie.tickCount > 600) {
////                        zombie.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
////                    }
////                }
////            });
//
//        } else {
//            // Revert weather
//            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
//                serverLevel.setWeatherParameters(6000, 0, false, false);
//            }
//
//            // Unfreeze ice
//            BlockState below = level.getBlockState(pos.below());
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(pos.below(), Blocks.WATER.defaultBlockState());
//            }
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//
//        if (!level.isClientSide && player.isShiftKeyDown()) {
//            for (int i = 0; i < 3; i++) {
//                UndeadMinion minion = ModEntities.UNDEAD_MINION.get().create(level);
//
//                if (minion != null) {
//                    minion.setSummonerUuid(player.getUUID());
//                    minion.setCustomName(new TextComponent("Undead Minion"));
//                    minion.setCustomNameVisible(true);
//                    minion.setPersistenceRequired();
//
//                    double x = player.getX() + level.random.nextDouble() * 2 - 1;
//                    double y = player.getY() + 1.5;
//                    double z = player.getZ() + level.random.nextDouble() * 2 - 1;
//                    minion.setPos(x, y, z);
//
//                    if (level instanceof ServerLevel serverLevel) {
//                        minion.finalizeSpawn(serverLevel,
//                                serverLevel.getCurrentDifficultyAt(minion.blockPosition()),
//                                MobSpawnType.MOB_SUMMONED, null, null);
//                    }
//                    level.addFreshEntity(minion);
//                }
//            }
//            level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
//    }
//
//    @SubscribeEvent
//    public void onLivingHurt(LivingHurtEvent event) {
//        if (event.getSource().getEntity() instanceof UndeadMinion minion
//                && event.getEntity() instanceof Player player) {
//            UUID summonerUuid = minion.getSummonerUuid();
//            if (summonerUuid != null && summonerUuid.equals(player.getUUID())) {
//                event.setCanceled(true);
//            }
//        }
//    }
//
//    @Override
//    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
//        return repair.getItem() == Items.NETHERITE_INGOT
//                || repair.getItem() == Items.DIAMOND
//                || repair.getItem() == Items.SOUL_SAND
//                || super.isValidRepairItem(toRepair, repair);
//    }
//}





package com.example.examplemod.item;
//
import com.example.examplemod.UltimateBow;
//import com.example.examplemod.entity.UndeadMinion;
import com.example.examplemod.entity.CustomSnowman;
import com.example.examplemod.register.ModEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
        import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

import java.util.UUID;

public class LichKingSwordItem extends SwordItem {

    public LichKingSwordItem() {
        super(Tiers.NETHERITE, 25, -2.4F, new Item.Properties().durability(1200).tab(CreativeModeTab.TAB_COMBAT));
        MinecraftForge.EVENT_BUS.register(this); // Register the damage handler event listener
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.enchant(Enchantments.SHARPNESS, 10);
        stack.enchant(Enchantments.UNBREAKING, 3);
        stack.enchant(Enchantments.MOB_LOOTING, 5);
        stack.enchant(Enchantments.FIRE_ASPECT, 2);
//        stack.enchant(Enchantments.MENDING, 1);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player)) return;

        boolean isHolding = player.getMainHandItem().equals(stack) || player.getOffhandItem().equals(stack);
        BlockPos pos = player.blockPosition();

        if (isHolding && !level.isClientSide) {
            level.getEntitiesOfClass(net.minecraft.world.entity.Mob.class, player.getBoundingBox().inflate(32)).forEach(mob -> {
                if (mob.getTarget() == player) {
                    mob.setTarget(null);
                }
            });
            // Apply buffs
            //player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 4, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 3, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2, true, false));

            if (player.getHealth() < player.getMaxHealth()) {
                player.heal(0.25F); // heals half a heart every tick while holding
            }

            // Change weather to storm/thunder
            if (!level.isClientSide && level instanceof ServerLevel serverLevel && !serverLevel.isThundering()) {
                serverLevel.setWeatherParameters(0, 6000, true, true);
            }

//            // Freeze water under player
//            BlockPos belowPos = pos.below();
//            BlockState below = level.getBlockState(pos.below());
//            FluidState fluid = below.getFluidState();
//            if (fluid.is(Fluids.WATER) && fluid.isSource()) {
//                level.setBlockAndUpdate(belowPos, Blocks.ICE.defaultBlockState());
//
//                // ❄️ Particle effect: Snowflake burst
//                if (level instanceof ServerLevel serverLevel) {
//                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
//                            belowPos.getX() + 0.5, belowPos.getY() + 1.0, belowPos.getZ() + 0.5,
//                            10, 0.3, 0.3, 0.3, 0.01);
//                }
//
//                // 🔊 Sound effect: Ice forming
//                level.playSound(null, belowPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.5F, 1.2F);
//            }

            // Remove undead minions after 30 seconds (600 ticks)
            level.getEntitiesOfClass(SnowGolem.class, player.getBoundingBox().inflate(32)).forEach(minion -> {
                if (minion.getCustomName() != null && "Frost Minion".equals(minion.getCustomName().getString())) {
                    if (minion.tickCount > 1200) {
                        minion.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
                    }
                }
            });

        } else {
            // Revert weather
            if (!level.isClientSide && level instanceof ServerLevel serverLevel && serverLevel.isThundering()) {
                serverLevel.setWeatherParameters(6000, 0, false, false);
            }

//            // Unfreeze ice
//            BlockPos belowPos = pos.below();
//            BlockState below = level.getBlockState(belowPos);
//
//            if (below.is(Blocks.ICE)) {
//                level.setBlockAndUpdate(belowPos, Blocks.WATER.defaultBlockState());
//
//                // 💧 Particle effect: Splash burst
//                if (level instanceof ServerLevel serverLevel) {
//                    serverLevel.sendParticles(ParticleTypes.SPLASH,
//                            belowPos.getX() + 0.5, belowPos.getY() + 1.0, belowPos.getZ() + 0.5,
//                            10, 0.3, 0.3, 0.3, 0.05);
//                }
//
//                // 🔊 Sound effect: Water splash
//                level.playSound(null, belowPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.5F, 1.0F);
//            }

        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isShiftKeyDown()) {
            try {
                System.out.println("Attempting to spawn snowmen");

                for (int i = 0; i < 3; i++) {
                    CustomSnowman snowman = ModEntities.CUSTOM_SNOWMAN.get().create(level);
                    if (snowman != null) {
                        snowman.setCustomName(new TextComponent("Frost Minion"));
                        snowman.setCustomNameVisible(true);
                        snowman.setPersistenceRequired();
                        snowman.setPumpkin(false); // if you don’t want pumpkin
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
                        System.out.println("Summoned snowman at " + snowman.getX() + ", " + snowman.getY() + ", " + snowman.getZ());
                    } else {
                        System.out.println("Failed to create snowman entity");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // 🔥 this will show the exact issue in logs
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }


    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.NETHERITE_INGOT
                || repair.getItem() == Items.DIAMOND
                || repair.getItem() == Items.SOUL_SAND
                || super.isValidRepairItem(toRepair, repair);
    }
}