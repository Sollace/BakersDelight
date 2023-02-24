package com.minelittlepony.bakersd.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import com.minelittlepony.bakersd.BakersBlockEntities;
import com.minelittlepony.bakersd.BakersTags;
import com.minelittlepony.bakersd.item.BreadItem;

public class BreadBlockEntity extends BlockEntity implements Inventory {

    private int slices;
    private ItemStack item = ItemStack.EMPTY;

    public BreadBlockEntity(BlockPos pos, BlockState state) {
        super(BakersBlockEntities.BREAD_BOARD, pos, state);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        slices = tag.getInt("slices");
        item = ItemStack.fromNbt(tag.getCompound("item"));
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("slices", slices);
        tag.put("item", item.writeNbt(new NbtCompound()));
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound compound = new NbtCompound();
        writeNbt(compound);
        return compound;
    }

    public int getSlices() {
        return slices;
    }

    public ItemStack getStack() {
        return item;
    }

    public ActionResult activate(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.isCreative()) {
            stack = stack.copy();
        }

        if (item.isEmpty() && stack.isIn(BakersTags.SLICEABLE)) {
            setStack(0, stack.split(1));
            player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);

            return ActionResult.SUCCESS;
        }

        if (!item.isEmpty()) {
            if (stack.isEmpty()) {
                player.giveItemStack(BreadItem.putSlices(item, slices));

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

        return ActionResult.PASS;
    }

    @Override
    public void clear() {
        setStack(0, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return slices != BreadItem.MAX_SLICES || item.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return slices != BreadItem.MAX_SLICES ? ItemStack.EMPTY : BreadItem.putSlices(item.copy(), slices);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return slices != BreadItem.MAX_SLICES ? ItemStack.EMPTY : BreadItem.putSlices(item.split(amount), slices);
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = BreadItem.putSlices(item, slices);
        clear();
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        slices = BreadItem.getSlices(stack);
        item = stack;
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}