package dev.crec.bringbacksuppression.mixins;

import dev.crec.bringbacksuppression.UpdateSuppressException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
	@Redirect(method = "blockUpdated", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;updateNeighborsAt(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"))
	private void fixUpdateSuppressionCrash(ServerLevel instance, BlockPos pos, Block block) {
		try {
			instance.updateNeighborsAt(pos, block);
		} catch (UpdateSuppressException e) {
			instance.getPlayers(p -> true).forEach(player -> player.sendMessage(new TextComponent("Suppressed at " + e.getPos().toShortString()), ChatType.CHAT,  Util.NIL_UUID));
			throw e;
		}
	}
}
