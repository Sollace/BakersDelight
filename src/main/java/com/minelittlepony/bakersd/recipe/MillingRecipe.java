package com.minelittlepony.bakersd.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MillingRecipe implements Recipe<Inventory> {

    private final Identifier id;

    private final ItemStack output;

    private final DefaultedList<Ingredient> input;

    public MillingRecipe(Identifier id, DefaultedList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack createIcon() {
       return new ItemStack(Blocks.GRINDSTONE);
    }

    @Override
    public RecipeType<?> getType() {
        return BakersRecipes.MILLING;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        RecipeMatcher recipeFinder = new RecipeMatcher();
        int i = 0;

        for(int j = 0; j < inv.size(); ++j) {
           ItemStack itemStack = inv.getStack(j);
           if (!itemStack.isEmpty()) {
              i++;
              recipeFinder.addInput(itemStack);
           }
        }

        return i == input.size() && recipeFinder.match(this, null);
    }

    @Override
    public ItemStack craft(Inventory inv) {
        RecipeMatcher recipeFinder = new RecipeMatcher();

        for(int j = 0; j < inv.size(); ++j) {
           recipeFinder.addInput(inv.getStack(j));
        }

        ItemStack output = getOutput().copy();
        int count = recipeFinder.countCrafts(this, null);
        output.setCount(output.getCount() * count);

        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return (width * height) >= 2;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakersRecipesSerializers.MILLING;
    }

    public static class Serializer implements RecipeSerializer<MillingRecipe> {
        @Override
        public MillingRecipe read(Identifier identifier, JsonObject jsonObject) {
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));

            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }

            if (defaultedList.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }

            return new MillingRecipe(identifier, defaultedList, ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result")));
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();

            for(int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }

        @Override
        public MillingRecipe read(Identifier id, PacketByteBuf buffer) {
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(buffer.readVarInt(), Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); ++i) {
                ingredients.set(i, Ingredient.fromPacket(buffer));
            }

            return new MillingRecipe(id, ingredients, buffer.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buffer, MillingRecipe shapelessRecipe) {
            buffer.writeVarInt(shapelessRecipe.input.size());
            for (Ingredient i : shapelessRecipe.input) {
                i.write(buffer);
            }

            buffer.writeItemStack(shapelessRecipe.output);
        }
    }
}
