package com.mikitellurium.telluriumforge.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

public record BlockEntityRegistrator(DeferredRegister<BlockEntityType<?>> registry) implements RegistryHelper<BlockEntityType<?>> {
    @Override
    public <S extends BlockEntityType<?>> RegistryObject<S> register(String id, Supplier<S> object) {
        return registry.register(id, object);
    }

    public <T extends BlockEntity, S extends BlockEntityType<T>> RegistryObject<S> ofBlock(BlockEntityType.BlockEntitySupplier<T> factory, RegistryObject<Block> block) {
        return ofBlocks(block.getId().getPath(), factory, Set.of(block));
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity, S extends BlockEntityType<T>> RegistryObject<S> ofBlocks(String id, BlockEntityType.BlockEntitySupplier<T> factory, Set<RegistryObject<Block>> blocks) {
        // Use suppliers to avoid calling get() before DeferredBlock is constructed
        Supplier<Block[]> blockSupplier = () -> blocks.stream().map(RegistryObject::get).toArray(Block[]::new);
        Supplier<BlockEntityType<T>> typeSupplier = () -> BlockEntityType.Builder.of(factory, blockSupplier.get()).build(null);
        return (RegistryObject<S>) this.register(id, typeSupplier);
    }

    /**
     * Create a new {@code BlockEntityRegistrator} using the provided block entity registry and mod id
     */
    public static BlockEntityRegistrator makeRegistrator(String modId) {
        return new BlockEntityRegistrator(DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modId));
    }
}
