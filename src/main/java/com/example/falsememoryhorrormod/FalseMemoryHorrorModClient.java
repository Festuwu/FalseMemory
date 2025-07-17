package com.example.falsememoryhorrormod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FalseMemoryHorrorModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // Initialize sky effects manager
        SkyEffectsManager.initialize();
        
        // Register client tick handler
        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickHandler());
        
        FalseMemoryHorrorMod.LOGGER.info("Client-side horror effects initialized");
    }
}