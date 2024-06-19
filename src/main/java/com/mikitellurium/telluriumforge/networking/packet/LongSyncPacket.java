package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * a long value.
 */
public abstract class LongSyncPacket extends BlockEntitySyncPayload<Long> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the long value to sync
     */
    public LongSyncPacket(BlockPos blockPos, Long value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeLong(this.getValue());
    }


}
