package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class LongSyncPacket extends BlockPosSyncPacket {
    private long value;

    public LongSyncPacket() {
    }

    public LongSyncPacket(BlockPos pos, long value) {
        super(pos);
        this.value = value;
    }

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
