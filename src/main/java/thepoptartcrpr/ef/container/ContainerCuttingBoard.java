package thepoptartcrpr.ef.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import thepoptartcrpr.ef.container.slots.SlotCuttingBoardInput;
import thepoptartcrpr.ef.container.slots.SlotCuttingBoardKnife;
import thepoptartcrpr.ef.container.slots.SlotCuttingBoardOutput;
import thepoptartcrpr.ef.tileentity.TileEntityCuttingBoard;

public class ContainerCuttingBoard extends Container {

	private TileEntityCuttingBoard te;
	private IItemHandler handler;
	
	public ContainerCuttingBoard(IInventory playerInv, TileEntityCuttingBoard te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotCuttingBoardKnife(handler, 0, 44, 35));
		this.addSlotToContainer(new SlotCuttingBoardInput(handler, 1, 8, 52));
		this.addSlotToContainer(new SlotCuttingBoardOutput(handler, 2, 116, 35));
		
		int xPos = 8;
		int yPos = 84;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(this.te.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < this.handler.getSlots()) {
                if (!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
                    return null;
            } else {
                if (!this.mergeItemStack(current, 0, handler.getSlots(), false))
                    return null;
            }

            if (current.stackSize == 0) 
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
        }
        return previous;
    }

}
