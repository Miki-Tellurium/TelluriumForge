package com.mikitellurium.telluriumforge.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static void validateJsonElement(JsonObject obj, String... keys) {
        Arrays.asList(keys).forEach((s) -> {
            if (!obj.has(s))
                throw new JsonSyntaxException("Missing " + s + ", expected to find a string or object");
        });
    }

    public static Ingredient ingredientFromJson(JsonElement element) {
        Ingredient ingredient;
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            ingredient = Ingredient.of(CraftingHelper.getItemStack(obj, true));
        } else {
            List<ItemStack> itemStacks = new ArrayList<>();
            element.getAsJsonArray().forEach((e) -> {
                JsonObject obj = e.getAsJsonObject();
                itemStacks.add(CraftingHelper.getItemStack(obj, true));
            });
            ingredient = Ingredient.of(itemStacks.toArray(new ItemStack[]{}));
        }
        return ingredient;
    }

    public static Item itemFromJson(JsonObject object, String memberName) {
        String s = GsonHelper.getAsString(object, memberName);
        Holder<Item> item = ForgeRegistries.ITEMS.getHolder(ResourceLocation.tryParse(s)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + s + "'"));
        if (item.get() == Items.AIR) {
            throw new JsonSyntaxException("Empty ingredient not allowed here");
        } else {
            return item.get();
        }
    }

    public static ItemStack itemStackFromJson(JsonObject object, String memberName) {
        if (!object.has(memberName)) throw new JsonSyntaxException("Missing " + memberName + ", expected to find a string or object");
        ItemStack output;
        if (object.get(memberName).isJsonObject()) {
            JsonObject resultJson = GsonHelper.getAsJsonObject(object, memberName);
            output = CraftingHelper.getItemStack(resultJson, true);
        } else {
            String result = GsonHelper.getAsString(object, memberName);
            ResourceLocation resourcelocation = new ResourceLocation(result);
            output = new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(resourcelocation));
        }
        return output;
    }


}
