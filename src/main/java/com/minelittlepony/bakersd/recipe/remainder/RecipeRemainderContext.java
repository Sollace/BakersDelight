package com.minelittlepony.bakersd.recipe.remainder;

import net.minecraft.item.ItemStack;

public interface RecipeRemainderContext {
    void setContainingStack(ItemStack stack);

    boolean isRecipeRemainder();
}
