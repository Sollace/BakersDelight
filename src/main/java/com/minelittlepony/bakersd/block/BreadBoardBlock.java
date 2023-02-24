package com.minelittlepony.bakersd.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.minelittlepony.bakersd.blockentity.BreadBlockEntity;
import com.minelittlepony.bakersd.util.shape.VoxelShapeUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BreadBoardBlock extends OrientedBlock implements BlockEntityProvider {

    private static final Map<Direction, VoxelShape> SHAPES = Arrays.stream(Direction.values())
            .filter(d -> d.getAxis() != Axis.Y)
            .collect(Collectors.toMap(
                    Function.identity(),
                    VoxelShapeUtil.rotator(Block.createCuboidShape(4, 0, 0, 12, 1, 16)))
            );

    public BreadBoardBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity entity = world.getBlockEntity(pos);

        if (entity instanceof Inventory && !((Inventory)entity).isEmpty()) {
            return ((Inventory)entity).getStack(0);
        }
        return super.getPickStack(world, pos, state);
    }

    @Override
    @Deprecated
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient) {
            return;
        }

        if (entity.fallDistance <= 0) {
            return;
        }

        BlockEntity be = world.getBlockEntity(pos);

        if (be instanceof BreadBlockEntity && !((BreadBlockEntity)be).getStack().isEmpty()) {
            int rng = (int)Math.ceil(200 / entity.fallDistance);
            if (rng <= 0 || world.random.nextInt(rng) == 0) {
                world.breakBlock(pos, true);
                world.setBlockState(pos, state);
            }
        }
    }

    @Override
    @Deprecated
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
        return SHAPES.getOrDefault(state.get(Properties.HORIZONTAL_FACING), VoxelShapes.fullCube());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BreadBlockEntity entity = (BreadBlockEntity)world.getBlockEntity(pos);

        ActionResult result = entity.activate(player, hand);

        world.updateNeighborsAlways(pos, this);

        return result;
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        BlockEntity e = world.getBlockEntity(pos);
        return e == null ? false : e.onSyncedBlockEvent(type, data);
    }

    @Override
    protected Direction getPlacementDirection(ItemPlacementContext ctx) {
        Direction dir = super.getPlacementDirection(ctx);

        Arm arm = ctx.getPlayer() == null ? Arm.RIGHT : ctx.getPlayer().getMainArm();

        if (ctx.getHand() == Hand.OFF_HAND) {
            arm = arm.getOpposite();
        }

        if (arm == Arm.RIGHT) {
            return dir.rotateYCounterclockwise();
        }
        return dir.rotateYClockwise();
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        return state.getWeakRedstonePower(view, pos, facing);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        return ((BreadBlockEntity)view.getBlockEntity(pos)).getSlices();
    }

    @Deprecated
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity e = world.getBlockEntity(pos);
            if (e instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory)e);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BreadBlockEntity(pos, state);
    }
}
