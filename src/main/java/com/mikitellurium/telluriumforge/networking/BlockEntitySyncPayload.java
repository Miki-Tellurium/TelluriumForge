package com.mikitellurium.telluriumforge.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public abstract class BlockEntitySyncPayload<T> implements BasePacketPayload {


    /**
     * Make a {@link StreamCodec} for the payload registration. Example:
     * <pre><code>
     * public static StreamCodec<FriendlyByteBuf, IntegerPayload> CODEC =
     *      getCodec(ByteBufCodecs.INT, IntegerPayload::new);
     * </code></pre>
     *
     * @param valueCodec the {@link StreamCodec} for the value type
     * @param factory a payload factory
     * @return the codec
     */
    public static <T, P extends BlockEntitySyncPayload<T>> StreamCodec<FriendlyByteBuf, P> getCodec(StreamCodec<ByteBuf, T> valueCodec, BiFunction<BlockPos, T, P> factory) {
        return StreamCodec.composite(
                BlockPos.STREAM_CODEC, P::getBlockPos,
                valueCodec, P::getValue,
                factory);
    }

    private final BlockPos blockPos;
    private final T value;

    public BlockEntitySyncPayload(BlockPos blockPos, T value) {
        this.blockPos = blockPos;
        this.value = value;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public T getValue() {
        return value;
    }

    @Override
    public abstract void handle(IPayloadContext context);

    @Override
    public abstract @NotNull Type<? extends CustomPacketPayload> type();

}
