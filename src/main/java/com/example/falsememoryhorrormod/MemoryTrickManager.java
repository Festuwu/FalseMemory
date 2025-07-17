package com.example.falsememoryhorrormod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;

public class MemoryTrickManager {
    
    private static final Random RANDOM = Random.create();
    private static final Map<PlayerEntity, Integer> MEMORY_ITEM_TIMERS = new HashMap<>();
    private static final Map<PlayerEntity, ItemStack> MEMORY_ITEMS = new HashMap<>();
    private static final String[] MEMORY_ITEM_NAMES = {
        "Keep me safe", "Don't forget me", "Remember this", "Hold onto me", "I was here"
    };
    
    public static void tick(PlayerEntity player) {
        if (player == null || player.getWorld().isClient) return;
        
        Integer timer = MEMORY_ITEM_TIMERS.get(player);
        if (timer != null) {
            timer--;
            if (timer <= 0) {
                removeMemoryItem(player);
            } else {
                MEMORY_ITEM_TIMERS.put(player, timer);
            }
        } else if (RANDOM.nextInt(12000) == 0) { // Every 10 minutes on average
            addMemoryItem(player);
        }
    }
    
    private static void addMemoryItem(PlayerEntity player) {
        if (player.getInventory().getEmptySlot() == -1) return; // No empty slots
        
        ItemStack memoryItem = new ItemStack(Items.PAPER);
        String itemName = MEMORY_ITEM_NAMES[RANDOM.nextInt(MEMORY_ITEM_NAMES.length)];
        memoryItem.setCustomName(Text.literal(itemName).formatted(Formatting.ITALIC, Formatting.GRAY));
        
        player.getInventory().insertStack(memoryItem);
        MEMORY_ITEMS.put(player, memoryItem);
        MEMORY_ITEM_TIMERS.put(player, 3600 + RANDOM.nextInt(7200)); // 3-9 minutes
        
        FalseMemoryHorrorMod.LOGGER.info("Added memory item '" + itemName + "' to player inventory");
    }
    
    private static void removeMemoryItem(PlayerEntity player) {
        ItemStack memoryItem = MEMORY_ITEMS.get(player);
        if (memoryItem != null) {
            player.getInventory().removeStack(player.getInventory().getSlotWithStack(memoryItem));
            MEMORY_ITEMS.remove(player);
            MEMORY_ITEM_TIMERS.remove(player);
            FalseMemoryHorrorMod.LOGGER.info("Removed memory item from player inventory");
        }
    }
    
    public static void onPlayerDisconnect(PlayerEntity player) {
        MEMORY_ITEM_TIMERS.remove(player);
        MEMORY_ITEMS.remove(player);
    }
}