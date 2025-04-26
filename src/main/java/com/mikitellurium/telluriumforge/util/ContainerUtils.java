package com.mikitellurium.telluriumforge.util;

import net.minecraft.world.SimpleContainer;
import net.neoforged.neoforge.items.IItemHandler;

public class ContainerUtils {

    /**
     * Get a {@link SimpleContainer} from the provided {@link IItemHandler}
     */
    public static SimpleContainer fromItemHandler(IItemHandler itemHandler) {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

}
