package com.example.falsememoryhorrormod.world.gen;

import com.example.falsememoryhorrormod.FalseMemoryHorrorMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ModFeatures {

    public static final Feature<DefaultFeatureConfig> HIDDEN_ROOM = new HiddenRoomFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> VILLAGE_RUINS = new VillageRuinsFeature(DefaultFeatureConfig.CODEC);

    public static void registerFeatures() {
        Registry.register(Registries.FEATURE, new Identifier(FalseMemoryHorrorMod.MOD_ID, "hidden_room"), HIDDEN_ROOM);
        Registry.register(Registries.FEATURE, new Identifier(FalseMemoryHorrorMod.MOD_ID, "village_ruins"), VILLAGE_RUINS);
        FalseMemoryHorrorMod.LOGGER.info("Registered features.");
    }
} 