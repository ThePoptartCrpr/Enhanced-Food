package thepoptartcrpr.ef.container;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thepoptartcrpr.ef.container.slots.SlotOvenFuel;
import thepoptartcrpr.ef.container.slots.SlotOvenInput;
import thepoptartcrpr.ef.container.slots.SlotOvenOutput;
import thepoptartcrpr.ef.tileentity.TileEntityOven;
import thepoptartcrpr.ef.utils.Utils;

public class ContainerOven extends Container {
	
	private TileEntityOven te;
	private IItemHandler handler;
	
	public ContainerOven(IInventory playerInv, TileEntityOven te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotOvenInput(handler, 0, 44, 35));
		this.addSlotToContainer(new SlotOvenFuel(handler, 1, 8, 52));
		this.addSlotToContainer(new SlotOvenOutput(handler, 2, 116, 35));
		
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
		
		// Utils.getConsole().info("handler.getSlots() " + handler.getSlots());
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
