package com.minelittlepony.bakersd.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DoughItem extends Item {

    public DoughItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && !stack.getNbt().getBoolean("silent")) {
            getBakedItem(stack).ifPresent(item -> {
                tooltip.add(Text.translatable("tooltip.bakersd.once_baked", item.getName()).formatted(Formatting.ITALIC, Formatting.GRAY));
            });
        }
    }

    public static Optional<Item> getBakedItem(ItemStack stack) {
        if (stack.hasNbt()) {
            Identifier result = new Identifier(stack.getNbt().getString("bakeResult"));
            return Registries.ITEM.getOrEmpty(result);
        }

        return Optional.empty();
    }

    public static ItemStack appendBakeResult(ItemStack stack, ItemStack result) {
        stack.getOrCreateNbt().putString("bakeResult", Registries.ITEM.getId(result.getItem()).toString());
        return stack;
    }

}
