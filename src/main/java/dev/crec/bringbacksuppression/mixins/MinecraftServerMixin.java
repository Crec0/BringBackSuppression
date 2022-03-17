package dev.crec.bringbacksuppression.mixins;

import dev.crec.bringbacksuppression.UpdateSuppressException;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Shadow
	@Final
	private static Logger LOGGER;

	@Shadow
	protected abstract void waitUntilNextTick();

	@Redirect(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;waitUntilNextTick()V"))
	private void tickChildren(MinecraftServer instance) {
		try {
			this.waitUntilNextTick();
		} catch (UpdateSuppressException e) {
			LOGGER.error("Suppressing update: {}", e.getPos().toShortString());
		}
	}
}
