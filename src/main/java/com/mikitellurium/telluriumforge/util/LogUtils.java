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
    public static void chatMessage(String message) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(Text.literal(message));
        } else {
            TelluriumForge.logger().error("Could not send chat message: {}", message);
        }
    }

    /**
     * Log an object as a string on the console
     */
    public static void consoleLog(Object obj) {
        System.out.println(obj);
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
