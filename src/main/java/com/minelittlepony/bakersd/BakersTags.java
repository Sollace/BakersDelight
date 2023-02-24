package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public interface BakersTags {
    TagKey<Item> CRUSHABLES = of("crushables");
    TagKey<Item> FLOURS = of("flours");
    TagKey<Item> DOUGHS = of("doughs");
    TagKey<Item> BREADS = of("breads");
    TagKey<Item> SLICEABLE = of("sliceable");

    static TagKey<Item> of(String name) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("bakersd", name));
    }

    static void bootstrap() {
        FuelRegistry.INSTANCE.add(BREADS, 100);
        FuelRegistry.INSTANCE.add(DOUGHS, 20);
    };
}
