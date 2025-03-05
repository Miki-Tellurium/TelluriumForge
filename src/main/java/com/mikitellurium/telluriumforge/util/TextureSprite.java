package com.mikitellurium.telluriumforge.util;

import net.minecraft.resources.ResourceLocation;

public record TextureSprite(ResourceLocation texture, int uOffset, int vOffset, int width, int height, int xPos, int yPos) {
}
