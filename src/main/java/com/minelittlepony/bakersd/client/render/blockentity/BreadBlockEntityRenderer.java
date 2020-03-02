package com.minelittlepony.bakersd.client.render.blockentity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.bakersd.BreadModelLoader;
import com.minelittlepony.bakersd.blockentity.BreadBlockEntity;

public class BreadBlockEntityRenderer extends BlockEntityRenderer<BreadBlockEntity> {

    public BreadBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BreadBlockEntity entity, float delta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {

        ItemStack stack = entity.getStack();

        if (stack.isEmpty() || entity.getSlices() == 0) {
            return;
        }

        Direction direction = entity.getCachedState().get(Properties.HORIZONTAL_FACING).rotateYClockwise();
        if (direction.getAxis() == Direction.Axis.X) {
            direction = direction.getOpposite();
        }
        float angle = direction.asRotation();

        matrices.push();

        matrices.translate(0.5, 0.56, 0.5);

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(angle));

        ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();

        ModelIdentifier modelId = BreadModelLoader.getModelId(Registry.ITEM.getId(stack.getItem()), entity.getSlices());

        BakedModel bakedModel = renderer.getModels().getModelManager().getModel(modelId);
        renderer.renderItem(stack, ModelTransformation.Mode.FIXED, false, matrices, provider, light, overlay, bakedModel);

        matrices.pop();
    }

}
