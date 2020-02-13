package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.bakersd.block.BoxBlock;
import com.minelittlepony.bakersd.block.RyeBlock;
import com.minelittlepony.bakersd.block.TallCropBlock;

public interface BakersBlocks {

    Block YEAST = register(new BoxBlock(BoxBlock.SMALL, FabricBlockSettings.of(Material.WOOD).sounds(BoxBlock.SOUND).build()), "yeast");
    Block BAKING_SODA = register(new BoxBlock(BoxBlock.SMALL, FabricBlockSettings.of(Material.WOOD).sounds(BoxBlock.SOUND).build()), "baking_soda");


    Block RYE = register(new RyeBlock(FabricBlockSettings.of(Material.PLANT)
                        .noCollision()
                        .ticksRandomly()
                        .nonOpaque()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.CROP)
                        .build(),
                    () -> BakersItems.RYE
                ), "rye");

    Block CORN = register(new TallCropBlock(FabricBlockSettings.of(Material.PLANT)
                        .noCollision()
                        .ticksRandomly()
                        .nonOpaque()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.CROP)
                        .build(),
                    () -> BakersItems.CORN
                ), "corn");

    static <T extends Block> T register(T block, String name) {
        return (T)Registry.register(Registry.BLOCK, new Identifier("bakersd", name), block);
    }

    static void bootstrap() { }
}
