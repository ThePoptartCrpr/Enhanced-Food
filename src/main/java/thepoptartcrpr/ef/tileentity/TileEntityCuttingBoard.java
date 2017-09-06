package thepoptartcrpr.ef.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thepoptartcrpr.ef.blocks.machines.BlockTableCuttingBoard;
import thepoptartcrpr.ef.init.EFTools;
import thepoptartcrpr.ef.recipes.CuttingBoardRecipes;
import thepoptartcrpr.ef.recipes.OvenRecipes;
import thepoptartcrpr.ef.utils.Utils;

public class TileEntityCuttingBoard extends TileEntity implements ITickable, ICapabilityProvider {
	
	private ItemStackHandler handler;
	public int cutTime;
	public int totalCutTime;
	
	public TileEntityCuttingBoard() {
		this.handler = new ItemStackHandler(3);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.cutTime = nbt.getInteger("CutTime");
		this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
		super.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("CutTime", this.cutTime);
		nbt.setTag("ItemStackHandler", this.handler.serializeNBT());
		return super.writeToNBT(nbt);
	}

	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			BlockTableCuttingBoard.setKnife(this.hasKnife(), this.worldObj, pos);
			
			if (this.canCut()) {
				ItemStack inputStack = handler.getStackInSlot(1);
				if (this.hasKnife()) {
					totalCutTime = CuttingBoardRecipes.getCuttingTimeKnife(inputStack);
					cutTime++;
					if (cutTime >= totalCutTime) {
						this.cutItem();
						cutTime = 0;
					}
				} else {
					if (CuttingBoardRecipes.getCuttingTimeNoKnife(inputStack) != 0) {
						totalCutTime = CuttingBoardRecipes.getCuttingTimeNoKnife(inputStack);
						cutTime++;
						if (cutTime >= totalCutTime) {
							this.cutItem();
							cutTime = 0;
						}
					}
				}
			} else {
				this.cutTime = 0;
			}
		}
	}
	
	private boolean canCut() {
		if (handler.getStackInSlot(1) == null) {
			return false;
		} else {
			ItemStack itemstack = CuttingBoardRecipes.getCuttingResult(handler.getStackInSlot(1));
			if (itemstack == null) return false;
			if (handler.getStackInSlot(2) == null) return true;
			if (!handler.getStackInSlot(2).isItemEqual(itemstack)) return false;
			int result = handler.getStackInSlot(2).stackSize + itemstack.stackSize;
			return result <= this.getInventoryStackLimit() && result <= handler.getStackInSlot(2).getMaxStackSize();
		}
	}
	
	public void cutItem() {
		if (this.canCut()) {
			ItemStack outputStack = CuttingBoardRecipes.getCuttingResult(handler.getStackInSlot(1));
			
			if (handler.getStackInSlot(2) == null) {
				handler.setStackInSlot(2, outputStack.copy());
			}
			else if (handler.getStackInSlot(2).getItem() == outputStack.getItem()) {
				handler.getStackInSlot(2).stackSize += outputStack.stackSize;
			}
			
			if (handler.getStackInSlot(1).stackSize <= 1) {
				handler.setStackInSlot(1, null);
			} else {
				--handler.getStackInSlot(1).stackSize;	
			}
		}
	}
	
	private boolean hasKnife() {
		return ItemStack.areItemsEqual(handler.getStackInSlot(0), new ItemStack(EFTools.stainlessSteelKnife));
	}
	
	public void setKnife(boolean knife) {
		ItemStack knifeStack = null;
		if (knife)
			knifeStack = new ItemStack(EFTools.stainlessSteelKnife);
		this.handler.setStackInSlot(0, knifeStack);
	}
	
	private int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

}
