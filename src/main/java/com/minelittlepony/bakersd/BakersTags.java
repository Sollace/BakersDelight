package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public interface BakersTags {
    Tag<Item> CRUSHABLES = TagRegistry.item(new Identifier("bakersd", "crushables"));
    Tag<Item> FLOURS = TagRegistry.item(new Identifier("bakersd", "flours"));
    Tag<Item> DOUGHS = TagRegistry.item(new Identifier("bakersd", "doughs"));
    Tag<Item> BREADS = TagRegistry.item(new Identifier("bakersd", "breads"));
    Tag<Item> SLICEABLE = TagRegistry.item(new Identifier("bakersd", "sliceable"));

    static void bootstrap() {
        FuelRegistry.INSTANCE.add(BakersTags.BREADS, 100);
        FuelRegistry.INSTANCE.add(BakersTags.DOUGHS, 20);
    };
}
