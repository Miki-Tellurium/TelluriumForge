package com.mikitellurium.telluriumforge.util;

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
}
