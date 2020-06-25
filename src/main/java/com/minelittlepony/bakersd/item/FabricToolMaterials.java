package com.minelittlepony.bakersd.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public class FabricToolMaterials implements ToolMaterial {

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    private FabricToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantibility, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantibility;
        this.repairIngredient = new Lazy<>(repairIngredient);
    }

    @Override
    public int getDurability() {
        return itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    public static class Builder {
        private int level;
        private int durability;
        private float speed;
        private float damage;
        private int enchantability;
        private Supplier<Ingredient> repairIngredient = () -> Ingredient.EMPTY;

        public Builder durability(int durability) {
            this.durability = durability;
            return this;
        }

        public Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder damage(float damage) {
            this.damage = damage;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder enchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public Builder repairIngredient(Supplier<Ingredient> supplier) {
            repairIngredient = supplier;
            return this;
        }

        public ToolMaterial build() {
            return new FabricToolMaterials(level, durability, speed, damage, enchantability, repairIngredient);
        }
    }
}
