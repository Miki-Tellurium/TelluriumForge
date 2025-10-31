package com.mikitellurium.telluriumforge.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.IItemHandler;

public class ContainerUtils {
    public static IInventory fromItemHandler(IItemHandler itemHandler) {
        InventoryBasic inventory = new InventoryBasic(new TextComponentString(""), itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setInventorySlotContents(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }
}
