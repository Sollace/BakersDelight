package com.minelittlepony.bakersd.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface BakersRecipesSerializers {
    RecipeSerializer<GrindingRecipe> GRINDING = register(new Identifier("bakersd", "grinding"), new GrindingRecipe.Serializer());
    RecipeSerializer<BakingRecipe> BAKING = register(new Identifier("bakersd", "baking"), new BakingRecipe.Serializer());
    RecipeSerializer<ShapelessRecipe> SHAPELESS = register(new Identifier("bakersd", "kneading_shapeless"), new ShapelessKneadingRecipe.Serializer());
    RecipeSerializer<ShapedRecipe> SHAPED = register(new Identifier("bakersd", "kneading_shaped"), new ShapedKneadingRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(Identifier id, S serializer) {
        return (S)Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
    }

    static void bootstrap() {}
}
