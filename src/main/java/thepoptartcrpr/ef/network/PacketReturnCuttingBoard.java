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

public class PacketReturnCuttingBoard implements IMessage {
	
	private boolean messageValid;
	
	private int cutTime;
	private int totalCutTime;
	private String className;
	private String cutTimeFieldName;
	private String totalCutTimeFieldName;
	
	public PacketReturnCuttingBoard() {
		this.messageValid = false;
	}
	
	public PacketReturnCuttingBoard(int cutTime, int totalCutTime, String className, String cutTimeFieldName, String totalCutTimeFieldName) {
		this.cutTime = cutTime;
		this.totalCutTime = totalCutTime;
		this.className = className;
		this.cutTimeFieldName = cutTimeFieldName;
		this.totalCutTimeFieldName = totalCutTimeFieldName;
		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.cutTime = buf.readInt();
			this.totalCutTime = buf.readInt();
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
		buf.writeInt(this.cutTime);
		buf.writeInt(this.totalCutTime);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cutTimeFieldName);
		ByteBufUtils.writeUTF8String(buf, this.totalCutTimeFieldName);
	}

	public static class Handler implements IMessageHandler<PacketReturnCuttingBoard, IMessage> {

		@Override
		public IMessage onMessage(PacketReturnCuttingBoard message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		void processMessage(PacketReturnCuttingBoard message) {
			try {
				Class clazz = Class.forName(message.className);
				Field cookTimeField = clazz.getDeclaredField(message.cutTimeFieldName);
				Field totalCookTimeField = clazz.getDeclaredField(message.totalCutTimeFieldName);
				cookTimeField.setInt(clazz, message.cutTime);
				totalCookTimeField.setInt(clazz, message.totalCutTime);
			} catch (Exception exception) {
				Utils.getConsole().catching(exception);
			}
		}

	}

}
