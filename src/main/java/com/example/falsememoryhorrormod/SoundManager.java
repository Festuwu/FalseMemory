package com.example.falsememoryhorrormod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class SoundManager {

    private static final Random RANDOM = new Random();

    public static void playDistantSound(World world, PlayerEntity player, SoundEvent sound) {
        if (world.isClient) {
            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            double distance = 16 + RANDOM.nextDouble() * 16; // 16-32 blocks away
            double x = player.getX() + Math.cos(angle) * distance;
            double y = player.getY() + (RANDOM.nextDouble() - 0.5) * 8;
            double z = player.getZ() + Math.sin(angle) * distance;
            
            BlockPos pos = new BlockPos((int)x, (int)y, (int)z);

            world.playSound(player, pos, sound, SoundCategory.AMBIENT, 0.7f, 0.8f + RANDOM.nextFloat() * 0.2f);
        }
    }

    public static void playWhisper(World world, PlayerEntity player) {
        if (world.isClient) {
            world.playSound(player, player.getBlockPos(), SoundRegistry.SOFT_WHISPER_EVENT, SoundCategory.AMBIENT, 0.4f, 1.0f);
        }
    }
    
    public static void playTypingSound(World world, PlayerEntity player) {
        if (world.isClient) {
            world.playSound(player, player.getBlockPos(), SoundRegistry.TYPING_SOUND_EVENT, SoundCategory.AMBIENT, 0.3f, 0.9f + RANDOM.nextFloat() * 0.2f);
        }
    }
} 