package com.minelittlepony.bakersd.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;

import com.minelittlepony.bakersd.BakersSounds;
import com.minelittlepony.bakersd.recipe.remainder.RemainderSetter;

public class RollingPinItem extends SwordItem {
    public RollingPinItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
       super(material, attackDamage, attackSpeed, settings);
       ((RemainderSetter)this).setRecipeRemainder(stack -> {

           stack = stack.copy();
           stack.damage(1, Item.RANDOM, null);

           return stack;
       });
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.world.playSoundFromEntity(null, target, BakersSounds.ROLLING_PIN_DONK, SoundCategory.BLOCKS, 100, 1);
        return super.postHit(stack, target, attacker);
    }
}
