package com.mikitellurium.telluriumforge.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * The {@code NetworkingHelper} class provides utility methods for registering and
 * sending payloads.
 */
public class NetworkingHelper {

    /**
     * Sends a packet to a specific player.
     *
     * @param player The player to send the payload to
     * @param packet The packet to send
     */
    public static void sendToClient(ServerPlayerEntity player, FabricPacket packet) {
        ServerPlayNetworking.send(player, packet);
    }

    /**
     * Sends a packet to all players tracking a specific block position in the given world.
     *
     * @param world  The {@code ServerWorld}
     * @param pos    The block position to track
     * @param packet The packet to send
     */
    public static void sendToTrackingClients(ServerWorld world, BlockPos pos, FabricPacket packet) {
        if (world != null && !world.isClient) {
            for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
                ServerPlayNetworking.send(player, packet);
            }
        }
    }

    /**
     * Sends a packet to all players on the given world.
     *
     * @param world The {@code ServerWorld}
     * @param packet The packet to send
     */
    public static void sendToAllClients(ServerWorld world, FabricPacket packet) {
        if (world != null) {
            for (ServerPlayerEntity player : PlayerLookup.all(world.getServer())) {
                ServerPlayNetworking.send(player, packet);
            }
        }
    }

    /**
     * Sends a packet from the client to the server.
     *
     * @param packet The packet to send
     */
    public static void sendToServer(FabricPacket packet) {
        ClientPlayNetworking.send(packet);
    }

}

