package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class BooleanSyncPacket extends TileDataSyncPacket<Boolean> {
    public BooleanSyncPacket() {}

    protected BooleanSyncPacket(BlockPos pos, Boolean value) {
        super(pos, value);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.setValue(buf.readBoolean());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeBoolean(this.getValue());
    }
}
