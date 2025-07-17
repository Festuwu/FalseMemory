package com.example.falsememoryhorrormod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {

    @Inject(method = "onStateReplaced", at = @At("HEAD"))
    private void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                world.playSound(null, pos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
            }, 1, TimeUnit.SECONDS);
        }
    }
} 