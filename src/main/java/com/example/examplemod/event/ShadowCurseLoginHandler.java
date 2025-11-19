package com.example.examplemod.event;

import com.example.examplemod.effect.ModEffects;
import com.example.examplemod.util.ShadowCurseHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShadowCurseLoginHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();

        // If the effect is already on the player (from save), sync it with the handler
        if (player.hasEffect(ModEffects.SHADOW_CURSE.get())) {
            ShadowCurseHandler.setCursedPlayer(player.getUUID());
        }

        // If handler already knows they’re cursed, but no effect on them, reapply it
        else if (ShadowCurseHandler.isPlayerCursed(player.getUUID())) {
            player.addEffect(new MobEffectInstance(ModEffects.SHADOW_CURSE.get(),
                    Integer.MAX_VALUE, 0, false, true));
        }
    }
}
