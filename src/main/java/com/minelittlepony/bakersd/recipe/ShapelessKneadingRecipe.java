package com.minelittlepony.bakersd.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

import com.google.gson.JsonObject;
import com.minelittlepony.bakersd.item.DoughItem;

public class ShapelessKneadingRecipe extends ShapelessRecipe {

    private final ItemStack output;

    public ShapelessKneadingRecipe(Identifier id, String group, CraftingRecipeCategory category, ItemStack intermediary, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, category, DoughItem.appendBakeResult(intermediary, output), input);
        this.output = output;
    }

    public String getBakingResult() {
        return Registries.ITEM.getId(output.getItem()).toString();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakersRecipesSerializers.SHAPELESS;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
       return DoughItem.appendBakeResult(super.craft(craftingInventory), output);
    }

    public static class Serializer extends ShapelessRecipe.Serializer {
        @Override
        public ShapelessRecipe read(Identifier id, JsonObject json) {
            ShapelessRecipe recipe = super.read(id, json);
            ItemStack baked = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "baked"));
            return new ShapelessKneadingRecipe(id, recipe.getGroup(), recipe.getCategory(), recipe.getOutput(), baked, recipe.getIngredients());
        }

        @Override
        public ShapelessRecipe read(Identifier id, PacketByteBuf buffer) {
            ShapelessRecipe recipe = super.read(id, buffer);
            return new ShapelessKneadingRecipe(id, recipe.getGroup(), recipe.getCategory(), recipe.getOutput(), buffer.readItemStack(), recipe.getIngredients());
        }

        @Override
        public void write(PacketByteBuf buffer, ShapelessRecipe recipe) {
            super.write(buffer, recipe);
            buffer.writeString(((ShapelessKneadingRecipe)recipe).getBakingResult());
        }
    }
}
