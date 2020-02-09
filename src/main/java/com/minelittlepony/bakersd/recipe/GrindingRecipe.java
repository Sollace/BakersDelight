package com.minelittlepony.bakersd.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public interface GrindingRecipe extends Recipe<Inventory> {
    @Override
    default RecipeType<?> getType() {
        return BakersRecipes.GRINDING;
    }
}