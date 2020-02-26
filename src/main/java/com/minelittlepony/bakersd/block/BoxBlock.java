package com.minelittlepony.bakersd.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
        super(settings);

        shape = createCuboidShape(
                8 - dimensions.x/2, 0,            8 - dimensions.z/2,
                dimensions.x/2 + 8, dimensions.y, dimensions.z/2 + 8);
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    @Deprecated
    public Vec3d getOffsetPos(BlockState state, BlockView view, BlockPos pos) {
        Block.OffsetType offset = this.getOffsetType();
        if (offset == Block.OffsetType.NONE) {
            return Vec3d.ZERO;
        }

        long l = MathHelper.hashCode(pos);
        return new Vec3d(
                (((l & 15L) / 16F) - 0.5) / 2,
                offset == Block.OffsetType.XYZ ? (((l >> 4 & 15L) / 15F) - 1) * 0.2 : 0,
                (((l >> 8 & 15L) / 15F) - 0.5) / 2
        );
    }

    @Override
    @Deprecated
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        Vec3d offset = getOffsetPos(state, view, pos);
        return shape.offset(offset.x, offset.y, offset.z);
    }
}
