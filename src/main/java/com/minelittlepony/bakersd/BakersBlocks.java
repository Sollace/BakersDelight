package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.bakersd.block.BoxBlock;

public interface BakersBlocks {

    Block YEAST = register(new BoxBlock(BoxBlock.SMALL, FabricBlockSettings.of(Material.WOOD).sounds(BoxBlock.SOUND).build()), "yeast");
    Block BAKING_SODA = register(new BoxBlock(BoxBlock.SMALL, FabricBlockSettings.of(Material.WOOD).sounds(BoxBlock.SOUND).build()), "baking_soda");

    static <T extends Block> T register(T block, String name) {
        return (T)Registry.register(Registry.BLOCK, new Identifier("bakersd", name), block);
    }

    static void bootstrap() { }
}
