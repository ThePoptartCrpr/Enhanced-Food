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

public class PacketReturnCookTime implements IMessage {
	
	private boolean messageValid;
	
	private int cookTime;
	private int totalCookTime;
	private String className;
	private String cookTimeFieldName;
	private String totalCookTimeFieldName;
	
	public PacketReturnCookTime() {
		this.messageValid = false;
	}
	
	public PacketReturnCookTime(int cookTime, int totalCookTime, String className, String cookTimeFieldName, String totalCookTimeFieldName) {
		this.cookTime = cookTime;
		this.totalCookTime = totalCookTime;
		this.className = className;
		this.cookTimeFieldName = cookTimeFieldName;
		this.totalCookTimeFieldName = totalCookTimeFieldName;
		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.cookTime = buf.readInt();
			this.totalCookTime = buf.readInt();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.cookTimeFieldName = ByteBufUtils.readUTF8String(buf);
			this.totalCookTimeFieldName = ByteBufUtils.readUTF8String(buf);
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
		buf.writeInt(this.cookTime);
		buf.writeInt(this.totalCookTime);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cookTimeFieldName);
		ByteBufUtils.writeUTF8String(buf, this.totalCookTimeFieldName);
	}

	public static class Handler implements IMessageHandler<PacketReturnCookTime, IMessage> {

		@Override
		public IMessage onMessage(PacketReturnCookTime message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		void processMessage(PacketReturnCookTime message) {
			try {
				Class clazz = Class.forName(message.className);
				Field cookTimeField = clazz.getDeclaredField(message.cookTimeFieldName);
				Field totalCookTimeField = clazz.getDeclaredField(message.totalCookTimeFieldName);
				cookTimeField.setInt(clazz, message.cookTime);
				totalCookTimeField.setInt(clazz, message.totalCookTime);
			} catch (Exception exception) {
				Utils.getConsole().catching(exception);
			}
		}

	}

}