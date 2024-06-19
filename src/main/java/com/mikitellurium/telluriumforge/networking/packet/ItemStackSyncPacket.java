package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * a ItemStack value.
 */
public abstract class ItemStackSyncPacket extends BlockEntitySyncPayload<ItemStack> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the ItemStack to sync
     */
    public ItemStackSyncPacket(BlockPos blockPos, ItemStack value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        ItemStack.PACKET_CODEC.encode(buf, this.getValue());
    }

}
