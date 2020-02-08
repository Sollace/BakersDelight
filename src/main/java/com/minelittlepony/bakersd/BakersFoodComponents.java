package com.minelittlepony.bakersd;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

public interface BakersFoodComponents {
    FoodComponent WHOLEGRAIN = FoodComponents.BREAD;
    FoodComponent DOUGH = new FoodComponent.Builder()
            .saturationModifier(0.9F)
            .alwaysEdible()
            .build();
    FoodComponent GOVERNMENT_LOAF = create(1, 0.7F);
    FoodComponent WORTH_LESS = create(1, 0.1F);
    FoodComponent WHEAT = create(6, 0.8F);
    FoodComponent ENHANCED_WHEAT = create(7, 0.9F);
    FoodComponent SOURDOUGH = create(7, 0.5F);
    FoodComponent RHY = create(8, 0.9F);
    FoodComponent CIABATTA = create(9, 0.4F);
    FoodComponent BANANA = create(1, 1F);

    static FoodComponent create(int hunger, float saturation) {
        return new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation)
                .build();
    }
}
