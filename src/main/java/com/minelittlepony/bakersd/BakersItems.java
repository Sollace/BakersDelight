package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import com.minelittlepony.bakersd.item.BaguetteItem;
import com.minelittlepony.bakersd.item.BreadItem;
import com.minelittlepony.bakersd.item.DoughItem;
import com.minelittlepony.bakersd.item.FabricToolMaterials;
import com.minelittlepony.bakersd.item.RollingPinItem;

import java.util.ArrayList;
import java.util.List;

public interface BakersItems {
    List<Item> REGISTRY = new ArrayList<>();
    ItemGroup GROUP = FabricItemGroup.builder(new Identifier("bakersd", "breads"))
            .icon(() -> new ItemStack(Items.BREAD))
            .entries((features, list, k) -> {
                REGISTRY.forEach(list::add);
            })
            .build();

    Item CORN = register(new AliasedBlockItem(BakersBlocks.CORN, new Item.Settings()), "corn", ItemGroups.FOOD_AND_DRINK);
    Item RYE = register(new AliasedBlockItem(BakersBlocks.RYE, new Item.Settings()), "rye", ItemGroups.FOOD_AND_DRINK);
    Item RAISINS = register(new Item(new Item.Settings()), "raisins", ItemGroups.FOOD_AND_DRINK);
    Item BANANA = register(new Item(new Item.Settings()), "banana", ItemGroups.FOOD_AND_DRINK);

    Item ROLLING_PIN = register(new RollingPinItem(ToolMaterials.WOOD, 0, 78, new Item.Settings()), "rolling_pin", ItemGroups.TOOLS);

    Item OAK_BREAD_BOARD = register(new BlockItem(BakersBlocks.OAK_BREAD_BOARD, new Item.Settings()), "oak_bread_board", ItemGroups.TOOLS);

    Item CORN_FLOUR = register(new Item(new Item.Settings()), "corn_flour", ItemGroups.INGREDIENTS);
    Item WHOLEGRAIN_FLOUR = register(new Item(new Item.Settings()), "wholegrain_flour", ItemGroups.INGREDIENTS);
    Item RYE_FLOUR = register(new Item(new Item.Settings()), "rye_flour", ItemGroups.INGREDIENTS);
    Item WHITE_FLOUR = register(new Item(new Item.Settings()), "white_flour", ItemGroups.INGREDIENTS);
    Item BROWN_FLOUR = register(new Item(new Item.Settings()), "brown_flour", ItemGroups.INGREDIENTS);
    Item WHEAT_FLOUR = register(new Item(new Item.Settings()), "wheat_flour", ItemGroups.INGREDIENTS);

    Item YEAST = register(new BlockItem(BakersBlocks.YEAST, new Item.Settings()), "yeast", ItemGroups.INGREDIENTS);
    Item BAKING_SODA = register(new BlockItem(BakersBlocks.BAKING_SODA, new Item.Settings()), "baking_soda", ItemGroups.INGREDIENTS);
    Item GINGER = register(new BlockItem(BakersBlocks.BAKING_SODA, new Item.Settings()), "ginger", ItemGroups.INGREDIENTS);

