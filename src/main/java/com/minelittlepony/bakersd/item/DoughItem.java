package com.minelittlepony.bakersd.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public class DoughItem extends Item {

    public DoughItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasTag() && !stack.getTag().getBoolean("silent")) {
            getBakedItem(stack).ifPresent(item -> {
                tooltip.add(new TranslatableText("tooltip.bakersd.once_baked", item.getName()).formatted(Formatting.ITALIC, Formatting.GRAY));
            });
        }
    }

    public static Optional<Item> getBakedItem(ItemStack stack) {
        if (stack.hasTag()) {
            Identifier result = new Identifier(stack.getTag().getString("bakeResult"));
            return Registry.ITEM.getOrEmpty(result);
        }

        return Optional.empty();
    }

    public static ItemStack appendBakeResult(ItemStack stack, ItemStack result) {
        stack.getOrCreateTag().putString("bakeResult", Registry.ITEM.getId(result.getItem()).toString());
        return stack;
    }

}
