package com.minelittlepony.bakersd.block;

import java.util.Random;
import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Lazy;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TallCropBlock extends CropBlock {

    public static final IntProperty AGE = IntProperty.of("age", 0, 4);
    public static final EnumProperty<Half> HALF = EnumProperty.of("half", Half.class);

    private static final VoxelShape[] SHAPES;
    static {
        double[] sizes = new double[] {3.2, 6.4, 9.6, 12.8, 16};

        SHAPES = new VoxelShape[sizes.length * sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < sizes.length; j++) {
                SHAPES[j + (i * sizes.length)] = Block.createCuboidShape(0, 0, 0, 16, sizes[j] + (i * 16), 16);
            }
        }
    }

    private final Lazy<Item> seeds;

    public TallCropBlock(Block.Settings settings, Supplier<Item> seedSupplier) {
       super(settings);
       seeds = new Lazy<>(seedSupplier);
       setDefaultState(getDefaultState().with(HALF, Half.TOP));
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem() {
       return seeds.get();
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 4;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (rand.nextInt(10) != 0 && world.getLightLevel(pos.up()) >= 2) {
            if (isFertilizable(world, pos, state, world.isClient)) {
                applyGrowth(world, pos, state);
            }
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView view, BlockPos pos) {
        return (state.getBlock() == this && !state.get(HALF).isTop()) || super.canPlantOnTop(state, view, pos);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {

        pos = pos.down();
        state = world.getBlockState(pos);

        if (state.getBlock() != this) {
            return;
        }

        world.breakBlock(pos, true);

        onBroken(world, pos, state);
    }

    @Override
    protected int getGrowthAmount(World world) {
        return super.getGrowthAmount(world) / 2;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
        BlockPos root = getRoot(view, pos);
        BlockPos diff = root.subtract(pos);
        Vec3d offset = state.getModelOffset(view, pos).add(diff.getX(), diff.getY(), diff.getZ());

        return SHAPES[Math.min(SHAPES.length - 1, getFullAge(view, root))].offset(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean isMature(BlockState state) {
        return getAge(state) >= getMaxAge();
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.down()).getBlock() != this && getFullAge(world, pos) <= 18;
    }

    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        pos = getGrowthPoint(world, pos);
        state = world.getBlockState(pos);

        int newAge = getAge(state) + getGrowthAmount(world);
        int max = getMaxAge();

        while (newAge > max) {
            world.setBlockState(pos, withAge(max).with(HALF, getHalfForPosition(world, pos)), 2);

            if (getFullAge(world, pos) > 18) {
                return;
            }

            newAge -= max;
            pos = pos.up();

            if (!world.isAir(pos)) {
                return;
            }
        }

        world.setBlockState(pos, withAge(newAge).with(HALF, getHalfForPosition(world, pos)), 2);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        state = super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);

        if (state.getBlock() == this && facing.getAxis() == Axis.Y) {
            return state.with(HALF, getHalfForPosition(world, pos));
        }
        return state;
    }

    protected BlockPos getGrowthPoint(BlockView world, BlockPos startingPos) {
        BlockPos above = startingPos.up();
        BlockState state = world.getBlockState(above);

        if (state.getBlock() == this) {
            if (isMature(state)) {
                return getGrowthPoint(world, above);
            }

            return above;
        }

        return startingPos;
    }

    protected BlockPos getRoot(BlockView world, BlockPos startingPos) {
        BlockPos below = startingPos.down();

        if (world.getBlockState(below).getBlock() == this) {
            return getRoot(world, below);
        }

        return startingPos;
    }

    protected Half getHalfForPosition(BlockView world, BlockPos pos) {
        boolean hasAbove = world.getBlockState(pos.up()).getBlock() == this;
        boolean hasBelow = world.getBlockState(pos.down()).getBlock() == this;

        if (hasAbove && hasBelow) {
            return Half.MIDDLE;
        }

        if (hasAbove) {
            return Half.BOTTOM;
        }

        if (!hasAbove && hasBelow && world.getBlockState(pos.down(3)).getBlock() == this) {
            return Half.CROWN;
        }

        return Half.TOP;
    }

    protected int getFullAge(BlockView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        int age = 0;

        if (state.getBlock() == this) {
            age += getAge(state);
            age += getFullAge(world, pos.up());
            if (world.getBlockState(pos.up()).getBlock() == this) {
                age++;
            }
        }

        return age;
    }

    public enum Half implements StringIdentifiable {
        CROWN,
        TOP,
        MIDDLE,
        BOTTOM;

        boolean isTop() {
            return this == TOP || this == CROWN;
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        @Override
        public String asString() {
            return toString();
        }
    }
}
