package com.minelittlepony.bakersd;

import java.util.*;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;

public interface ItemGroupRegistry {
    Map<ItemGroup, Set<Item>> REGISTRY = new HashMap<>();

    static <T extends Item> T register(T item, ItemGroup group) {
        REGISTRY.computeIfAbsent(group, g -> new HashSet<>()).add(item);
        return item;
    }

    static void bootstrap() {
        REGISTRY.forEach((group, items) -> {
            ItemGroupEvents.modifyEntriesEvent(group).register(event -> {
                event.addAll(items.stream().map(Item::getDefaultStack).toList());
            });
        });
    }
}
