package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class IntSyncPacket extends BlockPosSyncPacket {
    private int value;

    public IntSyncPacket() {
    }

    public IntSyncPacket(BlockPos pos, int value) {
        super(pos);
        this.value = value;
    }

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
