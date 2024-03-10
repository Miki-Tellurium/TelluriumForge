package com.mikitellurium.telluriumforge.util;

import com.mikitellurium.telluriumforge.TelluriumForge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class LogUtils {

    /**
     * Send a chat message to the client if a client player is present.
     *
     * @param message The message to send to the client
     */
    public static void clientChatMessage(String message) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(Text.of(message));
        } else {
            TelluriumForge.logger().error("Could not send chat message:" + message);
        }
    }

}
