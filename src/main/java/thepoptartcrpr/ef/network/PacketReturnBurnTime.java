package thepoptartcrpr.ef.network;

import java.lang.reflect.Field;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import thepoptartcrpr.ef.utils.Utils;

public class PacketReturnBurnTime implements IMessage {
	
	private boolean messageValid;
	
	private int burnTime;
	private String className;
	private String burnTimeFieldName;
	
	public PacketReturnBurnTime() {
		this.messageValid = false;
	}
	
	public PacketReturnBurnTime(int burnTime, String className, String burnTimeFieldName) {
		this.burnTime = burnTime;
		this.className = className;
		this.burnTimeFieldName = burnTimeFieldName;
		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.burnTime = buf.readInt();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.burnTimeFieldName = ByteBufUtils.readUTF8String(buf);
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
		buf.writeInt(this.burnTime);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.burnTimeFieldName);
	}
	
	public static class Handler implements IMessageHandler<PacketReturnBurnTime, IMessage> {

		@Override
		public IMessage onMessage(PacketReturnBurnTime message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		void processMessage(PacketReturnBurnTime message) {
			try {
				Class clazz = Class.forName(message.className);
				Field burnTimeField = clazz.getDeclaredField(message.burnTimeFieldName);
				burnTimeField.setInt(clazz, message.burnTime);
			} catch (Exception exception) {
				Utils.getConsole().catching(exception);
			}
		}

	}

}
