package com.minelittlepony.bakersd.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface BakersRecipes {
    RecipeType<MillingRecipe> MILLING = register(new Identifier("bakersd", "milling"));

    static <T extends Recipe<?>> RecipeType<T> register(Identifier id) {
        return Registry.register(Registries.RECIPE_TYPE, id, new RecipeType<T>() {
            @Override
            public String toString() {
                return id.toString();
            }
        });
    }

    static void bootstrap() {
        BakersRecipesSerializers.bootstrap();
    }
}
