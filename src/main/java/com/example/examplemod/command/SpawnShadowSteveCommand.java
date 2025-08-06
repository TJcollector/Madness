package com.example.examplemod.command;

import com.example.examplemod.entity.ShadowSteve;
import com.example.examplemod.register.ModEntities;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class SpawnShadowSteveCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spawnshadowsteve")
                .requires(source -> source.hasPermission(2)) // permission level 2 = cheat commands
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    ServerLevel level = player.getLevel();

                    ShadowSteve boss = ModEntities.SHADOW_STEVE.get().create(level);
                    if (boss != null) {
                        boss.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                        level.addFreshEntity(boss);

                        // Set thunderstorm for 5 minutes (6000 ticks)
                        level.setWeatherParameters(0, 6000, true, true);

                        context.getSource().sendSuccess(
                                new net.minecraft.network.chat.TextComponent("Spawned Shadow Steve and started thunderstorm!"), true);

                    }
                    return 1;
                })
        );
    }
}
