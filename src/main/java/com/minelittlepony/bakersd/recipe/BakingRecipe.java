package com.minelittlepony.bakersd.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import com.minelittlepony.bakersd.item.DoughItem;

public class BakingRecipe extends AbstractCookingRecipe {

    protected BakingRecipe(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(RecipeType.SMELTING, id, group, input, output, experience, cookTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BakersRecipesSerializers.BAKING;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        return super.matches(inv, world);
    }

    @Override
    public ItemStack craft(Inventory inv) {
        ItemStack stack = inv.getStack(0);
        return DoughItem.getBakedItem(stack).map(ItemStack::new).orElse(super.craft(inv));
    }

    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    public static class Serializer extends BakingRecipeSerializer<BakingRecipe> {
        public Serializer() {
            super(BakingRecipe::new, 200);
        }
    }
}
