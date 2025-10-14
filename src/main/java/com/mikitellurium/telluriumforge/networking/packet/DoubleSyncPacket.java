package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;

public class DoubleSyncPacket extends BlockPosSyncPacket {
    private double value;

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.value = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeDouble(value);
    }
}