    Item WHOLEGRAIN_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "wholegrain_dough", ItemGroups.FOOD_AND_DRINK);
    Item RYE_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "rye_dough", ItemGroups.FOOD_AND_DRINK);
    Item CORN_BREAD_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "corn_bread_dough", ItemGroups.FOOD_AND_DRINK);
    Item WHITE_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "white_dough", ItemGroups.FOOD_AND_DRINK);
    Item BROWN_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "brown_dough", ItemGroups.FOOD_AND_DRINK);
    Item WHEAT_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "wheat_dough", ItemGroups.FOOD_AND_DRINK);
    Item WHITE_SODA_BREAD_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "white_soda_bread_dough", ItemGroups.FOOD_AND_DRINK);
    Item GINGERBREAD_DOUGH = register(new DoughItem(new Item.Settings().food(BakersFoodComponents.DOUGH)), "gingerbread_dough", ItemGroups.FOOD_AND_DRINK);

    Item WHOLEGRAIN_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.WHOLEGRAIN)), "wholegrain_bread", ItemGroups.FOOD_AND_DRINK);
    Item WHITE_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF)), "white_bread", ItemGroups.FOOD_AND_DRINK);
    Item BROWN_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF)), "brown_bread", ItemGroups.FOOD_AND_DRINK);
    Item WHEAT_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.WHEAT)), "wheat_bread", ItemGroups.FOOD_AND_DRINK);

    // TODO: sourdough requires fermentation
    // Item SOURDOUGH_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.SOURDOUGH)), "sourdough_bread");
    Item RYE_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.RYE)), "rye_bread", ItemGroups.FOOD_AND_DRINK);
    Item BAGUETTE = register(new BaguetteItem(new FabricToolMaterials.Builder()
            .repairIngredient(() -> Ingredient.fromTag(BakersTags.BREADS))
            .durability(16)
            .enchantability(15)
            .level(1)
            .build(), 2, 8, new Item.Settings().food(BakersFoodComponents.WHEAT)), "baguette", ItemGroups.FOOD_AND_DRINK);

    Item BUN = register(new Item(new Item.Settings().food(BakersFoodComponents.WHOLEGRAIN)), "bun", ItemGroups.FOOD_AND_DRINK);

    Item PORTUGUESE_ROLL = register(new Item(new Item.Settings().food(BakersFoodComponents.WHOLEGRAIN)), "portuguese_roll", ItemGroups.FOOD_AND_DRINK);
    Item CIABATTA_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.CIABATTA)), "ciabatta_bread", ItemGroups.FOOD_AND_DRINK);
    Item PITA_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.WORTH_LESS)), "pita_bread", ItemGroups.FOOD_AND_DRINK);

    Item BANANA_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.BANANA)), "banana_bread", ItemGroups.FOOD_AND_DRINK);
    Item RAISIN_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.BANANA)), "raisin_bread", ItemGroups.FOOD_AND_DRINK);

    Item TORTILLA = register(new Item(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF)), "tortilla", ItemGroups.FOOD_AND_DRINK);
    Item BREAD_STICK = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT)), "bread_stick", ItemGroups.FOOD_AND_DRINK);

    Item CROISSANT = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT)), "croissant", ItemGroups.FOOD_AND_DRINK);
    Item CHOCOLATE_CROISSANT = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT)), "chocolate_croissant", ItemGroups.FOOD_AND_DRINK);

    Item GRITS = register(new Item(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF)), "grits", ItemGroups.FOOD_AND_DRINK);
    Item POLENTA_LOAF = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.WHEAT)), "polenta_loaf", ItemGroups.FOOD_AND_DRINK);

    // https:en.wikipedia.org/wiki/Soda_bread
    Item SODA_BREAD = register(new BreadItem(new Item.Settings().food(BakersFoodComponents.BANANA)), "soda_bread", ItemGroups.FOOD_AND_DRINK);

    // https:en.wikipedia.org/wiki/Zopf
    Item TRESSE = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT)), "tresse", ItemGroups.FOOD_AND_DRINK);

    Item BAGEL = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT)), "bagel", ItemGroups.FOOD_AND_DRINK);
    Item PRETZEL = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT)), "pretzel", ItemGroups.FOOD_AND_DRINK);
    Item GINGER_BREAD_MAN = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT)), "ginger_bread_man", ItemGroups.FOOD_AND_DRINK);
    Item GINGER_BREAD_WOMAN = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT)), "ginger_bread_woman", ItemGroups.FOOD_AND_DRINK);

    static <T extends Item> T register(T item, String name) {
        REGISTRY.add(item);
        return Registry.register(Registries.ITEM, new Identifier("bakersd", name), item);
    }

    static <T extends Item> T register(T item, String name, ItemGroup group) {
        ItemGroupRegistry.register(item, group);
        return register(item, name);
    }

    static void bootstrap() {
        BakersBlockEntities.bootstrap();
        ItemGroupRegistry.bootstrap();
    }
}
