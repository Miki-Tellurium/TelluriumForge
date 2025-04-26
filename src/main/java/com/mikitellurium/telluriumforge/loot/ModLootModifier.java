package com.mikitellurium.telluriumforge.loot;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import org.jetbrains.annotations.NotNull;

public abstract class ModLootModifier extends LootModifier {

    private final ResourceLocation lootTable;

    protected ModLootModifier(LootItemCondition[] lootConditions, ResourceLocation lootTable) {
        super(lootConditions);
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot;
    }

    @Override
    public abstract MapCodec<? extends IGlobalLootModifier> codec();

    public ResourceLocation getLootTable() {
        return lootTable;
    }

    public static LootItemCondition[] getLootConditions(ResourceLocation lootTableLocation) {
        return new LootItemCondition[] {LootTableIdCondition.builder(lootTableLocation).build()};
    }

}
