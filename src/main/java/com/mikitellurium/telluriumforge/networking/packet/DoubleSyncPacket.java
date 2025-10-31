package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class DoubleSyncPacket extends TileDataSyncPacket<Double> {
    public DoubleSyncPacket() {}

    protected DoubleSyncPacket(BlockPos pos, Double value) {
        super(pos, value);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.setValue(buf.readDouble());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeDouble(this.getValue());
    }
}
