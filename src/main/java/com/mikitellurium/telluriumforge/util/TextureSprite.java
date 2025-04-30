package com.mikitellurium.telluriumforge.util;

import net.minecraft.util.Identifier;

public record TextureSprite(Identifier texture, int uOffset, int vOffset, int width, int height, int xPos, int yPos) {
}
