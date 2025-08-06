package com.example.examplemod.event;

import com.example.examplemod.effect.ModEffects;
import com.example.examplemod.util.ShadowCurseHandler;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
@Mod.EventBusSubscriber(modid = "ultimatebow", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class CurseEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level;

        if (level.isClientSide || event.phase != TickEvent.Phase.END) return;

        if (ShadowCurseHandler.isPlayerCursed(player.getUUID())) {
            boolean isDaylight = level.isDay() && level.canSeeSky(player.blockPosition());

            int brightness = level.getBrightness(LightLayer.SKY, player.blockPosition());
            if (isDaylight && !player.isCreative()) {
                if (!player.isOnFire()) {
                    player.setSecondsOnFire(2); // Light them on fire briefly
                }
            }
        }
    }


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
