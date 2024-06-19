package com.mikitellurium.telluriumforge.networking.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

/**
 * A basic class used to send packets for block entities data synchronization.
 * This packet only sends the {@code BlockPos} of the {@code BlockEntity} and a
 * single value of type T.
 * <p>
 * Implementations should store the {@link CustomPayload.Id} instance in a static final
 * field and return that in the {@link #getId} method implemented from
 * the {@code CustomPayload} interface.
 * <p>
 * The {@link #handleClient} and {@link #handleServer} methods handles the custom payload
 * on the client and server side respectively. They can be implemented based on the side that
 * needs to receive data.
 *
 * @param <T> The type of data that this payload send
 */
public abstract class BlockEntitySyncPayload<T> implements CustomPayload {

    /**
     * Creates a new {@code CustomPayload.Id} instance with the specified identifier.
     *
     * @param <T> The type of the custom payload
     * @param id  The identifier for the custom payload
     * @return A new {@code CustomPayload.Id} instance with the specified identifier
     */
    public static <T extends CustomPayload> CustomPayload.Id<T> makeId(Identifier id) {
        return new Id<>(id);
    }

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
    public BlockEntitySyncPayload(BlockPos blockPos, T value) {
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

    /**
     * Writes the custom payload data to the provided {@code RegistryByteBuf}.
     *
     * @param buf The {@code RegistryByteBuf} to write the data to
     */
    public abstract void write(RegistryByteBuf buf);

    /**
     * Handles the custom payload in the specified client-side networking context.
     *
     * @param context The {@code Context} in which the payload is handled
     */
    public void handleClient(ClientPlayNetworking.Context context) {}

    /**
     * Handles the custom payload in the specified server-side networking context.
     *
     * @param context The {@code Context} in which the payload is handled
     */
    public void handleServer(ServerPlayNetworking.Context context) {}

    @Override
    public abstract Id<? extends CustomPayload> getId();

}
