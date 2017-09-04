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

public class PacketReturnWorker implements IMessage {
	
	private boolean messageValid;
	
	private int cooldown;
	private int maxCooldown;
	
	private String className;
	private String cooldownFieldName;
	private String maxCooldownFieldName;
	
	public PacketReturnWorker() {
		this.messageValid = false;
	}
	
	public PacketReturnWorker(int cooldown, int maxCooldown, String className, String cooldownFieldName, String maxCooldownFieldName) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.className = className;
		this.cooldownFieldName = cooldownFieldName;
		this.maxCooldownFieldName = maxCooldownFieldName;
		this.messageValid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.cooldown = buf.readInt();
			this.maxCooldown = buf.readInt();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.cooldownFieldName = ByteBufUtils.readUTF8String(buf);
			this.maxCooldownFieldName = ByteBufUtils.readUTF8String(buf);
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
		buf.writeInt(this.cooldown);
		buf.writeInt(this.maxCooldown);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cooldownFieldName);
		ByteBufUtils.writeUTF8String(buf, this.maxCooldownFieldName);
	}
	
	/**
	 * The message handler for the {@link PacketReturnWorker} message
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static class Handler implements IMessageHandler<PacketReturnWorker, IMessage> {

		/**
		 * Schedules the task on the client which will process the message
		 */
		@Override
		public IMessage onMessage(PacketReturnWorker message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		/**
		 * Update the fields in the class provided using the data from the
		 * message
		 * 
		 * @param message
		 *            The message which holds the data
		 */
		void processMessage(PacketReturnWorker message) {
			try {
				Class clazz = Class.forName(message.className);
				Field cooldownField = clazz.getDeclaredField(message.cooldownFieldName);
				Field maxCooldownField = clazz.getDeclaredField(message.maxCooldownFieldName);
				cooldownField.setInt(clazz, message.cooldown);
				maxCooldownField.setInt(clazz, message.maxCooldown);
			} catch (Exception exception) {
				Utils.getConsole().catching(exception);
			}
		}

}

}
