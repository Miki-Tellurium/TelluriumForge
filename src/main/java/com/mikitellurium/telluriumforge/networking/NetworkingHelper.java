package com.mikitellurium.telluriumforge.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Consumer;
import java.util.function.Function;

public class NetworkingHelper {

    private final SimpleChannel channel;

    private NetworkingHelper(String modId) {
        this.channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(modId, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
    }

    public static NetworkingHelper create(String modId) {
        return new NetworkingHelper(modId);
    }

    public <P extends ModPacket> void registerPacket(int id, Class<P> clazz, Function<FriendlyByteBuf, P> factory, NetworkDirection networkDirection) {
        channel.messageBuilder(clazz, id, networkDirection)
                .decoder(factory)
                .encoder(P::write)
                .consumerMainThread(P::handle)
                .add();
    }

    public void simpleChannel(Consumer<SimpleChannel> consumer) {
        consumer.accept(channel);
    }

    public <P extends ModPacket> void send(PacketDistributor.PacketTarget target, P message) {
        channel.send(target, message);
    }

    public <P extends ModPacket> void sendToServer(P message) {
        channel.sendToServer(message);
    }

    public <P extends ModPacket> void sendToClientPlayer(P message, ServerPlayer player) {
        this.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public <P extends ModPacket> void sendToClients(P message) {
        this.send(PacketDistributor.ALL.noArg(), message);
    }

}
