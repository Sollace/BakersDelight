package com.minelittlepony.bakersd.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.util.Tickable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceBlockEntity.class)
abstract class MixinAbstractFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable {
    private MixinAbstractFurnaceBlockEntity() { super(null); }

    @SuppressWarnings("unchecked")
    @Redirect(
            method = {
                    "craftRecipe(Lnet/minecraft/recipe/Recipe;)V",
                    "canAcceptRecipeOutput(Lnet/minecraft/recipe/Recipe;)Z"},
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/recipe/Recipe;getOutput()Lnet/minecraft/item/ItemStack;"))
    private ItemStack redirectGetOutput(Recipe<?> instance) {
        return ((Recipe<Inventory>)instance).craft(this);
    }
}
