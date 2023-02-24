package com.minelittlepony.bakersd;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

import com.minelittlepony.bakersd.client.render.blockentity.BreadBlockEntityRenderer;
import com.minelittlepony.bakersd.util.event.ModelLoadedCallback;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BakersBlocks.RYE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BakersBlocks.CORN, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(BakersBlockEntities.BREAD_BOARD, BreadBlockEntityRenderer::new);
        ModelLoadedCallback.EVENT.register(new BreadModelLoader());
    }
}
