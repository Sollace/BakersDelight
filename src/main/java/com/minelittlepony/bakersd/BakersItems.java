package com.minelittlepony.bakersd;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.bakersd.item.RollingPinItem;

public interface BakersItems {

    Item CORN = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "corn");
    Item RYE = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "rye");
    Item RAISINS = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "raisins");
    Item BANANA = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "banana");

    Item ROLLING_PIN = register(new RollingPinItem(ToolMaterials.WOOD, 0, 78, new Item.Settings().group(ItemGroup.TOOLS)), "rolling_pin");

    Item CORN_FLOUR = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "corn_flour");//
    Item WHOLEGRAIN_FLOUR = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "wholegrain_flour");//
    Item RYE_FLOUR = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "rye_flour");//
    Item WHITE_FLOUR = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "white_flour");//
    Item BROWN_FLOUR = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "brown_flour");//
    Item WHEAT_FLOUR = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "wheat_flour");//

    Item YEAST = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "yeast");
    Item BAKING_SODA = register(new Item(new Item.Settings().group(ItemGroup.MISC)), "baking_soda");

    Item WHOLEGRAIN_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "wholegrain_dough");//
    Item RYE_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "rye_dough");//
    Item CORN_BREAD_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "corn_bread_dough");//
    Item WHITE_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "white_dough");//
    Item BROWN_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "brown_dough");//
    Item WHEAT_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "wheat_dough");//
    Item WHITE_SODA_BREAD_DOUGH = register(new Item(new Item.Settings().food(BakersFoodComponents.DOUGH).group(ItemGroup.FOOD)), "white_soda_bread_dough");//

    Item WHOLEGRAIN_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.WHOLEGRAIN).group(ItemGroup.FOOD)), "wholegrain_bread");//
    Item WHITE_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF).group(ItemGroup.FOOD)), "white_bread");//
    Item BROWN_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF).group(ItemGroup.FOOD)), "brown_bread");//
    Item WHEAT_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "wheat_bread");//

    Item SOURDOUGH_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.SOURDOUGH).group(ItemGroup.FOOD)), "sourdough_bread");
    Item RYE_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.RYE).group(ItemGroup.FOOD)), "rye_bread");//
    Item BAGUETTE = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "baguette");//

    Item BUN = register(new Item(new Item.Settings().food(BakersFoodComponents.WHOLEGRAIN).group(ItemGroup.FOOD)), "bun");//

    Item PORTUGUESE_ROLL = register(new Item(new Item.Settings().food(BakersFoodComponents.WHOLEGRAIN).group(ItemGroup.FOOD)), "portuguese_roll");//
    Item CIABATTA_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.CIABATTA).group(ItemGroup.FOOD)), "ciabatta_bread");//
    Item PITA_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.WORTH_LESS).group(ItemGroup.FOOD)), "pita_bread");//

    Item BANANA_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.BANANA).group(ItemGroup.FOOD)), "banana_bread");
    Item RAISIN_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.BANANA).group(ItemGroup.FOOD)), "raisin_bread");

    Item TORTILLA = register(new Item(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF).group(ItemGroup.FOOD)), "tortilla");//
    Item BREAD_STICK = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "bread_stick");//

    Item CROISSANT = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "croissant");//
    Item CHOCOLATE_CROISSANT = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "chocolate_croissant");//

    // white_corn_bread
    Item GRITS = register(new Item(new Item.Settings().food(BakersFoodComponents.GOVERNMENT_LOAF).group(ItemGroup.FOOD)), "grits");
    // yellow_corn_bread
    Item POLENTA_LOAF = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "polenta_loaf");//

    // https://en.wikipedia.org/wiki/Soda_bread
    Item SODA_BREAD = register(new Item(new Item.Settings().food(BakersFoodComponents.BANANA).group(ItemGroup.FOOD)), "soda_bread");//

    // https://en.wikipedia.org/wiki/Zopf
    Item TRESSE = register(new Item(new Item.Settings().food(BakersFoodComponents.WHEAT).group(ItemGroup.FOOD)), "tresse");//

    Item BAGEL = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT).group(ItemGroup.FOOD)), "bagel");
    Item PRETZEL = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT).group(ItemGroup.FOOD)), "pretzel");
    Item GINGER_BREAD_MAN = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT).group(ItemGroup.FOOD)), "ginger_bread_man");
    Item GINGER_BREAD_WOMAN = register(new Item(new Item.Settings().food(BakersFoodComponents.ENHANCED_WHEAT).group(ItemGroup.FOOD)), "ginger_bread_woman");

    static <T extends Item> T register(T item, String name) {
        return (T)Registry.register(Registry.ITEM, new Identifier("bakersd", name), item);
    }

    static void bootstrap() { }
}
