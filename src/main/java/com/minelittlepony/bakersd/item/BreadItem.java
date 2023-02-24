package com.minelittlepony.bakersd.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreadItem extends Item {

    public static int MAX_SLICES = 8;

    public BreadItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendTooltip(stack, tooltip);
    }

    public static void appendTooltip(ItemStack stack, List<Text> tooltip) {
        if (stack.hasNbt() && !stack.getNbt().getBoolean("silent")) {
            int slices = getSlices(stack);
            if (slices != MAX_SLICES) {
                tooltip.add(Text.translatable("tooltip.bakersd.slices", slices, MAX_SLICES).formatted(Formatting.ITALIC, Formatting.GRAY));
            }
        }
    }

    public static ItemStack putSlices(ItemStack stack, int slices) {
        if (slices == 0 || slices == MAX_SLICES) {
            stack.removeSubNbt("slices");
        } else {
            stack.getOrCreateNbt().putInt("slices", slices);
        }

        return stack;
    }

    public static int getSlices(ItemStack stack) {
        if (stack.isEmpty() || !stack.hasNbt()) {
            return MAX_SLICES;
        }
        int result = MathHelper.clamp(stack.getNbt().getInt("slices"), 0, MAX_SLICES);
        return result == 0 ? MAX_SLICES : result;
    }
}
