package com.mikitellurium.telluriumforge.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class BlockPosSyncPacket implements IMessage {
    private BlockPos blockPos;

    public BlockPosSyncPacket() {}

    public BlockPosSyncPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.blockPos = ((PacketBuffer)buf).readBlockPos();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ((PacketBuffer)buf).writeBlockPos(blockPos);
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
