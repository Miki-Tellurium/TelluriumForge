package com.mikitellurium.telluriumforge.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
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

    /**
     * Registers a custom payload type with the payload type registry for the specified destination.
     *
     * @param <T>   The type of the custom payload
     * @param id    The id of the custom payload
     * @param codec The codec for encoding and decoding the custom payload
     * @param side  The destination side for the payload
     */
    public static <T extends CustomPayload> void registerPayload(CustomPayload.Id<T> id,
                                                                    PacketCodec<? super RegistryByteBuf, T> codec,
                                                                    PacketDestination side) {
        if (side == PacketDestination.SERVER) {
            PayloadTypeRegistry.playC2S().register(id, codec);
        } else if (side == PacketDestination.CLIENT) {
            PayloadTypeRegistry.playS2C().register(id, codec);
        } else if (side == PacketDestination.COMMON) {
            PayloadTypeRegistry.playC2S().register(id, codec);
            PayloadTypeRegistry.playS2C().register(id, codec);
        }
    }

    /**
     * Registers a custom payload type for client-to-server communication and associates it with a handler.
     *
     * @param <T>     The type of the custom payload
     * @param id      The id of the custom payload
     * @param codec   The codec for encoding and decoding the custom payload
     * @param handler The handler for processing the received payload on the server
     */
    public static <T extends CustomPayload> void registerPayload(CustomPayload.Id<T> id,
                                                                 PacketCodec<? super RegistryByteBuf, T> codec,
                                                                 ServerPlayNetworking.PlayPayloadHandler<T> handler) {
        registerPayload(id, codec, PacketDestination.SERVER);
        registerC2SReceiver(id, handler);
    }

    /**
     * Registers a custom payload type for server-to-client communication and associates it with a handler.
     *
     * @param <T>     The type of the custom payload
     * @param id      The id of the custom payload
     * @param codec   The codec for encoding and decoding the custom payload
     * @param handler The handler for processing the received payload on the client
     */
    public static <T extends CustomPayload> void registerPayload(CustomPayload.Id<T> id,
                                                                 PacketCodec<? super RegistryByteBuf, T> codec,
                                                                 ClientPlayNetworking.PlayPayloadHandler<T> handler) {
        registerPayload(id, codec, PacketDestination.CLIENT);
        registerS2CReceiver(id, handler);
    }

    /**
     * Registers a custom payload type for both client-to-server and server-to-client communication,
     * and associates it with handlers for both sides.
     *
     * @param <T>           The type of the custom payload
     * @param id            The id of the custom payload
     * @param codec         The codec for encoding and decoding the custom payload
     * @param serverHandler The handler for processing the received payload on the server
     * @param clientHandler The handler for processing the received payload on the client
     */
    public static <T extends CustomPayload> void registerPayload(CustomPayload.Id<T> id,
                                                                 PacketCodec<? super RegistryByteBuf, T> codec,
                                                                 ServerPlayNetworking.PlayPayloadHandler<T> serverHandler,
                                                                 ClientPlayNetworking.PlayPayloadHandler<T> clientHandler) {
        registerPayload(id, codec, PacketDestination.COMMON);
        registerC2SReceiver(id, serverHandler);
        registerS2CReceiver(id, clientHandler);
    }

    /**
     * Registers a custom client-to-server payload receiver.
     *
     * @param <T> The type of payload
     * @param id  The id of the custom payload
     * @param handler The handler for processing the received payload on the server
     */
    public static <T extends CustomPayload> void registerC2SReceiver(CustomPayload.Id<T> id,
                                                              ServerPlayNetworking.PlayPayloadHandler<T> handler) {
        ServerPlayNetworking.registerGlobalReceiver(id, handler);
    }

    /**
     * Registers a custom server-to-client payload receiver.
     *
     * @param <T> The type of payload
     * @param id  The id of the custom payload
     * @param handler The handler for processing the received payload on the client
     */
    public static <T extends CustomPayload> void registerS2CReceiver(CustomPayload.Id<T> id,
                                                                     ClientPlayNetworking.PlayPayloadHandler<T> handler) {
        ClientPlayNetworking.registerGlobalReceiver(id, handler);
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
     * @param server The {@code ServerWorld}
     * @param payload The payload to send
     */
    public static void sendToAllClients(ServerWorld server, CustomPayload payload) {
        if (server != null) {
            for (ServerPlayerEntity player : PlayerLookup.all(server.getServer())) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    /**
     * Sends a payload to the server.
     *
     * @param payload The payload to send
     */
    public static void sendToServer(CustomPayload payload) {
        ClientPlayNetworking.send(payload);
    }
}

