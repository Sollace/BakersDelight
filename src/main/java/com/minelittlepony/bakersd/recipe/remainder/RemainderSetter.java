package com.minelittlepony.bakersd.recipe.remainder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public interface RemainderSetter {
    void setRecipeRemainder(Item item);

    void setRecipeRemainder(Function<ItemStack, ItemStack> supplier);
}
