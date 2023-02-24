package com.minelittlepony.bakersd.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BoxBlock extends OrientedBlock {

    public static final Vec3d SMALL = new Vec3d(7, 9, 7);

    public static final BlockSoundGroup SOUND = new BlockSoundGroup(1.0F, 1.5F,
            SoundEvents.BLOCK_WOOD_BREAK,
            SoundEvents.BLOCK_WOOD_STEP,
            SoundEvents.BLOCK_WOOD_PLACE,
            SoundEvents.BLOCK_WOOD_HIT,
            SoundEvents.BLOCK_WOOD_FALL
    );

    private final VoxelShape shape;

    public BoxBlock(Vec3d dimensions, Settings settings) {
        super(settings.offsetType(OffsetType.XZ).dynamicBounds());

        shape = createCuboidShape(
                8 - dimensions.x/2, 0,            8 - dimensions.z/2,
                dimensions.x/2 + 8, dimensions.y, dimensions.z/2 + 8);
    }

    @Override
    @Deprecated
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
        Vec3d offset = state.getModelOffset(view, pos);
        return shape.offset(offset.x, offset.y, offset.z);
    }
}
