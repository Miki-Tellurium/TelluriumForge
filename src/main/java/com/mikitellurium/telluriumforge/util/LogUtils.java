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
    public static void chatMessage(String message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.sendSystemMessage(Component.literal(message));
        } else {
            TelluriumForge.logger().error("Could not send chat message: {}", message);
        }
    }

    public static void consoleLog(Object message) {
        System.out.println(message);
    }

    public static void consoleLogSequence(Object... objects) {
        consoleLog("---");
        for (Object object : objects) {
            consoleLog(object);
        }
    }

    public static void debugIsNull(String prefix, Object object) {
        String s = object == null ? "null" : "NOT null";
        consoleLog(prefix + ": " + s);
    }

    public static void debugIsEqual(String prefix, Object firstObj, Object secondObj) {
        String s = firstObj.equals(secondObj) ? "equal" : "NOT equal";
        consoleLog(prefix + ": " + s);
    }

}
