package com.mikitellurium.telluriumforge.networking.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public interface BasePayload extends CustomPayload {

   default void handle(ClientPlayNetworking.Context responseSender) {}

   default void handle(ServerPlayNetworking.Context responseSender) {}

}
