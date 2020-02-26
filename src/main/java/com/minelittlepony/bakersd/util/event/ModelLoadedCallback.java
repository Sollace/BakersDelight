package com.minelittlepony.bakersd.util.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;

public interface ModelLoadedCallback {

    Event<ModelLoadedCallback> EVENT = EventFactory.createArrayBacked(ModelLoadedCallback.class, listeners -> (loader, modelId) -> {
        for (ModelLoadedCallback event : listeners) {
            event.onModelQueued(loader, modelId);
        }
    });

    void onModelQueued(CustomModelLoader loader, ModelIdentifier modelId);

    public interface CustomModelLoader {

        ModelLoader getVanillaLoader();

        ResourceManager getResourceManager();

        void emitUnbakedModel(ModelIdentifier modelId, UnbakedModel model);
    }
}
