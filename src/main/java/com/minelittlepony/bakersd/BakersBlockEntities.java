package com.minelittlepony.bakersd;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import com.minelittlepony.bakersd.blockentity.BreadBlockEntity;

public interface BakersBlockEntities {
    BlockEntityType<BreadBlockEntity> BREAD_BOARD = register(FabricBlockEntityTypeBuilder.create(BreadBlockEntity::new, BakersBlocks.OAK_BREAD_BOARD), "bread_board");

    static <T extends BlockEntity> BlockEntityType<T> register(FabricBlockEntityTypeBuilder<T> builder, String name) {
        return (BlockEntityType<T>)Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier("bakersd", name), builder.build(null));
    }

    static void bootstrap() { }
}
