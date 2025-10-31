package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class LongSyncPacket extends TileDataSyncPacket<Long> {
    public LongSyncPacket() {}

    protected LongSyncPacket(BlockPos pos, Long value) {
        super(pos, value);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.setValue(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeLong(this.getValue());
    }
}
