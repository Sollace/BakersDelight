package com.minelittlepony.bakersd.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minelittlepony.bakersd.recipe.remainder.RecipeRemainderContext;
import com.minelittlepony.bakersd.recipe.remainder.StackDelegate;

@Mixin(ItemStack.class)
abstract class MixinItemStack {
    @Shadow @Final @Mutable
    private Item item;

    @ModifyVariable(
            method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V",
            at = @At("RETURN"),
            index = 0)
    private ItemConvertible aaaa(ItemConvertible item) {
        if (((RecipeRemainderContext)item).isRecipeRemainder()) {
            ItemStack self = (ItemStack)(Object)this;
            ItemStack from = ((StackDelegate)item).getStack();

            this.item = from.getItem();
            self.setTag(from.getTag());
            self.setCount(from.getCount());
        }
        return item;
    }

    @Inject(method = "getItem()Lnet/minecraft/item/Item;", at = @At("RETURN"))
    private void onGetItem(CallbackInfoReturnable<Item> info) {
        ((RecipeRemainderContext)info.getReturnValue()).setContainingStack((ItemStack)(Object)this);
    }
}
