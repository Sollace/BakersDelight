package com.minelittlepony.bakersd.mixin;

import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.GrindstoneContainer;
import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minelittlepony.bakersd.BakersTags;
import com.minelittlepony.bakersd.recipe.BakersRecipes;

@Mixin(GrindstoneContainer.class)
abstract class MixinGrindstoneContainer extends Container {

    @Shadow @Final
    private Inventory resultInventory;
    @Shadow @Final
    private Inventory craftingInventory;
    @Shadow @Final
    private BlockContext context;

    private MixinGrindstoneContainer() { super(null, 0); }

    @Inject(method = "updateResult()V", at = @At("HEAD"), cancellable = true)
    private void onUpdateResult(CallbackInfo info) {
        context.run((world, pos) -> {
            world.getServer()
                    .getRecipeManager()
                    .getFirstMatch(BakersRecipes.MILLING, craftingInventory, world)
                    .ifPresent(recipe -> {
                        resultInventory.setInvStack(0, recipe.craft(craftingInventory));
                        sendContentUpdates();
                        info.cancel();
                    });
        });
    }
}

@Mixin(targets = {
    "net.minecraft.container.GrindstoneContainer$2",
    "net.minecraft.container.GrindstoneContainer$3"
})
abstract class MixinGrindstoneContainer_2 extends Slot {
    public MixinGrindstoneContainer_2() {super(null, 0, 0, 0);}

    @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onCanInsert(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (BakersTags.CRUSHABLES.contains(stack.getItem())) {
            info.setReturnValue(true);
        }
    }
}
