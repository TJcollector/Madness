

package com.example.examplemod.event;

import com.example.examplemod.effect.ModEffects;
import com.example.examplemod.util.ShadowCurseHandler;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "ultimatebow", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class CurseEvents {
    // Per-tick curse logic
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (event.phase != TickEvent.Phase.END || player.level.isClientSide) return;

        if (ShadowCurseHandler.isPlayerCursed(player.getUUID())) {
            // Ensure MobEffect is applied (in case login just happened)
            if (!player.hasEffect(ModEffects.SHADOW_CURSE.get())) {
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(ModEffects.SHADOW_CURSE.get(), Integer.MAX_VALUE, 0, false, true));
            }

            // Fire in daylight
            boolean isDaylight = player.level.isDay() && player.level.canSeeSky(player.blockPosition());
            if (isDaylight && !player.isCreative() && !player.isOnFire()) {
                player.setSecondsOnFire(2);
            }
        }
    }

    // Death handler
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (ShadowCurseHandler.isPlayerCursed(player.getUUID())) {
            ShadowCurseHandler.clearCurse();
            player.removeEffect(ModEffects.SHADOW_CURSE.get());
            player.sendMessage(new TextComponent("§7The curse of the frost fades as death claims you."), player.getUUID());
        }
    }
}
