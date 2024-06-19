package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * an UUID.
 */
public abstract class UUIDSyncPacket extends BlockEntitySyncPayload<UUID> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the uuid value to sync
     */
    public UUIDSyncPacket(BlockPos blockPos, UUID value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeUuid(this.getValue());
    }

}
