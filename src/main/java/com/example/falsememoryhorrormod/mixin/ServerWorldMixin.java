package com.example.falsememoryhorrormod.mixin;

import com.example.falsememoryhorrormod.MobDisappearanceManager;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "spawnEntity(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void preventMobSpawnDuringDisappearance(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (MobDisappearanceManager.isDisappearanceActive() && MobDisappearanceManager.shouldHideEntity(entity)) {
            cir.setReturnValue(false);
        }
    }
} 