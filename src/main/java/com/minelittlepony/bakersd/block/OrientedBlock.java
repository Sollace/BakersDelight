package com.minelittlepony.bakersd.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

import javax.annotation.Nullable;

public class OrientedBlock extends WaterloggedBlock {

    public OrientedBlock(Settings settings) {
        super(settings.nonOpaque());

        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    @Deprecated
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        pos = pos.down();
        return Block.isFaceFullSquare(world.getBlockState(pos).getCollisionShape(world, pos, EntityContext.absent()), Direction.UP);
    }

    protected Direction getPlacementDirection(ItemPlacementContext ctx) {
        return ctx.getPlayerFacing();
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, getPlacementDirection(ctx));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.HORIZONTAL_FACING);
    }
}
