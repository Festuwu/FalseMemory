package com.example.falsememoryhorrormod.mixin;

import com.example.falsememoryhorrormod.FalseMemoryHorrorMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    private static final Random RANDOM = new Random();
    private static String fakeName = null;
    private static Vec3d fakeNamePos = null;
    private static int fakeNameTicks = 0;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (RANDOM.nextInt(10000) == 0 && fakeName == null) {
            fakeName = "Player" + (100 + RANDOM.nextInt(899));
            fakeNamePos = entity.getPos().add(new Vec3d(RANDOM.nextGaussian() * 10, RANDOM.nextGaussian() * 2, RANDOM.nextGaussian() * 10));
            fakeNameTicks = 10 + RANDOM.nextInt(20);
            FalseMemoryHorrorMod.LOGGER.info("Spawning fake nameplate: " + fakeName);
        }

        if (fakeName != null && fakeNamePos != null && fakeNameTicks > 0) {
            fakeNameTicks--;
            if (fakeNameTicks == 0) {
                fakeName = null;
                fakeNamePos = null;
            } else {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null) {
                    Vec3d playerPos = client.player.getPos();
                    double distance = playerPos.distanceTo(fakeNamePos);
                    if (distance < 50.0) {
                        matrices.push();
                        matrices.translate(
                            fakeNamePos.x - playerPos.x,
                            fakeNamePos.y - playerPos.y + 2.0,
                            fakeNamePos.z - playerPos.z
                        );
                        matrices.multiply(client.getEntityRenderDispatcher().getRotation());
                        matrices.scale(-0.025F, -0.025F, 0.025F);
                        
                        Text nameText = Text.of(fakeName);
                        float textWidth = client.textRenderer.getWidth(nameText);
                        client.textRenderer.draw(nameText, -textWidth / 2.0f, 0, 0xFFFFFF, false, matrices.peek().getPositionMatrix(), vertexConsumers, net.minecraft.client.font.TextRenderer.TextLayerType.NORMAL, 0, light);
                        
                        matrices.pop();
                    }
                }
            }
        }
    }
} 