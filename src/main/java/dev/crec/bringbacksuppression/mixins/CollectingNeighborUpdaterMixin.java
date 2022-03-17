package dev.crec.bringbacksuppression.mixins;

import dev.crec.bringbacksuppression.UpdateSuppressException;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CollectingNeighborUpdater.class)
public class CollectingNeighborUpdaterMixin {
	@Redirect(method = "addAndRun", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getMaxChainedNeighborUpdates()I"))
	private int setMinimumUpdatesIfThresholdIsUnlimited(MinecraftServer instance) {
		int maxChainedNeighborUpdates = instance.getMaxChainedNeighborUpdates();
		return maxChainedNeighborUpdates == -1 ? 8000 : maxChainedNeighborUpdates;
	}

	@Inject(method = "addAndRun", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;)V", shift = At.Shift.BEFORE), remap = false)
	private void reintroduceUpdateSuppression(BlockPos blockPos, CollectingNeighborUpdater.NeighborUpdates neighborUpdates, CallbackInfo ci) {
		throw new UpdateSuppressException("Update suppressing", blockPos);
	}
}
