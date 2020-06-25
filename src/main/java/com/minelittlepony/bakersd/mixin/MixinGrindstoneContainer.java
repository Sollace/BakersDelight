package com.minelittlepony.bakersd.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minelittlepony.bakersd.BakersTags;
import com.minelittlepony.bakersd.recipe.BakersRecipes;

@Mixin(GrindstoneScreenHandler.class)
abstract class MixinGrindstoneContainer extends ScreenHandler {

    @Shadow @Final
    private Inventory result;
    @Shadow @Final
    private Inventory input;
    @Shadow @Final
    private ScreenHandlerContext context;

    private MixinGrindstoneContainer() { super(null, 0); }

    @Inject(method = "updateResult()V", at = @At("HEAD"), cancellable = true)
    private void onUpdateResult(CallbackInfo info) {
        context.run((world, pos) -> {
            world.getServer()
                    .getRecipeManager()
                    .getFirstMatch(BakersRecipes.MILLING, input, world)
                    .ifPresent(recipe -> {
                        result.setStack(0, recipe.craft(input));
                        sendContentUpdates();
                        info.cancel();
                    });
        });
    }
}

@Mixin(targets = {
    "net.minecraft.screen.GrindstoneScreenHandler$2",
    "net.minecraft.screen.GrindstoneScreenHandler$3"
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
