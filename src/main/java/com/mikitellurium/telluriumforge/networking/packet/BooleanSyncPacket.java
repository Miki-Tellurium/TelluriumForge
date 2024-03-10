package com.mikitellurium.telluriumforge.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPacket} class that send
 * a boolean value.
 * <p>
 * Implementations should store the packet type instance in a static final
 * field and return that in the {@link #getType()} method implemented from
 * the {@code FabricPacket} interface.
 */
public abstract class BooleanSyncPacket extends BlockEntitySyncPacket<Boolean> {

    /**
     * Construct a new packet with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value
     */
    public BooleanSyncPacket(BlockPos blockPos, Boolean value) {
        super(blockPos, value);
    }

    /**
     * Construct a new packet using a {@code PacketByteBuf} containing
     * the data used to synchronize the block entity.
     *
     * @param buf the {@code PacketByteBuf} containing the data
     */
    public BooleanSyncPacket(PacketByteBuf buf) {
        this(buf.readBlockPos(), buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeBoolean(this.getValue());
    }

    @Override
    public abstract PacketType<?> getType();

}
