package com.minelittlepony.bakersd.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface BakersRecipes {
    RecipeType<GrindingRecipe> GRINDING = register(new Identifier("bakersd", "grinding"));

    RecipeSerializer<ShapelessGrindingRecipe> GRINDING_SERIALIZER = register(new Identifier("bakersd", "grinding"), new ShapelessGrindingRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(Identifier id, S serializer) {
        return (S)Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
    }

    static <T extends Recipe<?>> RecipeType<T> register(Identifier id) {
        return Registry.register(Registry.RECIPE_TYPE, id, new RecipeType<T>() {
            @Override
            public String toString() {
                return id.toString();
            }
        });
    }

    static void bootstrap() {}
}
