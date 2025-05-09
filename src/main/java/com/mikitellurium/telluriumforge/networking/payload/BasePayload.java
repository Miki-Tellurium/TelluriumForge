package com.mikitellurium.telluriumforge.networking.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public interface BasePayload extends CustomPayload {

   default void handle(ClientPlayNetworking.Context responseSender) {}

   default void handle(ServerPlayNetworking.Context responseSender) {}

}
