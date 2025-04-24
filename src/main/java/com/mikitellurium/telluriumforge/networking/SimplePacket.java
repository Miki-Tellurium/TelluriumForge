package com.mikitellurium.telluriumforge.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface SimplePacket {

    /**
     * Write the data of this packet to the {@link FriendlyByteBuf}
     */
    void write(FriendlyByteBuf buf);

    /**
     * Handle the packet when it is received
     */
    boolean handle(Supplier<NetworkEvent.Context> supplier);

}
