package com.minelittlepony.bakersd.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
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

    private static final Map<Direction, VoxelShape> SHAPES;

    static {
        VoxelShape base = Block.createCuboidShape(4, 0, 0, 12, 1, 16);
        SHAPES = Arrays.stream(Direction.values())
            .filter(d -> d.getAxis() != Axis.Y)
            .collect(Collectors.toMap(
                    Function.identity(),
                    d -> VoxelShapeUtil.rotate(base, d))
            );
    }

    public BreadBoardBlock(Settings settings) {
        super(settings);
    }

    @Override
    @Deprecated
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return SHAPES.getOrDefault(state.get(Properties.HORIZONTAL_FACING), VoxelShapes.fullCube());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BreadBlockEntity entity = (BreadBlockEntity)world.getBlockEntity(pos);

        return entity.activate(player, hand);
    }

    @Override
    public boolean onBlockAction(BlockState state, World world, BlockPos pos, int type, int data) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.onBlockAction(type, data);
     }

    @Override
    protected Direction getPlacementDirection(ItemPlacementContext ctx) {
        Direction dir = super.getPlacementDirection(ctx);

        Arm arm = ctx.getPlayer() == null ? Arm.RIGHT : ctx.getPlayer().getMainArm();

        if (ctx.getHand() == Hand.OFF_HAND) {
            arm = arm.getOpposite();
        }

        switch (arm) {
            case RIGHT: return dir.rotateYCounterclockwise();
            default: return dir.rotateYClockwise();
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new BreadBlockEntity();
    }
}
