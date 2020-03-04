package com.minelittlepony.bakersd.blockentity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.packet.BlockEntityUpdateS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import com.minelittlepony.bakersd.BakersBlockEntities;
import com.minelittlepony.bakersd.BakersTags;

import javax.annotation.Nullable;

public class BreadBlockEntity extends BlockEntity implements Inventory, BlockEntityClientSerializable {

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

    public int getSlices() {
        return slices;
    }

    public ItemStack getStack() {
        return item;
    }

    @Override public void fromClientTag(CompoundTag tag) {fromTag(tag);}
    @Override public CompoundTag toClientTag(CompoundTag tag) {return toTag(tag);}
    @Override public CompoundTag toInitialChunkDataTag() { return toTag(new CompoundTag()); }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(pos, 127, toInitialChunkDataTag());
    }

    public ActionResult activate(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!item.isEmpty()) {
            if (stack.isEmpty()) {
                player.giveItemStack(item);

                markDirty();
                return ActionResult.SUCCESS;
            }

            if (stack.getItem() instanceof SwordItem) {
                slices--;
                player.giveItemStack(item.copy());
                stack.damage(1, player, t -> t.sendToolBreakStatus(hand));

                if (slices == 0) {
                    item = ItemStack.EMPTY;
                }

                markDirty();
                return ActionResult.SUCCESS;
            }
        }

        if (item.isEmpty() && stack.getItem().isIn(BakersTags.SLICEABLE)) {
            item = stack.split(1);
            player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);

            slices = MAX_SLICES;

            markDirty();
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void clear() {
        setInvStack(0, ItemStack.EMPTY);
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Override
    public boolean isInvEmpty() {
        return slices != MAX_SLICES || item.isEmpty();
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return slices != MAX_SLICES ? ItemStack.EMPTY : item;
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        return slices != MAX_SLICES ? ItemStack.EMPTY : item.split(amount);
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        ItemStack stack = item;
        clear();
        return stack;
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        slices = MAX_SLICES;
        item = stack;
        markDirty();
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }
}