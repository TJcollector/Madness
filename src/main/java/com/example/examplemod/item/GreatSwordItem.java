package com.example.examplemod.item;
//
import com.example.examplemod.UltimateBow;
//import com.example.examplemod.entity.UndeadMinion;
import com.example.examplemod.entity.CustomSnowman;
import com.example.examplemod.register.ModEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

import java.util.UUID;

public class GreatSwordItem extends SwordItem {

    public GreatSwordItem() {
        super(Tiers.NETHERITE, 10, -2.4F, new Item.Properties().durability(1000).tab(CreativeModeTab.TAB_COMBAT));
        MinecraftForge.EVENT_BUS.register(this); // Register the damage handler event listener
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.enchant(Enchantments.SHARPNESS, 5);
        stack.enchant(Enchantments.UNBREAKING, 3);
        stack.enchant(Enchantments.MOB_LOOTING, 3);
        stack.enchant(Enchantments.FIRE_ASPECT, 2);

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
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1, true, false));
        }

            // Unfreeze ice
    }
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.NETHERITE_INGOT
                || super.isValidRepairItem(toRepair, repair);
    }
}
