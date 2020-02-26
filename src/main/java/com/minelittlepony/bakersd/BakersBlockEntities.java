package com.minelittlepony.bakersd;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.bakersd.blockentity.BreadBlockEntity;

public interface BakersBlockEntities {
    BlockEntityType<BreadBlockEntity> BREAD_BOARD = register(BlockEntityType.Builder.create(BreadBlockEntity::new, BakersBlocks.OAK_BREAD_BOARD), "bread_board");

    static <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType.Builder<T> builder, String name) {
        return (BlockEntityType<T>)Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("bakersd", name), builder.build(null));
    }

    static void bootstrap() { }
}
