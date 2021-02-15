package com.minelittlepony.bakersd.recipe.remainder;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public interface StackDelegate extends ItemConvertible, RecipeRemainderContext {
    ItemStack getStack();

    static StackDelegate of(Function<ItemStack, ItemStack> supplier) {
        return new ItemStackDelegateImpl(supplier);
    }
}
