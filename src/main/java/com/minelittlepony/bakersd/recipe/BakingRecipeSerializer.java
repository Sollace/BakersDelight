package com.minelittlepony.bakersd.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.registry.Registries;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BakingRecipeSerializer<T extends BakingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final RecipeFactory<T> factory;

    public BakingRecipeSerializer(RecipeFactory<T> factory, int cookingTime) {
       this.defaultCookingTime = cookingTime;
       this.factory = factory;
    }

    @SuppressWarnings("deprecation")
    @Override
    public T read(Identifier id, JsonObject json) {
       JsonElement input = JsonHelper.hasArray(json, "ingredient")
               ? JsonHelper.getArray(json, "ingredient")
               : JsonHelper.getObject(json, "ingredient");

       String output = JsonHelper.getString(json, "result");

       return factory.create(id,
               JsonHelper.getString(json, "group", ""),
               CookingRecipeCategory.CODEC.byId(JsonHelper.getString(json, "category", null), CookingRecipeCategory.MISC),
               Ingredient.fromJson(input),
               new ItemStack(Registries.ITEM
                       .getOrEmpty(new Identifier(output))
                       .orElseThrow(() -> new IllegalStateException("Item: " + output + " does not exist"))
               ),
               JsonHelper.getFloat(json, "experience", 0),
               JsonHelper.getInt(json, "cookingtime", defaultCookingTime)
       );
    }

    @Override
    public T read(Identifier id, PacketByteBuf buffer) {
       return factory.create(id,
               buffer.readString(32767),
               buffer.readEnumConstant(CookingRecipeCategory.class),
               Ingredient.fromPacket(buffer),
               buffer.readItemStack(),
               buffer.readFloat(),
               buffer.readVarInt()
       );
    }

    @Override
    public void write(PacketByteBuf buffer, T recipe) {
       buffer.writeString(recipe.getGroup());
       buffer.writeEnumConstant(recipe.getCategory());
       recipe.getInput().write(buffer);
       buffer.writeItemStack(recipe.getOutput());
       buffer.writeFloat(recipe.getExperience());
       buffer.writeVarInt(recipe.getCookTime());
    }

    public interface RecipeFactory<T extends AbstractCookingRecipe> {
       T create(Identifier id, String group, CookingRecipeCategory category, Ingredient input, ItemStack defaultOutput, float experience, int cookTime);
    }
 }