package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;

public class BooleanSyncPacket extends BlockPosSyncPacket {
    private boolean value;

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.value = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeBoolean(value);
    }
}
