package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * a float value.
 */
public abstract class FloatSyncPacket extends BlockEntitySyncPayload<Float> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the float value to sync
     */
    public FloatSyncPacket(BlockPos blockPos, Float value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeFloat(this.getValue());
    }

}
