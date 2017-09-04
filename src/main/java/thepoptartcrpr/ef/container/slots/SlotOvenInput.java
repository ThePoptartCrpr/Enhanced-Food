package thepoptartcrpr.ef.container.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thepoptartcrpr.ef.blocks.EFFluid;

public class SlotOvenInput extends SlotItemHandler {

	public SlotOvenInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		// return super.isItemValid(stack) && stack.getItem() == Items.ARROW;
		return super.isItemValid(stack) && !ItemStack.areItemStacksEqual(stack, UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, EFFluid.fuel));
	}

}
