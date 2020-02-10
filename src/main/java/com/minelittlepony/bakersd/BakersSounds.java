package com.minelittlepony.bakersd;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface BakersSounds {

    SoundEvent ROLLING_PIN_DONK = register("rolling_pin.donk");

    static SoundEvent register(String name) {
        Identifier id = new Identifier("bakersd", name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    static void bootstrap() { }
}
