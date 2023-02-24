package com.minelittlepony.bakersd.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.util.collection.DefaultedList;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minelittlepony.bakersd.recipe.BakingRecipe;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class MixinAbstractFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {
    private MixinAbstractFurnaceBlockEntity() { super(null, null, null); }

    @Inject(method = "craftRecipe", at = @At("HEAD"))
    private static void onCraftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> inventory, int maxCount, CallbackInfoReturnable<Boolean> info) {
        if (recipe instanceof BakingRecipe bakingRecipe) {
            bakingRecipe.prepare(inventory);
        }
    }
}
