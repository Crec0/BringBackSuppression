package dev.crec.bringbacksuppression;

import net.minecraft.core.BlockPos;

public final class UpdateSuppressException extends RuntimeException {
	private final BlockPos pos;

	public UpdateSuppressException(String s, BlockPos pos) {
		super(s);
		this.pos = pos;
	}

	public BlockPos getPos() {
		return pos;
	}
}
