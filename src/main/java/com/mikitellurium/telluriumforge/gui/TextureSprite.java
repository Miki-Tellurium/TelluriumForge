package com.mikitellurium.telluriumforge.gui;

import net.minecraft.util.ResourceLocation;

public class TextureSprite {
    private final ResourceLocation texture;
    private final int uOffset;
    private final int vOffset;
    private final int width;
    private final int height;
    private final int xPos;
    private final int yPos;

    public TextureSprite(ResourceLocation texture, int uOffset, int vOffset, int width, int height, int xPos, int yPos) {
        this.texture = texture;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public ResourceLocation texture() {
        return texture;
    }

    public int uOffset() {
        return uOffset;
    }

    public int vOffset() {
        return vOffset;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int xPos() {
        return xPos;
    }

    public int yPos() {
        return yPos;
    }
}
