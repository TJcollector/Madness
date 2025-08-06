//package com.example.examplemod.item;
//
//import net.minecraft.world.item.BowItem;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.Arrow;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.Level;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//
//public class UltimateBowItem extends Item {
//
//    public UltimateBowItem(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack bowStack = player.getItemInHand(hand);
//
//        if (!level.isClientSide) {
//            Arrow arrow = new Arrow(level, player);
//            arrow.setBaseDamage(10.0); // Custom damage
//            arrow.setCritArrow(true); // Optional: always crit
//            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F); // speed and accuracy
//            level.addFreshEntity(arrow);
//
//            level.playSound(null, player.getX(), player.getY(), player.getZ(),
//                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
//        }
//
//        // Optional: consume durability
//        bowStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
//
//        return InteractionResultHolder.sidedSuccess(bowStack, level.isClientSide());
//    }
//}
package com.example.examplemod.item;

import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.stats.Stats;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class UltimateBowItem extends BowItem {

    public UltimateBowItem() {
        super(new Properties().stacksTo(1).durability(1250).tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        stack.enchant(Enchantments.POWER_ARROWS, 5);
        stack.enchant(Enchantments.INFINITY_ARROWS, 1);
        stack.enchant(Enchantments.FLAMING_ARROWS, 1);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        int useDuration = this.getUseDuration(stack) - timeLeft;

        if (!world.isClientSide) {
            Arrow arrow = new Arrow(world, player);
            arrow.setBaseDamage(10.0); // custom damage
            arrow.setCritArrow(true);
            arrow.setSecondsOnFire(100);
            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY; // Prevent pickup

            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.5F, 1.0F);
            world.addFreshEntity(arrow);

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return
                repair.getItem() == Items.NETHERITE_INGOT
                        || repair.getItem() == Items.DIAMOND
                        || super.isValidRepairItem(toRepair, repair);
    }
}
//use .\gradlew build to complile and make .jar file
//./gradlew runClient for testing