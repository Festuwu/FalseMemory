package com.example.falsememoryhorrormod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public class FalseMemoryHorrorModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // Initialize sky effects manager
        SkyEffectsManager.initialize();
        
        // Register client tick handler
        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickHandler());
        
        WorldRenderEvents.AFTER_ENTITIES.register(FakeNameplateRenderer::render);

        FalseMemoryHorrorMod.LOGGER.info("Client-side horror effects initialized");
    }
}