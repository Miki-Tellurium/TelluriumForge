package com.mikitellurium.telluriumforge.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * The {@code NetworkingHelper} class provides utility methods for registering and
 * sending payloads.
 */
public class NetworkingHelper {

    public static <T extends CustomPayload> void registerS2C(CustomPayload.Id<T> type, PacketCodec<? super PacketByteBuf, T> codec, ClientPlayNetworking.PlayPayloadHandler<T> handler) {
        PayloadTypeRegistry.playS2C().register(type, codec);
        ClientPlayNetworking.registerGlobalReceiver(type, handler);
    }

    public static <T extends CustomPayload> void registerC2S(CustomPayload.Id<T> type, PacketCodec<? super PacketByteBuf, T> codec, ServerPlayNetworking.PlayPayloadHandler<T> handler) {
        PayloadTypeRegistry.playC2S().register(type, codec);
        ServerPlayNetworking.registerGlobalReceiver(type, handler);
    }

    /**
     * Sends a payload to a specific player.
     *
     * @param player The player to send the payload to
     * @param payload The payload to send
     */
    public static void sendToClient(ServerPlayerEntity player, CustomPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    /**
     * Sends a payload to all players tracking a specific block position in the given world.
     *
     * @param world  The {@code ServerWorld}
     * @param pos    The block position to track
     * @param payload The payload to send
     */
    public static void sendToTrackingClients(ServerWorld world, BlockPos pos, CustomPayload payload) {
        if (world != null && !world.isClient) {
            for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    /**
     * Sends a payload to all players on the given world.
     *
     * @param world The {@code ServerWorld}
     * @param payload The payload to send
     */
    public static void sendToAllClients(ServerWorld world, CustomPayload payload) {
        if (world != null) {
            for (ServerPlayerEntity player : PlayerLookup.all(world.getServer())) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    /**
     * Sends a payload from the client to the server.
     *
     * @param payload The payload to send
     */
    public static void sendToServer(CustomPayload payload) {
        ClientPlayNetworking.send(payload);
    }

}

