package com.minelittlepony.bakersd.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import com.minelittlepony.bakersd.BakersBlockEntities;
import com.minelittlepony.bakersd.BakersTags;

public class BreadBlockEntity extends BlockEntity {

    public static int MAX_SLICES = 8;

    private int slices;
    private ItemStack item = ItemStack.EMPTY;

    public BreadBlockEntity() {
        super(BakersBlockEntities.BREAD_BOARD);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        slices = tag.getInt("slices");
        item = ItemStack.fromTag(tag.getCompound("item"));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag = super.toTag(tag);
        tag.putInt("slices", slices);
        tag.put("item", item.toTag(new CompoundTag()));
        return tag;
    }

    @Override
    public boolean onBlockAction(int type, int value) {
        return false;
    }

    public int getSlices() {
        return slices;
    }

    public ItemStack getStack() {
        return item;
    }

    public ActionResult activate(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!item.isEmpty()) {
            if (stack.isEmpty()) {
                player.giveItemStack(item);

                return ActionResult.SUCCESS;
            }

            if (stack.getItem() instanceof SwordItem) {
                slices--;
                player.giveItemStack(item.copy());
                stack.damage(1, player, t -> t.sendToolBreakStatus(hand));

                if (slices == 0) {
                    item = ItemStack.EMPTY;
                }

                return ActionResult.SUCCESS;
            }
        }

        if (item.isEmpty() && stack.getItem().isIn(BakersTags.SLICEABLE)) {
            item = stack.split(1);
            slices = MAX_SLICES;

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}