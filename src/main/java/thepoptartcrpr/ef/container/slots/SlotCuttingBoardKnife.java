package thepoptartcrpr.ef.container.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thepoptartcrpr.ef.init.EFTools;

public class SlotCuttingBoardKnife extends SlotItemHandler {

	public SlotCuttingBoardKnife(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && ItemStack.areItemStacksEqual(stack, new ItemStack(EFTools.stainlessSteelKnife));
	}

}
