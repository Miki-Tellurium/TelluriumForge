package com.mikitellurium.telluriumforge.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.Supplier;

public record BlockEntityRegistrator(String modId) implements RegistryHelper<BlockEntityType<?>>, IdentifierProvider {
    @Override
    public <S extends BlockEntityType<?>> S register(String id, Supplier<S> object) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, ofMod(id), object.get());
    }

    public <T extends BlockEntity> BlockEntityType<T> ofBlock(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block block) {
        BlockEntityType<T> type = FabricBlockEntityTypeBuilder.create(factory, block).build();
        return this.register(id, () -> type);
    }

    public <T extends BlockEntity> BlockEntityType<T> ofBlocks(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, List<Block> blocks) {
        BlockEntityType<T> type = FabricBlockEntityTypeBuilder.create(factory, blocks.toArray(Block[]::new)).build();
        return this.register(id, () -> type);
    }
}
