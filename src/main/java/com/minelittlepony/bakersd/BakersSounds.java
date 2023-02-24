package com.minelittlepony.bakersd;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public interface BakersSounds {

    SoundEvent ROLLING_PIN_DONK = register("rolling_pin.donk");

    static SoundEvent register(String name) {
        Identifier id = new Identifier("bakersd", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    static void bootstrap() { }
}
