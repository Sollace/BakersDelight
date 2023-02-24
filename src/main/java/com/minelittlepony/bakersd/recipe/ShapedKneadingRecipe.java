package com.minelittlepony.bakersd.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

import com.google.gson.JsonObject;
import com.minelittlepony.bakersd.item.DoughItem;

public class ShapedKneadingRecipe extends ShapedRecipe {

    private final ItemStack output;

    public ShapedKneadingRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height, DefaultedList<Ingredient> input, ItemStack intermediary, ItemStack output) {
        super(id, group, category, width, height, input, DoughItem.appendBakeResult(intermediary, output));
        this.output = output;
    }

    public String getBakingResult() {
        return Registries.ITEM.getId(output.getItem()).toString();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakersRecipesSerializers.SHAPED;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
       return DoughItem.appendBakeResult(super.craft(craftingInventory), output);
    }

    public static class Serializer extends ShapedRecipe.Serializer {
        @Override
        public ShapedRecipe read(Identifier id, JsonObject json) {
            ShapedRecipe recipe = super.read(id, json);
            ItemStack baked = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "baked"));
            return new ShapedKneadingRecipe(id, recipe.getGroup(), recipe.getCategory(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getOutput(), baked);
        }

        @Override
        public ShapedRecipe read(Identifier id, PacketByteBuf buffer) {
            ShapedRecipe recipe = super.read(id, buffer);
            return new ShapedKneadingRecipe(id, recipe.getGroup(), recipe.getCategory(),recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getOutput(), buffer.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buffer, ShapedRecipe recipe) {
            super.write(buffer, recipe);
            buffer.writeString(((ShapedKneadingRecipe)recipe).getBakingResult());
        }
    }
}
