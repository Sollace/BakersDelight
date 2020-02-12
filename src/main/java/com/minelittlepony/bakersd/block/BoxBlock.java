package com.minelittlepony.bakersd.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import javax.annotation.Nullable;

public class BoxBlock extends WaterloggedBlock {

    public static final Vec3d SMALL = new Vec3d(7, 9, 7);

    public static final BlockSoundGroup SOUND = new BlockSoundGroup(1.0F, 1.5F, SoundEvents.BLOCK_WOOD_BREAK, SoundEvents.BLOCK_WOOD_STEP, SoundEvents.BLOCK_WOOD_PLACE, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL);

    private final VoxelShape shape;

    public BoxBlock(Vec3d dimensions, Settings settings) {
        super(settings.nonOpaque());

        shape = createCuboidShape(
                8 - dimensions.x/2, 0,            8 - dimensions.z/2,
                dimensions.x/2 + 8, dimensions.y, dimensions.z/2 + 8);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
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

    @Override
    @Deprecated
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        pos = pos.down();
        return Block.isFaceFullSquare(world.getBlockState(pos).getCollisionShape(world, pos, EntityContext.absent()), Direction.UP);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
       return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.HORIZONTAL_FACING);
    }
}
