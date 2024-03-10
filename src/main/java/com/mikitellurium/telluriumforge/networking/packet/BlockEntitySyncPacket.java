package com.mikitellurium.telluriumforge.networking.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * A basic class used to send packets for block entities data synchronization.
 * This packet only sends the {@code BlockPos} of the {@code BlockEntity} and a
 * single value of type T.
 * <p>
 * Implementations should store the packet type instance in a static final
 * field and return that in the {@link #getType()} method implemented from
 * the {@code FabricPacket} interface.
 *
 * @param <T> The type of data that this packet send
 */
public abstract class BlockEntitySyncPacket<T> implements FabricPacket {

    /**
     * The {@code BlockPos} of the block entity
     */
    private final BlockPos blockPos;
    private final T value;

    /**
     * Construct a new packet with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     */
    public BlockEntitySyncPacket(BlockPos blockPos, T value) {
        this.blockPos = blockPos;
        this.value = value;
    }

    /**
     * Gets the {@code BlockPos} of the block entity stored
     * in this packet.
     *
     * @return the {@code BlockPos} of the block entity
     */
    public BlockPos getBlockPos() {
        return blockPos;
    }

    /**
     * Gets the value used to sync to the block entity
     * stored in this packet.
     *
     * @return the value stored in this packet
     */
    public T getValue() {
        return value;
    }

}
