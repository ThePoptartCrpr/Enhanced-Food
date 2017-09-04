package thepoptartcrpr.ef.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thepoptartcrpr.ef.Variables;

public class Utils {
	
	private static Logger console;
	private static Lang lang;
	
	public static Logger getConsole() {
		if(console == null) {
			console = LogManager.getFormatterLogger(Variables.MODID);
		}
		return console;
	}
	
	public static Logger log(Message msg) {
		getConsole().info(msg);
		return null;
	}
	
	public static Logger log(String string) {
		getConsole().info(string);
		return null;
	}
	
	public static Lang getLang() {
		if (lang == null) {
			lang = new Lang(Variables.MODID);
		}
		return lang;
	}
	
	public static boolean areItemsEqual(ItemStack stackOne, ItemStack stackTwo) {
		stackOne.stackSize = 1;
		stackTwo.stackSize = 1;
		return ItemStack.areItemStacksEqual(stackOne, stackTwo);
	}
	
	// public static int calculateRedstone(ItemStackHandler handler) {
		// int i = 0;
		// float f = 0.0F;
		// for(int j = 0; j < handler.getSlots(); j++) {
			// ItemStack stack = handler.getStackInSlot(j);
			// if(!stack.isEmpty()) {
				// f += (float)stack.getCount() / (float)Math.min(handler.getSlotLimit(j), stack.getMaxStackSize());
				// i++;
			// }
		// }
		// f = f / (float) handler.getSlots();
		// return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
// }
	
	public static ItemStack addStackToInventory(IItemHandler handler, ItemStack stack, boolean simulate) {
		ItemStack remainder = stack;
		for(int slot = 0; slot < handler.getSlots(); slot++) {
			remainder = handler.insertItem(slot, stack, simulate);
			// if(remainder == ItemStack.EMPTY) break;
		}
		return remainder;
	}
	
	public static ItemStack addStackToInventory(IItemHandler handler, int maxSlot, ItemStack stack, boolean simulate) {
		ItemStack remainder = stack;
		for(int slot = 0; slot < maxSlot; slot++) {
			remainder = handler.insertItem(slot, stack, simulate);
			// if(remainder == ItemStack.EMPTY) break;
		}
		return remainder;
	}
	
	public static boolean isInventoryFull(IItemHandler handler) {
		int filledSlots = 0;
		for(int slot = 0; slot < handler.getSlots(); slot++) {
			// if(handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot)) filledSlots++;
		}
		return filledSlots == handler.getSlots();
	}
	
	public static boolean isInventoryFull(IItemHandler handler, int maxSlot) {
		int filledSlots = 0;
		for(int slot = 0; slot < maxSlot; slot++) {
			// if(handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot)) filledSlots++;
		}
		return filledSlots == maxSlot;
	}

}
