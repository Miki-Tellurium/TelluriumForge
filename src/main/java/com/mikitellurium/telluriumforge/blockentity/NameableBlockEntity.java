package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;

/**
 * Automatically handles custom name for block entities
 */
public abstract class NameableBlockEntity extends BlockEntity implements Nameable {

    private Text name;

    public NameableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void setCustomName(Text name) {
        this.name = name;
    }

    @Override
    public boolean hasCustomName() {
        return this.name != null && !this.name.equals(this.getDefaultName());
    }

    @Override
    public Text getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    protected abstract Text getDefaultName();

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", NbtCompound.STRING_TYPE)) {
            this.name = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if (this.name != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.name));
        }
        super.writeNbt(nbt);
    }

}
