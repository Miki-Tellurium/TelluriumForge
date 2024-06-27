package com.mikitellurium.telluriumforge.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.BlockPos;

/**
 * Implementation of the {@link BlockEntitySyncPayload} class that send
 * an Ingredient.
 */
public abstract class IngredientSyncPayload extends BlockEntitySyncPayload<Ingredient> {

    /**
     * Construct a new payload with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     * @param value the Ingredient to sync
     */
    public IngredientSyncPayload(BlockPos blockPos, Ingredient value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        Ingredient.PACKET_CODEC.encode(buf, this.getValue());
    }

}
