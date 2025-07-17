package com.example.falsememoryhorrormod.mixin;

import com.example.falsememoryhorrormod.SoundManager;
import com.example.falsememoryhorrormod.SoundRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mixin(Block.class)
public class BlockMixin {

    private static final Random RANDOM = new Random();

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, net.minecraft.block.entity.BlockEntity blockEntity, net.minecraft.item.ItemStack tool, CallbackInfo ci) {
        if (world.isClient) {
            // Echoed mining sound
            if (RANDOM.nextInt(100) < 5) { // 5% chance
                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                    SoundManager.playDistantSound(world, player, SoundRegistry.ECHOED_MINING_EVENT);
                }, 2, TimeUnit.SECONDS);
            }

            // Quiet block break sound
            if (RANDOM.nextInt(100) < 2) { // 2% chance
                SoundManager.playDistantSound(world, player, SoundRegistry.QUIET_BLOCK_BREAK_EVENT);
            }
        }
    }
} 