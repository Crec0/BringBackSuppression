package dev.crec.bringbacksuppression.mixins;

import dev.crec.bringbacksuppression.UpdateSuppressException;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PacketUtils.class)
public class PacketUtilsMixin {
	@Inject(
		method = "method_11072(Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/protocol/Packet;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/network/PacketListener;shouldPropagateHandlingExceptions()Z",
			shift = At.Shift.BEFORE
		),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void shouldPropagateHandlingExceptions(PacketListener packetListener, Packet<?> packet, CallbackInfo ci, Exception exception) {
		if (exception instanceof UpdateSuppressException updateSuppressException) {
			throw updateSuppressException;
		}
	}
}
