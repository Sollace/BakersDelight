package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public interface BakersTags {
    Tag<Item> CRUSHABLES = TagRegistry.item(new Identifier("bakersd", "crushables"));
    Tag<Item> FLOURS = TagRegistry.item(new Identifier("bakersd", "flours"));
    Tag<Item> DOUGHS = TagRegistry.item(new Identifier("bakersd", "doughs"));
    Tag<Item> BREADS = TagRegistry.item(new Identifier("bakersd", "breads"));

    static void bootstrap() {};
}
