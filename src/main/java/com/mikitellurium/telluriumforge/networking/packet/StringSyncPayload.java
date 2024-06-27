package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * a string value.
 */
public abstract class StringSyncPayload extends BlockEntitySyncPayload<String> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the string value to sync
     */
    public StringSyncPayload(BlockPos blockPos, String value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeString(this.getValue());
    }

}
