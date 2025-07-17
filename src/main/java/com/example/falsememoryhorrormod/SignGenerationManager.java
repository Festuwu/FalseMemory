package com.example.falsememoryhorrormod;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SignGenerationManager {
    
    private static final Random RANDOM = Random.create();
    private static final String[] SIGN_MESSAGES = {
        "was here",
        "found this place",
        "don't follow me",
        "I was never here",
        "remember me",
        "you shouldn't be here",
        "turn back now",
        "I'm watching you"
    };
    
    public static void tick(World world, PlayerEntity player) {
        if (world.isClient || player == null) return;
        
        // 1 in 20000 chance per tick (very rare)
        if (RANDOM.nextInt(20000) == 0) {
            generatePlayerSign(world, player);
        }
    }
    
    private static void generatePlayerSign(World world, PlayerEntity player) {
        // Find a suitable location nearby
        BlockPos playerPos = player.getBlockPos();
        
        for (int attempts = 0; attempts < 10; attempts++) {
            int x = playerPos.getX() + RANDOM.nextInt(21) - 10;
            int y = playerPos.getY() + RANDOM.nextInt(11) - 5;
            int z = playerPos.getZ() + RANDOM.nextInt(21) - 10;
            
            BlockPos signPos = new BlockPos(x, y, z);
            BlockPos groundPos = signPos.down();
            
            // Check if location is suitable
            if (world.getBlockState(signPos).isAir() && 
                world.getBlockState(groundPos).isSolidBlock(world, groundPos)) {
                
                // Place the sign
                world.setBlockState(signPos, Blocks.OAK_SIGN.getDefaultState(), 3);
                
                // Set sign text
                SignBlockEntity signEntity = (SignBlockEntity) world.getBlockEntity(signPos);
                if (signEntity != null) {
                    String message = SIGN_MESSAGES[RANDOM.nextInt(SIGN_MESSAGES.length)];
                    signEntity.changeText(signText -> {
                        return signText.withMessage(0, Text.of(player.getName().getString()))
                                      .withMessage(1, Text.of(message));
                    }, true);
                }
                
                FalseMemoryHorrorMod.LOGGER.info("Generated sign with player name at: " + signPos);
                break;
            }
        }
    }
}