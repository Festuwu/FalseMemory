package com.example.falsememoryhorrormod;

import com.example.falsememoryhorrormod.SoundRegistry;
import com.example.falsememoryhorrormod.world.gen.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FalseMemoryHorrorMod implements ModInitializer {
    public static final String MOD_ID = "falsememory";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("The walls are watching...");
        SoundRegistry.registerSounds();
        ModFeatures.registerFeatures();
        MobDisappearanceManager.initialize();
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                MobDisappearanceManager.tick(world);
            }
        });
    }
} 
