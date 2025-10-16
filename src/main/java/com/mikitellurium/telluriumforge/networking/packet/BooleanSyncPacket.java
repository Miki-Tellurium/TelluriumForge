package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class BooleanSyncPacket extends BlockPosSyncPacket {
    private boolean value;

    public BooleanSyncPacket() {
    }

    public BooleanSyncPacket(BlockPos pos, boolean value) {
        super(pos);
        this.value = value;
    }

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
