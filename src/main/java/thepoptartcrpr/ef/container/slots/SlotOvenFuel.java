package thepoptartcrpr.ef.container.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thepoptartcrpr.ef.blocks.EFFluid;

public class SlotOvenFuel extends SlotItemHandler {
	
	public SlotOvenFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && ItemStack.areItemStacksEqual(stack, UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, EFFluid.fuel));
	}

}
