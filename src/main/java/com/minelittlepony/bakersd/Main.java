package com.minelittlepony.bakersd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.minelittlepony.bakersd.recipe.BakersRecipes;

import net.fabricmc.api.ClientModInitializer;

public class Main implements ClientModInitializer {

    public static final Logger logger = LogManager.getLogger("BakersD");

    @Override
    public void onInitializeClient() {
        BakersRecipes.bootstrap();
        BakersTags.bootstrap();
        BakersItems.bootstrap();
    }
}
