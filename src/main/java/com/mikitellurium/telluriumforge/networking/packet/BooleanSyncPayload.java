package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * a boolean value.
 */
public abstract class BooleanSyncPayload extends BlockEntitySyncPayload<Boolean> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the boolean value to sync
     */
    public BooleanSyncPayload(BlockPos blockPos, Boolean value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeBoolean(this.getValue());
    }

}
