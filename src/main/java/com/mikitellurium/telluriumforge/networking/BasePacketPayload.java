package com.mikitellurium.telluriumforge.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public interface BasePacketPayload extends CustomPacketPayload {

    void handle(IPayloadContext context);

    @Override
    @NotNull
    Type<? extends CustomPacketPayload> type();

}
