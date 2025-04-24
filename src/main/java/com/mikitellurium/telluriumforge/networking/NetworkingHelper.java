package com.mikitellurium.telluriumforge.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class NetworkingHelper {

    public static SimpleChannel simpleChannel(String modId) {
        return NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(modId, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
    }

    public static <T extends ModPacket> void registerPacket(SimpleChannel channel, int id, Class<T> clazz, Function<FriendlyByteBuf, T> factory, NetworkDirection networkDirection) {
        channel.messageBuilder(clazz, id, networkDirection)
                .decoder(factory)
                .encoder(T::write)
                .consumerMainThread(T::handle)
                .add();
    }

}
