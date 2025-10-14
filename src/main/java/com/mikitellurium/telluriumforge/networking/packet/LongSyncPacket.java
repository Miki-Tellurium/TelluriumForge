package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;

public class LongSyncPacket extends BlockPosSyncPacket {
    private long value;

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.value = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeLong(value);
    }
}
