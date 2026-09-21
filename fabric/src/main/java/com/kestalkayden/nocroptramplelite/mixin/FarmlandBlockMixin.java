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

/** Wraps the turnToBaseBlock(...) invocation inside FarmlandBlock.fallOn so
 *  we can skip it without disrupting the surrounding fall-damage path (the
 *  super.fallOn call still runs). MixinExtras' @WrapOperation is bundled with
 *  Fabric Loader so this needs no extra runtime dep.
 *
 *  <p>26.3 generalised farmland over a {@code baseBlock}: the static
 *  {@code turnToDirt(Entity, BlockState, Level, BlockPos)} became the instance
 *  method {@code turnToBaseBlock(...)} with the same parameters, so the wrapped
 *  call is now virtual and the handler receives the block instance first. */
@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {

    @WrapOperation(
        method = "fallOn",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/FarmlandBlock;turnToBaseBlock(" +
                     "Lnet/minecraft/world/entity/Entity;" +
                     "Lnet/minecraft/world/level/block/state/BlockState;" +
                     "Lnet/minecraft/world/level/Level;" +
                     "Lnet/minecraft/core/BlockPos;)V"))
    private void nocroptramplelite$skipTurnToBaseBlock(
            FarmlandBlock self, Entity entity, BlockState state, Level level, BlockPos pos,
            Operation<Void> original) {
        if (!ModConfig.get().shouldPrevent(entity)) {
            original.call(self, entity, state, level, pos);
        }
    }
}
