package com.minelittlepony.bakersd.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ShapelessGrindingRecipe implements GrindingRecipe {

    private final Identifier id;

    private final ItemStack output;

    private final DefaultedList<Ingredient> input;

    public ShapelessGrindingRecipe(Identifier id, DefaultedList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
       return new ItemStack(Blocks.GRINDSTONE);
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        RecipeFinder recipeFinder = new RecipeFinder();
        int i = 0;

        for(int j = 0; j < inv.getInvSize(); ++j) {
           ItemStack itemStack = inv.getInvStack(j);
           if (!itemStack.isEmpty()) {
              i++;
              recipeFinder.addItem(itemStack);
           }
        }

        return i == input.size() && recipeFinder.findRecipe(this, null);
    }

    @Override
    public ItemStack craft(Inventory inv) {
        RecipeFinder recipeFinder = new RecipeFinder();

        for(int j = 0; j < inv.getInvSize(); ++j) {
           recipeFinder.addItem(inv.getInvStack(j));
        }

        ItemStack output = getOutput().copy();
        int count = recipeFinder.countRecipeCrafts(this, null);
        output.setCount(output.getCount() * count);

        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return (width * height) >= 2;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
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
        return BakersRecipes.GRINDING_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<ShapelessGrindingRecipe> {
        @Override
        public ShapelessGrindingRecipe read(Identifier identifier, JsonObject jsonObject) {
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));

            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }

            if (defaultedList.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }

            return new ShapelessGrindingRecipe(identifier, defaultedList, ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result")));
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
        public ShapelessGrindingRecipe read(Identifier id, PacketByteBuf buffer) {
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(buffer.readVarInt(), Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); ++i) {
                ingredients.set(i, Ingredient.fromPacket(buffer));
            }

            return new ShapelessGrindingRecipe(id, ingredients, buffer.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buffer, ShapelessGrindingRecipe shapelessRecipe) {
            buffer.writeVarInt(shapelessRecipe.input.size());
            for (Ingredient i : shapelessRecipe.input) {
                i.write(buffer);
            }

            buffer.writeItemStack(shapelessRecipe.output);
        }
    }
}
