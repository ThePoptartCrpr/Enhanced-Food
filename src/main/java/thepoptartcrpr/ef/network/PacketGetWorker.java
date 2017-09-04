package thepoptartcrpr.ef.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import thepoptartcrpr.ef.capabilities.IWork;
import thepoptartcrpr.ef.init.EFCapabilities;
import thepoptartcrpr.ef.utils.Utils;

public class PacketGetWorker implements IMessage {
	
	private boolean messageValid;
	
	private BlockPos pos;
	private EnumFacing side;
	
	private String className;
	private String cooldownFieldName;
	private String maxCooldownFieldName;
	
	public PacketGetWorker() {
		this.messageValid = false;
	}
	
	public PacketGetWorker(BlockPos pos, EnumFacing side, String className, String cooldownFieldName, String maxCooldownFieldName) {
		this.pos = pos;
		this.side = side;
		this.className = className;
		this.cooldownFieldName = cooldownFieldName;
		this.maxCooldownFieldName = maxCooldownFieldName;
		this.messageValid = true;
		Utils.getConsole().info("Packet get worker fired");
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
			this.side = EnumFacing.byName(ByteBufUtils.readUTF8String(buf));
			this.className = ByteBufUtils.readUTF8String(buf);
			this.cooldownFieldName = ByteBufUtils.readUTF8String(buf);
			this.maxCooldownFieldName = ByteBufUtils.readUTF8String(buf);
		} catch (IndexOutOfBoundsException exception) {
			// Utils.getConsole().catching(exception);
			return;
		}
		this.messageValid = true;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		ByteBufUtils.writeUTF8String(buf, this.side.getName2());
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cooldownFieldName);
		ByteBufUtils.writeUTF8String(buf, this.maxCooldownFieldName);
	}
	
	public static class Handler implements IMessageHandler<PacketGetWorker, IMessage> {
		@Override
		public IMessage onMessage(PacketGetWorker message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}
		
		void processMessage(PacketGetWorker message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.pos);
			if (te == null)
				return;
			if (!te.hasCapability(EFCapabilities.CAPABILITY_WORKER, message.side))
				return;
			IWork worker = te.getCapability(EFCapabilities.CAPABILITY_WORKER, message.side);
			PacketHandler.INSTANCE.sendTo(
					new PacketReturnWorker(worker.getWorkDone(), worker.getMaxWork(), message.className,
							message.cooldownFieldName, message.maxCooldownFieldName),
					// new PacketReturnWorker(TileEntityOven),
					ctx.getServerHandler().playerEntity);
		}

	}

}
