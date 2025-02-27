package com.mikitellurium.telluriumforge.util;

import com.mikitellurium.telluriumforge.TelluriumForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class LogUtils {

    /**
     * Send a chat message to the client if a client player is present.
     *
     * @param message The message to send to the client
     */
    public static void clientChatMessage(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.sendSystemMessage(Component.literal(message));
        } else {
            TelluriumForge.logger().error("Could not send chat message: {}", message);
        }
    }

}
