package com.minelittlepony.bakersd.util.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;

/**
 * Event triggered when a vanilla item/block model is queued to be loaded into the game.
 * <p>
 * Mods may listen to this event to be notified of when a model is queried,
 * and optionally add their own models to be loaded in addition to the one passed in.
 * <p>
 *
 */
public interface ModelLoadedCallback {

    Event<ModelLoadedCallback> EVENT = EventFactory.createArrayBacked(ModelLoadedCallback.class, listeners -> (loader, modelId) -> {
        for (ModelLoadedCallback event : listeners) {
            event.onModelQueued(loader, modelId);
        }
    });

    /**
     * Called when a vanilla item or block model is queued to be loaded.
     *
     * @param loader The custom model loader.
     * @param modelId ID of the model to be loaded.
     */
    void onModelQueued(CustomModelLoader loader, ModelIdentifier modelId);

    public interface CustomModelLoader {

        /**
         * Returns the vanilla ModelLoader instance. This can be used to load custom models if needed.
         */
        ModelLoader getVanillaLoader();

        /**
         * Emits an unbaked model back into the ModelLoader to make it visible to the rest of the game.
         *
         * @param modelId The model id to reference this model by.
         * @param model The unbaked model.
         */
        void emitUnbakedModel(ModelIdentifier modelId, UnbakedModel model);
    }
}
