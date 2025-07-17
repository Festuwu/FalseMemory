package com.example.falsememoryhorrormod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class MobDisappearanceManager {
    
    private static final Random RANDOM = Random.create();
    private static boolean isActive = false;
    private static long disappearanceStartTime = 0;
    private static long nextDisappearanceTime = 0;
    private static final long DISAPPEARANCE_DURATION = 36000; // 30 minutes in ticks
    private static final long MIN_TRIGGER_TIME = 18000; // 15 minutes in ticks
    private static final long MAX_TRIGGER_TIME = 30000; // 25 minutes in ticks
    private static final List<Entity> hiddenEntities = new ArrayList<>();
    
    public static void initialize() {
        nextDisappearanceTime = MIN_TRIGGER_TIME + RANDOM.nextInt((int)(MAX_TRIGGER_TIME - MIN_TRIGGER_TIME));
        FalseMemoryHorrorMod.LOGGER.info("Next mob disappearance scheduled for: " + nextDisappearanceTime + " ticks");
    }
    
    public static void tick(World world) {
        if (world == null || world.isClient) return;
        
        long currentTime = world.getTime();
        
        if (!isActive) {
            // Check if it's time to start disappearance
            if (currentTime >= nextDisappearanceTime) {
                startDisappearance(world);
                isActive = true;
                disappearanceStartTime = currentTime;
                FalseMemoryHorrorMod.LOGGER.info("Mob disappearance started at tick: " + currentTime);
            }
        } else {
            // Check if disappearance should end
            if (currentTime >= disappearanceStartTime + DISAPPEARANCE_DURATION) {
                endDisappearance(world);
                isActive = false;
                // Schedule next disappearance
                nextDisappearanceTime = currentTime + MIN_TRIGGER_TIME + RANDOM.nextInt((int)(MAX_TRIGGER_TIME - MIN_TRIGGER_TIME));
                FalseMemoryHorrorMod.LOGGER.info("Mob disappearance ended. Next one scheduled for: " + nextDisappearanceTime + " ticks");
            }
        }
    }
    
    private static void startDisappearance(World world) {
        hiddenEntities.clear();
        FalseMemoryHorrorMod.LOGGER.info("Mob disappearance system activated - feature temporarily disabled for stability");
        // Feature disabled for now due to entity iteration complexity
    }
    
    private static void endDisappearance(World world) {
        for (Entity entity : hiddenEntities) {
            if (entity != null && entity.isAlive()) {
                entity.setInvisible(false);
                entity.setSilent(false);
                if (entity instanceof MobEntity mob) {
                    mob.setAiDisabled(false);
                }
            }
        }
        
        FalseMemoryHorrorMod.LOGGER.info("Restored " + hiddenEntities.size() + " entities");
        hiddenEntities.clear();
    }
    
    private static boolean shouldHideEntity(Entity entity) {
        return (entity instanceof MobEntity || entity instanceof PassiveEntity) && 
               !(entity instanceof PlayerEntity) &&
               entity.isAlive();
    }
    
    public static boolean isDisappearanceActive() {
        return isActive;
    }
    
    public static long getTimeUntilNextDisappearance(World world) {
        if (world == null) return -1;
        long currentTime = world.getTime();
        return Math.max(0, nextDisappearanceTime - currentTime);
    }
    
    public static long getDisappearanceTimeRemaining(World world) {
        if (!isActive || world == null) return -1;
        long currentTime = world.getTime();
        return Math.max(0, disappearanceStartTime + DISAPPEARANCE_DURATION - currentTime);
    }
}