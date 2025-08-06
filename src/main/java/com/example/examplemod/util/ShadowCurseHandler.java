package com.example.examplemod.util;

import java.util.UUID;

public class ShadowCurseHandler {
    private static UUID cursedPlayer = null;

    public static void setCursedPlayer(UUID uuid) {
        cursedPlayer = uuid;
    }

    public static UUID getCursedPlayer() {
        return cursedPlayer;
    }

    public static boolean isPlayerCursed(UUID uuid) {
        return cursedPlayer != null && cursedPlayer.equals(uuid);
    }

    public static void clearCurse() {
        cursedPlayer = null;
    }
}
