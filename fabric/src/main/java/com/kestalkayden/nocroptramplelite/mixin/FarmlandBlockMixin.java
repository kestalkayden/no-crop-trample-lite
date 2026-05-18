package com.kestalkayden.nocroptramplelite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.kestalkayden.nocroptramplelite.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Wraps the static turnToDirt(...) invocation inside FarmlandBlock.fallOn so
 *  we can skip it without disrupting the surrounding fall-damage path (the
 *  super.fallOn call still runs). MixinExtras' @WrapOperation is bundled with
 *  Fabric Loader so this needs no extra runtime dep. */
@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {

    @WrapOperation(
        method = "fallOn",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/FarmlandBlock;turnToDirt(" +
                     "Lnet/minecraft/world/entity/Entity;" +
                     "Lnet/minecraft/world/level/block/state/BlockState;" +
                     "Lnet/minecraft/world/level/Level;" +
                     "Lnet/minecraft/core/BlockPos;)V"))
    private void nocroptramplelite$skipTurnToDirt(
            Entity entity, BlockState state, Level level, BlockPos pos,
            Operation<Void> original) {
        if (!ModConfig.get().shouldPrevent(entity)) {
            original.call(entity, state, level, pos);
        }
    }
}
