package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;

public class IntSyncPacket extends BlockPosSyncPacket {
    private int value;

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(value);
    }
}
