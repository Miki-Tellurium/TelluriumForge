package com.mikitellurium.telluriumforge.registry;

import com.mikitellurium.telluriumforge.registry.deferred.DeferredBlockEntityType;
import com.mikitellurium.telluriumforge.registry.deferred.DeferredBlockEntityTypeRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;

public record BlockEntityRegistrator(DeferredBlockEntityTypeRegistry registry) implements RegistryHelper<BlockEntityType<?>> {
    @Override
    public <S extends BlockEntityType<?>> DeferredBlockEntityType<S> register(String id, Supplier<S> object) {
        return registry.register(id, object);
    }

    public <T extends BlockEntity, S extends BlockEntityType<T>> DeferredBlockEntityType<S> ofBlock(BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<Block> block) {
        return ofBlocks(block.getId().getPath(), factory, Set.of(block));
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity, S extends BlockEntityType<T>> DeferredBlockEntityType<S> ofBlocks(String id, BlockEntityType.BlockEntitySupplier<T> factory, Set<DeferredBlock<Block>> blocks) {
        // Use suppliers to avoid calling get() before DeferredBlock is constructed
        Supplier<Block[]> blockSupplier = () -> blocks.stream().map(DeferredHolder::get).toArray(Block[]::new);
        Supplier<BlockEntityType<T>> typeSupplier = () -> BlockEntityType.Builder.of(factory, blockSupplier.get()).build(null);
        return (DeferredBlockEntityType<S>) this.register(id, typeSupplier);
    }

    /**
     * Create a new {@code BlockEntityRegistrator} using the provided block entity registry and mod id
     */
    public static BlockEntityRegistrator makeRegistrator(String modId) {
        return new BlockEntityRegistrator(DeferredBlockEntityTypeRegistry.create(modId));
    }
}
