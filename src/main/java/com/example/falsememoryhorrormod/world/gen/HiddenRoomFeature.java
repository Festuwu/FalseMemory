package com.example.falsememoryhorrormod.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class HiddenRoomFeature extends Feature<DefaultFeatureConfig> {

    public HiddenRoomFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = (Random) context.getRandom();

        if (origin.getY() > 40) {
            return false;
        }

        // Create a 3x3x3 room of cobblestone
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    world.setBlockState(origin.add(x, y, z), Blocks.COBBLESTONE.getDefaultState(), 2);
                }
            }
        }

        // Hollow out the room
        for (int x = 0; x <= 0; x++) {
            for (int y = 0; y <= 0; y++) {
                for (int z = 0; z <= 0; z++) {
                    world.setBlockState(origin.add(x, y, z), Blocks.AIR.getDefaultState(), 2);
                }
            }
        }
        
        // Add a torch
        world.setBlockState(origin, Blocks.TORCH.getDefaultState(), 2);

        return true;
    }
} 