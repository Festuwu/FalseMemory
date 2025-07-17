package com.example.falsememoryhorrormod.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class VillageRuinsFeature extends Feature<DefaultFeatureConfig> {

    public VillageRuinsFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = (Random) context.getRandom();

        // Only generate at reasonable heights
        if (origin.getY() < 60 || origin.getY() > 80) {
            return false;
        }

        // Generate a ruined house layout
        generateRuinedHouse(world, origin, random);

        return true;
    }

    private void generateRuinedHouse(StructureWorldAccess world, BlockPos origin, Random random) {
        // House dimensions: 7x5x7
        int width = 7;
        int height = 5;
        int depth = 7;

        // Foundation - partially destroyed
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                if (random.nextInt(3) != 0) { // 66% chance to place foundation block
                    world.setBlockState(origin.add(x, -1, z), Blocks.COBBLESTONE.getDefaultState(), 2);
                }
            }
        }

        // Walls - mostly destroyed
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    boolean isWall = (x == 0 || x == width - 1 || z == 0 || z == depth - 1);
                    boolean isCorner = (x == 0 || x == width - 1) && (z == 0 || z == depth - 1);
                    
                    if (isWall) {
                        if (isCorner) {
                            // Corners are more likely to remain
                            if (random.nextInt(4) != 0) {
                                world.setBlockState(origin.add(x, y, z), Blocks.COBBLESTONE.getDefaultState(), 2);
                            }
                        } else {
                            // Regular walls are mostly destroyed
                            if (random.nextInt(5) == 0) {
                                world.setBlockState(origin.add(x, y, z), Blocks.COBBLESTONE.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }

        // Add some scattered debris
        for (int i = 0; i < 8; i++) {
            int x = random.nextInt(width);
            int z = random.nextInt(depth);
            int y = random.nextInt(2);
            
            if (random.nextInt(3) == 0) {
                world.setBlockState(origin.add(x, y, z), Blocks.COBBLESTONE.getDefaultState(), 2);
            } else {
                world.setBlockState(origin.add(x, y, z), Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
            }
        }

        // Add a door frame (without door)
        int doorX = width / 2;
        world.setBlockState(origin.add(doorX, 0, 0), Blocks.AIR.getDefaultState(), 2);
        world.setBlockState(origin.add(doorX, 1, 0), Blocks.AIR.getDefaultState(), 2);
        
        // Add some windows (as gaps)
        if (random.nextBoolean()) {
            world.setBlockState(origin.add(0, 1, depth / 2), Blocks.AIR.getDefaultState(), 2);
        }
        if (random.nextBoolean()) {
            world.setBlockState(origin.add(width - 1, 1, depth / 2), Blocks.AIR.getDefaultState(), 2);
        }

        // Sometimes add a well nearby
        if (random.nextInt(4) == 0) {
            BlockPos wellPos = origin.add(width + 2, 0, depth / 2);
            world.setBlockState(wellPos, Blocks.COBBLESTONE.getDefaultState(), 2);
            world.setBlockState(wellPos.up(), Blocks.COBBLESTONE.getDefaultState(), 2);
            world.setBlockState(wellPos.up(2), Blocks.COBBLESTONE.getDefaultState(), 2);
            world.setBlockState(wellPos.up().add(1, 0, 0), Blocks.COBBLESTONE.getDefaultState(), 2);
            world.setBlockState(wellPos.up().add(-1, 0, 0), Blocks.COBBLESTONE.getDefaultState(), 2);
            world.setBlockState(wellPos.up().add(0, 0, 1), Blocks.COBBLESTONE.getDefaultState(), 2);
            world.setBlockState(wellPos.up().add(0, 0, -1), Blocks.COBBLESTONE.getDefaultState(), 2);
        }
    }
}