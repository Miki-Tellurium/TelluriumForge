package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class DoubleSyncPacket extends BlockPosSyncPacket {
    private double value;

    public DoubleSyncPacket() {
    }

    public DoubleSyncPacket(BlockPos pos, double value) {
        super(pos);
        this.value = value;
    }

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
