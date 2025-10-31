package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class IntSyncPacket extends TileDataSyncPacket<Integer> {
    public IntSyncPacket() {}

    protected IntSyncPacket(BlockPos pos, Integer value) {
        super(pos, value);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.setValue(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(this.getValue());
    }
}
