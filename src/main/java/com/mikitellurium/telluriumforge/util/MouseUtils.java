package com.mikitellurium.telluriumforge.util;

import net.minecraft.client.util.math.Rect2i;

/**
 * The {@code MouseUtils} class provides utility methods for handling mouse-related operations.
 */
public class MouseUtils {

    /**
     * Checks if the specified mouse coordinates are within the boundaries of a rectangular area.
     *
     * @param mouseX The X-coordinate of the mouse
     * @param mouseY The Y-coordinate of the mouse
     * @param xPos   The X-coordinate of the top-left corner of the area
     * @param yPos   The Y-coordinate of the top-left corner of the area
     * @param sizeX  The width of the area
     * @param sizeY  The height of the area
     * @return {@code true} if the mouse is above the specified area, otherwise {@code false}
     */
    public static boolean isAboveArea(double mouseX, double mouseY, int xPos, int yPos, int sizeX, int sizeY) {
        return (mouseX >= xPos && mouseX <= xPos + sizeX) && (mouseY >= yPos && mouseY <= yPos + sizeY);
    }

    /**
     * Checks if the specified mouse coordinates are within the boundaries of a rectangular area.
     * This method takes a {@code Rect2i} object representing the area.
     *
     * @param mouseX The X-coordinate of the mouse
     * @param mouseY The Y-coordinate of the mouse
     * @param area   The rectangular area defined by a {@code Rect2i} object
     * @return {@code true} if the mouse is above the specified area, otherwise {@code false}
     */
    public static boolean isAboveArea(double mouseX, double mouseY, Rect2i area) {
        return isAboveArea(mouseX, mouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight());
    }

}
