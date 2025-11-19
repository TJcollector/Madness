package com.example.examplemod.util;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketMessenger {

    private static boolean started = false;
    private static final int PORT = 55555; // change to any port you like

    public static void startServer() {
        // Ensure this only runs on the client
        if (!FMLEnvironment.dist.equals(Dist.CLIENT)) return;

        if (started) return;
        started = true;

        Thread thread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("[SocketMessenger] Listening on port " + PORT + "...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String finalLine = line;
                        Minecraft.getInstance().execute(() -> {
                            MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
                            if (server != null) {
                                server.getPlayerList().broadcastMessage(
                                        new TextComponent("§c[Admin] §f" + finalLine),
                                        ChatType.SYSTEM,
                                        Util.NIL_UUID
                                );
                            } else {
                                System.out.println("[SocketMessenger] Singleplayer server not ready yet.");
                            }
                        });
                    }

                    clientSocket.close();
                }
            } catch (Exception e) {
                System.err.println("[SocketMessenger] Error: " + e.getMessage());
                e.printStackTrace();
            }
        }, "SocketMessengerThread");

        thread.setDaemon(true); // so it won't prevent the game from closing
        thread.start();
    }
}
