package com.mikitellurium.telluriumforge.gui.menu;

import net.minecraft.server.network.ServerPlayerEntity;

public interface TickingMenu {
    void tickMenu(ServerPlayerEntity player);
}
