package com.example.falsememoryhorrormod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

import java.util.Random;

public class SkyEffectsManager {
    
    private static final Random RANDOM = new Random();
    private static float celestialAngleOffset = 0.0f;
    private static boolean shouldStutter = false;
    private static int stutterTicks = 0;
    private static float stutterDirection = 0.0f;
    private static float originalSkyAngle = 0.0f;
    private static boolean isEffectActive = false;
    
    public static void initialize() {
        // Register world tick event for calculations
        ClientTickEvents.START_WORLD_TICK.register(SkyEffectsManager::onWorldTick);
        
        // Register world render event for sky effects
        WorldRenderEvents.START.register(SkyEffectsManager::onWorldRenderStart);
        
        FalseMemoryHorrorMod.LOGGER.info("Sky effects manager initialized");
    }
    
    private static void onWorldTick(ClientWorld world) {
        if (world == null) return;
        
        // Update time manipulation effects
        updateTimeEffects(world);
    }
    
    private static void onWorldRenderStart(WorldRenderContext context) {
        // Apply sky angle modifications during rendering
        if (isEffectActive) {
            ClientWorld world = context.world();
            if (world != null) {
                // Store original and apply modified angle
                float modifiedAngle = originalSkyAngle + celestialAngleOffset + stutterDirection;
                
                // Apply the modified sky angle using alternative methods
                try {
                    applySkyAngleModification(world, modifiedAngle);
                } catch (Exception e) {
                    FalseMemoryHorrorMod.LOGGER.warn("Failed to apply sky angle modification: " + e.getMessage());
                }
            }
        }
    }
    
    private static void updateTimeEffects(ClientWorld world) {
        // Store original sky angle
        originalSkyAngle = world.getSkyAngle(1.0f);
        
        // Original celestial angle offset effect
        if (RANDOM.nextInt(1000) == 0) {
            celestialAngleOffset = (RANDOM.nextFloat() - 0.5f) * 0.01f;
            isEffectActive = true;
        }
        
        if (celestialAngleOffset != 0.0f) {
            celestialAngleOffset *= 0.95f;
            if (Math.abs(celestialAngleOffset) < 0.0001f) {
                celestialAngleOffset = 0.0f;
                if (!shouldStutter) {
                    isEffectActive = false;
                }
            }
        }
        
        // Sun/Moon stutter effect
        if (!shouldStutter && RANDOM.nextInt(8000) == 0) {
            shouldStutter = true;
            stutterTicks = 10 + RANDOM.nextInt(30); // 0.5-2 seconds
            stutterDirection = (RANDOM.nextFloat() - 0.5f) * 0.05f; // Small backwards movement
            isEffectActive = true;
        }
        
        if (shouldStutter) {
            stutterTicks--;
            if (stutterTicks <= 0) {
                shouldStutter = false;
                stutterDirection = 0.0f;
                if (celestialAngleOffset == 0.0f) {
                    isEffectActive = false;
                }
            }
        }
        
        // Alternative: Directly modify world time for visual effects
        if (isEffectActive && RANDOM.nextInt(5000) == 0) {
            // Subtle time skip/stutter by modifying world time
            long currentTime = world.getTime();
            long timeOffset = (long) (stutterDirection * 100);
            
            // Note: This is a simplified approach - actual implementation
            // would need proper synchronization and validation
            FalseMemoryHorrorMod.LOGGER.debug("Time stutter effect: offset=" + timeOffset);
        }
    }
    
    private static void applySkyAngleModification(ClientWorld world, float modifiedAngle) {
        // This would require access widener or reflection to modify sky angle
        // For now, we'll use alternative approaches like rain/weather effects
        
        // Alternative: Create visual time distortion through weather
        if (Math.abs(modifiedAngle - originalSkyAngle) > 0.01f) {
            // Trigger brief weather changes to create visual distortion
            if (RANDOM.nextInt(100) == 0) {
                world.setRainGradient(0.3f);
                // This creates a brief, subtle sky darkening effect
            }
        }
    }
    
    public static float getCelestialAngleOffset() {
        return celestialAngleOffset;
    }
    
    public static boolean isStutterActive() {
        return shouldStutter;
    }
    
    public static float getStutterDirection() {
        return stutterDirection;
    }
}