package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.util.math.BlockPos;

public abstract class TileDataSyncPacket<T> extends BlockPosSyncPacket {
    private T value;

    public TileDataSyncPacket() {}

    protected TileDataSyncPacket(BlockPos pos, T value) {
        super(pos);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
