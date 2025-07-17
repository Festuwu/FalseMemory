package com.example.falsememoryhorrormod;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {

    public static final Identifier DISTANT_FOOTSTEPS_ID = new Identifier(FalseMemoryHorrorMod.MOD_ID, "distant_footsteps");
    public static SoundEvent DISTANT_FOOTSTEPS_EVENT = SoundEvent.of(DISTANT_FOOTSTEPS_ID);

    public static final Identifier ECHOED_MINING_ID = new Identifier(FalseMemoryHorrorMod.MOD_ID, "echoed_mining");
    public static SoundEvent ECHOED_MINING_EVENT = SoundEvent.of(ECHOED_MINING_ID);

    public static final Identifier QUIET_BLOCK_BREAK_ID = new Identifier(FalseMemoryHorrorMod.MOD_ID, "quiet_block_break");
    public static SoundEvent QUIET_BLOCK_BREAK_EVENT = SoundEvent.of(QUIET_BLOCK_BREAK_ID);

    public static final Identifier SOFT_WHISPER_ID = new Identifier(FalseMemoryHorrorMod.MOD_ID, "soft_whisper");
    public static SoundEvent SOFT_WHISPER_EVENT = SoundEvent.of(SOFT_WHISPER_ID);

    public static final Identifier TYPING_SOUND_ID = new Identifier(FalseMemoryHorrorMod.MOD_ID, "typing_sound");
    public static SoundEvent TYPING_SOUND_EVENT = SoundEvent.of(TYPING_SOUND_ID);


    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, DISTANT_FOOTSTEPS_ID, DISTANT_FOOTSTEPS_EVENT);
        Registry.register(Registries.SOUND_EVENT, ECHOED_MINING_ID, ECHOED_MINING_EVENT);
        Registry.register(Registries.SOUND_EVENT, QUIET_BLOCK_BREAK_ID, QUIET_BLOCK_BREAK_EVENT);
        Registry.register(Registries.SOUND_EVENT, SOFT_WHISPER_ID, SOFT_WHISPER_EVENT);
        Registry.register(Registries.SOUND_EVENT, TYPING_SOUND_ID, TYPING_SOUND_EVENT);
        
        FalseMemoryHorrorMod.LOGGER.info("Registered custom sounds.");
    }
} 