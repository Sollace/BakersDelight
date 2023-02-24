package com.minelittlepony.bakersd;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import com.minelittlepony.bakersd.item.BreadItem;
import com.minelittlepony.bakersd.util.event.ModelLoadedCallback;

public class BreadModelLoader implements ModelLoadedCallback {

    @Override
    public void onModelQueued(CustomModelLoader loader, ModelIdentifier id) {
        if (!"inventory".equals(id.getVariant())) {
            return;
        }

        for (int sliceCount = 1; sliceCount <= BreadItem.MAX_SLICES; sliceCount++) {
            Identifier breadId = new Identifier(id.getNamespace(), "bread/" + id.getPath() + (sliceCount == BreadItem.MAX_SLICES ? "" : "_slices_" + sliceCount));
            int slice = sliceCount;
            MinecraftClient.getInstance().getResourceManager()
                .getResource(new Identifier(breadId.getNamespace(), "models/" + breadId.getPath() + ".json"))
                .ifPresent(resource -> {
                    loader.emitUnbakedModel(getModelId(id, slice), loader.getVanillaLoader().getOrLoadModel(breadId));
                });
        }
    }

    public static ModelIdentifier getModelId(Identifier id, int sliceCount) {
        return new ModelIdentifier(id.getNamespace(), id.getPath() + (sliceCount == BreadItem.MAX_SLICES ? "" : "_slices_" + sliceCount), "bread");
    }

}