package com.minelittlepony.bakersd.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minelittlepony.bakersd.util.event.ModelLoadedCallback;

import java.util.Map;

@Mixin(ModelLoader.class)
abstract class MixinModelLoader implements ModelLoadedCallback.CustomModelLoader {

    @Shadow @Final
    private Map<Identifier, UnbakedModel> modelsToBake;

    @Inject(method = "addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", at = @At("HEAD"))
    private void addModel(ModelIdentifier id, CallbackInfo info) {
        ModelLoadedCallback.EVENT.invoker().onModelQueued(this, id);
    }

    @Override
    public ModelLoader getVanillaLoader() {
        return (ModelLoader)(Object)this;
    }

    @Override
    public void emitUnbakedModel(ModelIdentifier modelId, UnbakedModel model) {
        putModel(modelId, model);
        modelsToBake.put(modelId, model);
    }

    @Shadow
    abstract void putModel(Identifier id, UnbakedModel unbakedModel);
}
