package com.mikitellurium.telluriumforge.test;

import com.mikitellurium.telluriumforge.networking.packet.BooleanSyncPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.util.math.BlockPos;

public class ExamplePayload extends BooleanSyncPacket {

    public ExamplePayload(BlockPos blockPos, Boolean value) {
        super(blockPos, value);
    }

    @Override
    public PacketType<?> getType() {
        return null;
    }

}
