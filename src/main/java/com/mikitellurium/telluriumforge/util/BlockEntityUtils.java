package com.mikitellurium.telluriumforge.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class BlockEntityUtils {

    public static <T extends BlockEntity> BlockEntityType<T> makeType(BiFunction<BlockPos, BlockState, T> factory, RegistryObject<Block>... blocks) {
        Objects.requireNonNull(factory);
        Set<Block> blockSet = Arrays.stream(blocks).map(RegistryObject::get).collect(Collectors.toSet());
        return new BlockEntityType<>(factory::apply, blockSet, null);
    }

}
