package com.minelittlepony.bakersd.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public interface BakersRecipesSerializers {
    RecipeSerializer<MillingRecipe> MILLING = register(new Identifier("bakersd", "milling"), new MillingRecipe.Serializer());
    RecipeSerializer<BakingRecipe> BAKING = register(new Identifier("bakersd", "baking"), new BakingRecipe.Serializer());
    RecipeSerializer<ShapelessRecipe> SHAPELESS = register(new Identifier("bakersd", "kneading_shapeless"), new ShapelessKneadingRecipe.Serializer());
    RecipeSerializer<ShapedRecipe> SHAPED = register(new Identifier("bakersd", "kneading_shaped"), new ShapedKneadingRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(Identifier id, S serializer) {
        return (S)Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
    }

    static void bootstrap() {}
}
