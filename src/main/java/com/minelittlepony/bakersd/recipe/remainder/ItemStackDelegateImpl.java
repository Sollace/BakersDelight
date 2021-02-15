package com.minelittlepony.bakersd.recipe.remainder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class ItemStackDelegateImpl extends Item implements StackDelegate {

    private final Function<ItemStack, ItemStack> supplier;
    private ItemStack context = ItemStack.EMPTY;

    public ItemStackDelegateImpl(Function<ItemStack, ItemStack> supplier) {
        super(new Settings());
        this.supplier = supplier;
    }

    @Override
    public boolean isRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getStack() {
        ItemStack result = supplier.apply(context);
        if (result == null) {
            result = ItemStack.EMPTY;
        }
        return result.equals(context) ? ItemStack.EMPTY : result;
    }

    @Override
    public void setContainingStack(ItemStack stack) {
        this.context = (stack == null || stack.isEmpty()) ? ItemStack.EMPTY : stack;
    }
}
