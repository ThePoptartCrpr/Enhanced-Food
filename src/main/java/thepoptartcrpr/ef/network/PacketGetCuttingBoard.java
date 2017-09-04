package thepoptartcrpr.ef.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import thepoptartcrpr.ef.tileentity.TileEntityCuttingBoard;
import thepoptartcrpr.ef.tileentity.TileEntityOven;
import thepoptartcrpr.ef.utils.NetworkUtils;
import thepoptartcrpr.ef.utils.Utils;

public class PacketGetCuttingBoard implements IMessage {
	
	private boolean messageValid;
	
	private BlockPos pos;
	private String className;
	private String cutTimeFieldName;
	private String totalCutTimeFieldName;
	
	public PacketGetCuttingBoard() {
		this.messageValid = false;
	}
	
	public PacketGetCuttingBoard(BlockPos pos, String className, String cutTimeFieldName, String totalCutTimeFieldName) {
		this.pos = pos;
		this.className = className;
		this.cutTimeFieldName = cutTimeFieldName;
		this.totalCutTimeFieldName = totalCutTimeFieldName;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.pos = NetworkUtils.readBlockPos(buf);
			this.className = ByteBufUtils.readUTF8String(buf);
			this.cutTimeFieldName = ByteBufUtils.readUTF8String(buf);
			this.totalCutTimeFieldName = ByteBufUtils.readUTF8String(buf);
		} catch (IndexOutOfBoundsException exception) {
			Utils.getConsole().catching(exception);
			return;
		}
		this.messageValid = true;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		NetworkUtils.writeBlockPos(buf, this.pos);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cutTimeFieldName);
		ByteBufUtils.writeUTF8String(buf, this.totalCutTimeFieldName);
	}

	public static class Handler implements IMessageHandler<PacketGetCuttingBoard, IMessage> {

		@Override
		public IMessage onMessage(PacketGetCuttingBoard message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		void processMessage(PacketGetCuttingBoard message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.pos);
			if (te == null)
				return;
			PacketHandler.INSTANCE.sendTo(new PacketReturnCookTime(((TileEntityCuttingBoard) te).cutTime, ((TileEntityCuttingBoard) te).totalCutTime,
					message.className, message.cutTimeFieldName, message.totalCutTimeFieldName), ctx.getServerHandler().playerEntity);
		}

	}

}
