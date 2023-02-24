package com.minelittlepony.bakersd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.minelittlepony.bakersd.recipe.BakersRecipes;

import java.util.List;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {

    public static final Logger logger = LogManager.getLogger("BakersD");

    @Override
    public void onInitialize() {
        BakersSounds.bootstrap();
        BakersRecipes.bootstrap();
        BakersTags.bootstrap();
        BakersItems.bootstrap();

        LootTableEvents.MODIFY.register((res, manager, id, supplier, setter) -> {
            if (!"minecraft".contentEquals(id.getNamespace())) {
                return;
            }

            Identifier modId = new Identifier("bakersdmc", id.getPath());
            LootTable table = manager.getTable(modId);
            if (table != LootTable.EMPTY) {
                supplier.pools(List.of(table.pools));
            }
        });
    }
}
