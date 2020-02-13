package com.minelittlepony.bakersd.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BakingRecipeSerializer<T extends BakingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final RecipeFactory<T> factory;

    public BakingRecipeSerializer(RecipeFactory<T> factory, int cookingTime) {
       this.defaultCookingTime = cookingTime;
       this.factory = factory;
    }

    @Override
    public T read(Identifier id, JsonObject json) {
       JsonElement input = JsonHelper.hasArray(json, "ingredient")
               ? JsonHelper.getArray(json, "ingredient")
               : JsonHelper.getObject(json, "ingredient");

       String output = JsonHelper.getString(json, "result");

       return factory.create(id,
               JsonHelper.getString(json, "group", ""),
               Ingredient.fromJson(input),
               new ItemStack(Registry.ITEM
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
               Ingredient.fromPacket(buffer),
               buffer.readItemStack(),
               buffer.readFloat(),
               buffer.readVarInt()
       );
    }

    @Override
    public void write(PacketByteBuf buffer, T recipe) {
       buffer.writeString(recipe.getGroup());
       recipe.getInput().write(buffer);
       buffer.writeItemStack(recipe.getOutput());
       buffer.writeFloat(recipe.getExperience());
       buffer.writeVarInt(recipe.getCookTime());
    }

    public interface RecipeFactory<T extends AbstractCookingRecipe> {
       T create(Identifier id, String group, Ingredient input, ItemStack defaultOutput, float experience, int cookTime);
    }
 }