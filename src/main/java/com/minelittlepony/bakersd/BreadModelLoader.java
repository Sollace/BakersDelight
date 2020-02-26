package com.minelittlepony.bakersd;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import com.minelittlepony.bakersd.blockentity.BreadBlockEntity;
import com.minelittlepony.bakersd.util.event.ModelLoadedCallback;

public class BreadModelLoader implements ModelLoadedCallback {

    @Override
    public void onModelQueued(CustomModelLoader loader, ModelIdentifier id) {
        if (!"inventory".equals(id.getVariant())) {
            return;
        }

        for (int sliceCount = 1; sliceCount <= BreadBlockEntity.MAX_SLICES; sliceCount++) {
            Identifier breadId = new Identifier(id.getNamespace(), "bread/" + id.getPath() + (sliceCount == BreadBlockEntity.MAX_SLICES ? "" : "_slices_" + sliceCount));

            if (loader.getResourceManager().containsResource(new Identifier(breadId.getNamespace(), "models/" + breadId.getPath() + ".json"))) {
                loader.emitUnbakedModel(getModelId(id, sliceCount), loader.getVanillaLoader().getOrLoadModel(breadId));
            }
        }
    }

    public static ModelIdentifier getModelId(Identifier id, int sliceCount) {
        return new ModelIdentifier(id.getNamespace() + ":" + id.getPath() + (sliceCount == BreadBlockEntity.MAX_SLICES ? "" : "_slices_" + sliceCount), "bread");
    }

}
