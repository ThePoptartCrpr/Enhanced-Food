package thepoptartcrpr.ef.utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class NetworkUtils {
	
	public static void writeBlockPos(ByteBuf buf, BlockPos pos) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
	
	public static BlockPos readBlockPos(ByteBuf buf) {
		return new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}
	
	public static void writeEnumFacing(ByteBuf buf, EnumFacing facing) {
		ByteBufUtils.writeUTF8String(buf, facing.getName2());
	}
	
	public static EnumFacing readEnumFacing(ByteBuf buf) {
		return EnumFacing.byName(ByteBufUtils.readUTF8String(buf));
	}

}
