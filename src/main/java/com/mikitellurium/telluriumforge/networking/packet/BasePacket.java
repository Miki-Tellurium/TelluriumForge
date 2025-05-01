package com.mikitellurium.telluriumforge.networking.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface BasePacket extends FabricPacket {

   default void handle(ClientPlayerEntity player, PacketSender responseSender) {}

   default void handle(ServerPlayerEntity player, PacketSender responseSender) {}

}
