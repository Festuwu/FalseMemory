package com.example.falsememoryhorrormod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import java.util.Random;

public class FakeNameplateRenderer {
    private static final Random RANDOM = new Random();
    private static String fakeName = null;
    private static Vec3d fakeNamePos = null;
    private static int fakeNameTicks = 0;

    public static void tick(MinecraftClient client) {
        if (fakeNameTicks > 0) {
            fakeNameTicks--;
            if (fakeNameTicks == 0) {
                fakeName = null;
                fakeNamePos = null;
            }
        } else if (RANDOM.nextInt(10000) == 0) {
            if (client.player != null) {
                fakeName = "Player" + (100 + RANDOM.nextInt(899));
                fakeNamePos = client.player.getPos().add(RANDOM.nextGaussian() * 10, RANDOM.nextGaussian() * 2, RANDOM.nextGaussian() * 10);
                fakeNameTicks = 60; // 3 seconds
                FalseMemoryHorrorMod.LOGGER.info("Spawning fake nameplate: " + fakeName + " at " + fakeNamePos);
            }
        }
    }

    public static void render(WorldRenderContext context) {
        if (fakeName == null || fakeNamePos == null || fakeNameTicks <= 0) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        double distance = client.player.getPos().distanceTo(fakeNamePos);
        if (distance > 50.0) return;

        MatrixStack matrices = context.matrixStack();
        matrices.push();

        Vec3d cam = context.camera().getPos();
        matrices.translate(fakeNamePos.x - cam.x, fakeNamePos.y - cam.y + 2.0, fakeNamePos.z - cam.z);

        matrices.multiply(client.getEntityRenderDispatcher().getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);

        Text nameText = Text.of(fakeName);
        float textWidth = client.textRenderer.getWidth(nameText);
        VertexConsumerProvider vertexConsumers = context.consumers();

        BlockPos pos = new BlockPos((int)fakeNamePos.x, (int)fakeNamePos.y, (int)fakeNamePos.z);
        int blockLight = context.world().getLightLevel(LightType.BLOCK, pos);
        int skyLight = context.world().getLightLevel(LightType.SKY, pos);
        int light = (skyLight << 20) | (blockLight << 4);

        client.textRenderer.draw(nameText, -textWidth / 2.0f, 0, 0xFFFFFF, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextLayerType.NORMAL, 0, light);

        matrices.pop();
    }
} 