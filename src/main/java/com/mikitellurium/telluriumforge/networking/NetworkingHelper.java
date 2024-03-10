package com.mikitellurium.telluriumforge.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * The {@code NetworkingHelper} class provides utility methods for registering and
 * sending packets.
 */
public class NetworkingHelper {

    /**
     * Registers a custom client-to-server packet.
     *
     * @param <T>     The type of the packet
     * @param packetType  The custom {@code PacketType} to register
     * @param handler The handler for processing the received packet on the server
     */
    public <T extends FabricPacket> void registerC2SPacket(PacketType<T> packetType,
                                                                  ServerPlayNetworking.PlayPacketHandler<T> handler) {
        ServerPlayNetworking.registerGlobalReceiver(packetType, handler);
    }

    /**
     * Registers a custom server-to-client packet.
     *
     * @param <T>     The type of the packet
     * @param packetType  The custom {@code PacketType} to register
     * @param handler The handler for processing the received packet on the client
     */
    public <T extends FabricPacket> void registerS2CPacket(PacketType<T> packetType,
                                                                  ClientPlayNetworking.PlayPacketHandler<T> handler) {
        ClientPlayNetworking.registerGlobalReceiver(packetType, handler);
    }

    /**
     * Sends a packet to a specific player.
     *
     * @param player The player to send the packet to
     * @param packet The packet to send
     */
    public void sendToClient(ServerPlayerEntity player, FabricPacket packet) {
        ServerPlayNetworking.send(player, packet);
    }

    /**
     * Sends a packet to all players tracking a specific block position in the given world.
     *
     * @param world  The {@code ServerWorld}
     * @param pos    The block position to track
     * @param packet The packet to send
     */
    public void sendToTrackingClients(ServerWorld world, BlockPos pos, FabricPacket packet) {
        if (world != null && !world.isClient) {
            for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
                ServerPlayNetworking.send(player, packet);
            }
        }
    }

    /**
     * Sends a packet to all players on the given world.
     *
     * @param server The {@code ServerWorld}
     * @param packet The packet to send
     */
    public void sendToAllClients(ServerWorld server, FabricPacket packet) {
        if (server != null) {
            for (ServerPlayerEntity player : PlayerLookup.all(server.getServer())) {
                ServerPlayNetworking.send(player, packet);
            }
        }
    }

    /**
     * Sends a packet to the server.
     *
     * @param packet The packet to send
     */
    public void sendToServer(FabricPacket packet) {
        ClientPlayNetworking.send(packet);
    }

}

