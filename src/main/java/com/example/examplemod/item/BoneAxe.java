package com.example.examplemod.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public class BoneAxe extends AxeItem {

    public BoneAxe() {
        super(Tiers.IRON, 13.0F, -3.1F, // Axe damage/speed
                new Item.Properties()
                        .durability(900) // You can tweak this
                        .tab(CreativeModeTab.TAB_TOOLS));
        MinecraftForge.EVENT_BUS.register(this); // Register for events if needed
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
        stack.enchant(Enchantments.SMITE, 5);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player)) return;

        boolean isHolding = player.getMainHandItem().equals(stack) || player.getOffhandItem().equals(stack);

        if (isHolding && !level.isClientSide) {
            //player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 220, 1, true, false)); // Haste II
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.IRON_INGOT
                || super.isValidRepairItem(toRepair, repair);
    }
}
