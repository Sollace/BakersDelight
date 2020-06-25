package com.minelittlepony.bakersd;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.bakersd.block.BoxBlock;
import com.minelittlepony.bakersd.block.BreadBoardBlock;
import com.minelittlepony.bakersd.block.RyeBlock;
import com.minelittlepony.bakersd.block.TallCropBlock;

public interface BakersBlocks {
    Block YEAST = register(new BoxBlock(BoxBlock.SMALL, Block.Settings.of(Material.WOOD).sounds(BoxBlock.SOUND)), "yeast");
    Block BAKING_SODA = register(new BoxBlock(BoxBlock.SMALL, Block.Settings.of(Material.WOOD).sounds(BoxBlock.SOUND)), "baking_soda");
    Block OAK_BREAD_BOARD = register(new BreadBoardBlock(Block.Settings.of(Material.WOOD)
            .sounds(BlockSoundGroup.WOOD)
            .strength(2, 5)
            .slipperiness(1)), "oak_bread_board");

    Block RYE = register(new RyeBlock(Block.Settings.of(Material.PLANT)
                        .noCollision()
                        .ticksRandomly()
                        .nonOpaque()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.CROP),
                    () -> BakersItems.RYE
                ), "rye");

    Block CORN = register(new TallCropBlock(Block.Settings.of(Material.PLANT)
                        .noCollision()
                        .ticksRandomly()
                        .nonOpaque()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.CROP),
                    () -> BakersItems.CORN
                ), "corn");

    static <T extends Block> T register(T block, String name) {
        return (T)Registry.register(Registry.BLOCK, new Identifier("bakersd", name), block);
    }
}
