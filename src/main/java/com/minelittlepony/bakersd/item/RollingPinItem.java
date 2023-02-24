package com.minelittlepony.bakersd.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.random.Random;

import com.minelittlepony.bakersd.BakersSounds;

public class RollingPinItem extends SwordItem {
    private static final Random RANDOM = Random.create();

    public RollingPinItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
       super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        stack = stack.copy();
        stack.damage(1, RANDOM, null);
        return stack;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.world.playSoundFromEntity(null, target, BakersSounds.ROLLING_PIN_DONK, SoundCategory.BLOCKS, 100, 1);
        return super.postHit(stack, target, attacker);
    }
}
