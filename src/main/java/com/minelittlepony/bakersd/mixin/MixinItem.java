package com.minelittlepony.bakersd.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minelittlepony.bakersd.recipe.remainder.RecipeRemainderContext;
import com.minelittlepony.bakersd.recipe.remainder.RemainderSetter;
import com.minelittlepony.bakersd.recipe.remainder.StackDelegate;

import javax.annotation.Nullable;

import java.util.function.Function;

@Mixin(Item.class)
abstract class MixinItem implements ItemConvertible, RemainderSetter, RecipeRemainderContext {

    @Nullable
    private StackDelegate recipeRemainderStackDelegate;

    @Override
    @Accessor("recipeRemainder")
    public abstract void setRecipeRemainder(Item item);

    @Override
    public boolean isRecipeRemainder() {
        return false;
    }

    @Override
    public void setRecipeRemainder(Function<ItemStack, ItemStack> supplier) {
        recipeRemainderStackDelegate = StackDelegate.of(supplier);
    }

    @Override
    public void setContainingStack(ItemStack stack) {
        if (recipeRemainderStackDelegate != null) {
            recipeRemainderStackDelegate.setContainingStack(stack);
        }
    }

    @Inject(method = "getRecipeRemainder", at = @At("RETURN"), cancellable = true)
    private void onGetRemainder(CallbackInfoReturnable<Item> info) {
        if (recipeRemainderStackDelegate != null) {
            info.setReturnValue(recipeRemainderStackDelegate.asItem());
        }
    }
}
