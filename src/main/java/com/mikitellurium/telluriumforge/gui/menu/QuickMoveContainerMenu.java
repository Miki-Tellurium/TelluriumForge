package com.mikitellurium.telluriumforge.gui.menu;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class QuickMoveContainerMenu extends ScreenHandler {

    private final int invSize;

    protected QuickMoveContainerMenu(@Nullable ScreenHandlerType<?> menuType, int id, int invSize) {
        super(menuType, id);
        this.invSize = invSize;
    }

    // Adapted for Fabric
    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!insertItem(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + this.invSize, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + this.invSize) {
            // This is a TE slot so merge the stack into the players inventory
            if (!insertItem(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.setStack(ItemStack.EMPTY);
        } else {
            sourceSlot.markDirty();
        }
        sourceSlot.onTakeItem(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    protected boolean insertItem(ItemStack stack, int start, int end, boolean reverse) {
        boolean flag = false;
        int i = start;
        if (reverse) {
            i = end - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty()) {
                if (reverse) {
                    if (i < start) {
                        break;
                    }
                } else if (i >= end) {
                    break;
                }

                if (i < TE_INVENTORY_FIRST_SLOT_INDEX || this.isItemValid(i - VANILLA_SLOT_COUNT, stack)) {

                    Slot slot = this.slots.get(i);
                    ItemStack itemstack = slot.getStack();
                    if (!itemstack.isEmpty() && ItemStack.areEqual(stack, itemstack)) {
                        int j = itemstack.getCount() + stack.getCount();
                        int maxSize = Math.min(slot.getMaxItemCount(), stack.getMaxCount());
                        if (j <= maxSize) {
                            stack.setCount(0);
                            itemstack.setCount(j);
                            slot.markDirty();
                            flag = true;
                        } else if (itemstack.getCount() < maxSize) {
                            stack.decrement(maxSize - itemstack.getCount());
                            itemstack.setCount(maxSize);
                            slot.markDirty();
                            flag = true;
                        }
                    }

                }

                if (reverse) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (reverse) {
                i = end - 1;
            } else {
                i = start;
            }

            while(true) {
                if (reverse) {
                    if (i < start) {
                        break;
                    }
                } else if (i >= end) {
                    break;
                }

                if (i < TE_INVENTORY_FIRST_SLOT_INDEX || this.isItemValid(i - VANILLA_SLOT_COUNT, stack)) {

                    Slot slot1 = this.slots.get(i);
                    ItemStack itemstack1 = slot1.getStack();
                    if (itemstack1.isEmpty() && slot1.canInsert(stack)) {
                        if (stack.getCount() > slot1.getMaxItemCount()) {
                            slot1.setStack(stack.split(slot1.getMaxItemCount()));
                        } else {
                            slot1.setStack(stack.split(stack.getCount()));
                        }

                        slot1.markDirty();
                        flag = true;
                        break;
                    }

                }

                if (reverse) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }

    public abstract boolean isItemValid(int slot, ItemStack itemStack);


    @Override
    public abstract boolean canUse(PlayerEntity player);

}
