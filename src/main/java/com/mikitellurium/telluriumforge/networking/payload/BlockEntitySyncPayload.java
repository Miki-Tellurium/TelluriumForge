package com.mikitellurium.telluriumforge.networking.payload;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

/**
 * A basic class used to send payloads for block entities data synchronization.
 * This payload only sends the {@code BlockPos} of the {@code BlockEntity} and a
 * single value of type T.
 * <p>
 * Implementations should store the payload type instance in a static final
 * field and return that in the {@link #getId()} method implemented from
 * the {@link CustomPayload} interface.
 *
 * @param <T> The type of data that this payload send
 */
public abstract class BlockEntitySyncPayload<T> implements BasePayload {

    private final BlockPos blockPos;
    private final T value;

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     */
    public BlockEntitySyncPayload(BlockPos blockPos, T value) {
        this.blockPos = blockPos;
        this.value = value;
    }

    /**
     * Gets the {@code BlockPos} of the block entity stored
     * in this payload.
     *
     * @return the {@code BlockPos} of the block entity
     */
    public BlockPos getBlockPos() {
        return blockPos;
    }

    /**
     * Gets the value used to sync to the block entity
     * stored in this payload.
     *
     * @return the value stored in this payload
     */
    public T getValue() {
        return value;
    }

}
