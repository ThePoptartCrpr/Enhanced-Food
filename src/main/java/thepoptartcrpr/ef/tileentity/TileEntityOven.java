package thepoptartcrpr.ef.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thepoptartcrpr.ef.blocks.EFFluid;
import thepoptartcrpr.ef.blocks.machines.BlockOven;
import thepoptartcrpr.ef.capabilities.Worker;
import thepoptartcrpr.ef.handlers.AchievementHandler;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.recipes.OvenRecipes;
import thepoptartcrpr.ef.utils.Utils;

public class TileEntityOven extends TileEntity implements ITickable, ICapabilityProvider/*, IFluidTank, IFluidHandler*/ {
	
	private ItemStackHandler handler;
	// private IFluidHandler fluidHandler;
	private ItemStack[] ovenItemStacks = new ItemStack[3];
	private int cooldown;
	public int ovenBurnTime;
	public int currentItemBurnTime;
	public int cookTime;
	public int totalCookTime;
	// public FluidTank tank;
	
	public int maxCapacity;
	public int fluidSize;
	
	public TileEntityOven() {
		this.handler = new ItemStackHandler(3);
		this.maxCapacity = 2000;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		// this.cooldown = nbt.getInteger("Cooldown");
		this.maxCapacity = nbt.getInteger("MaxCapacity");
		this.fluidSize = nbt.getInteger("FluidSize");
		this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
		/*if (nbt.hasKey("FluidTank")) {
			// NBTTagCompound tankTag = nbt.getCompoundTag("FluidTank");
			// this.tank.readFromNBT(tankTag);
			// this.tank = new FluidTank(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("FluidTank")), this.maxCapacity);
		} else {
			// this.tank = new FluidTank(this.maxCapacity);
		}*/
		/*NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.ovenItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			
			if (j >= 0 && j < this.ovenItemStacks.length) {
				handler.setStackInSlot(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
			}
		}*/
		
		this.ovenBurnTime = nbt.getShort("BurnTime");
		this.cookTime = nbt.getShort("CookTime");
		this.totalCookTime = nbt.getShort("CookTimeTotal");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		// nbt.setInteger("Cooldown", this.cooldown);
		nbt.setInteger("MaxCapacity", this.maxCapacity);
		nbt.setInteger("FluidSize", this.fluidSize);
		nbt.setTag("ItemStackHandler", this.handler.serializeNBT());
		/*if (this.tank != null && this.tank.getFluid() != null) {
			// NBTTagCompound tankTag = new NBTTagCompound();
			// this.tank.writeToNBT(tankTag);
			// nbt.setTag("FluidTank", tankTag);
		}*/
		nbt.setShort("BurnTime", (short)this.ovenBurnTime);
		nbt.setShort("CookTime", (short)this.cookTime);
		nbt.setShort("CookTimeTotal", (short)this.totalCookTime);
		/*NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.ovenItemStacks.length; ++i) {
			if (handler.getStackInSlot(i) != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				handler.getStackInSlot(i).writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		nbt.setTag("Items", nbttaglist);*/
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void update() {
		/*if (this.worldObj != null && !this.worldObj.isRemote) {
			if (!this.worldObj.isAirBlock(pos) && this.worldObj.getBlockState(pos).getBlock() == EFBlocks.oven) {
				this.cooldown++;
				this.cooldown %= 100;
				// Utils.getConsole().info("Cooldown: " + this.cooldown);
			}
		}*/
		
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if (this.isBurning()) {
			--this.ovenBurnTime;
			--this.fluidSize;
		}
		
		if (!this.worldObj.isRemote) {
			if (ItemStack.areItemStacksEqual(handler.getStackInSlot(1), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, EFFluid.fuel))) {
				Utils.getConsole().info(this.getFill());
				if (this.canFill(250)) {
					handler.setStackInSlot(1, new ItemStack(Items.BUCKET));
					this.increaseFill(250);
					Utils.getConsole().info(this.getFill());
				}
			}
			
			if (this.isBurning() || handler.getStackInSlot(1) != null && handler.getStackInSlot(0) != null) {
				if (!this.isBurning() && this.canSmelt()) {
					this.currentItemBurnTime = this.ovenBurnTime = this.fluidSize;
					
					if (this.isBurning()) {
						flag1 = true;
						if (handler.getStackInSlot(1) != null) {
							// --handler.getStackInSlot(1).stackSize;
							
							// if (handler.getStackInSlot(1).stackSize == 0) {
								// handler.setStackInSlot(1, handler.getStackInSlot(1).getItem().getContainerItem(handler.getStackInSlot(1)));
							// }
						}
					}
				}
				
				if (this.isBurning() && this.canSmelt()) {
					++this.cookTime;
					
					if (this.cookTime >= this.totalCookTime) {
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime(handler.getStackInSlot(0));
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			}
			
			else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
			}
			
			if (flag != this.isBurning()) {
				flag1 = true;
			}
			
			IBlockState currentState = this.worldObj.getBlockState(pos);
			EnumFacing facing = (EnumFacing) currentState.getValue(BlockOven.FACING);
			BlockOven.setState(facing, this.isBurning(), worldObj, pos);
		}
		
		if (flag1) {
			this.markDirty();
		}
		
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
	
	// public boolean isUseableByPlayer(EntityPlayer player) {
		// return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	// }
	
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
	
	public ItemStack getStackInSlot(int index) {
		return handler.getStackInSlot(0);
	}
	
	public ItemStack decreaseStackSize(int index, int count) {
		if (handler.getStackInSlot(index) != null) {
			if (handler.getStackInSlot(index).stackSize <= count) {
				ItemStack itemstack1 = handler.getStackInSlot(index);
				handler.setStackInSlot(index, null);
				return itemstack1;
			}
			else {
				ItemStack itemstack = handler.getStackInSlot(index).splitStack(count);
				if (handler.getStackInSlot(index).stackSize == 0) {
					handler.setStackInSlot(index, null);
				}
				
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	public ItemStack removeStackFromSlot(int index) {
		Utils.getConsole().info("remove stack from slot");
		if (handler.getStackInSlot(index) != null) {
			ItemStack itemstack = handler.getStackInSlot(index);
			handler.setStackInSlot(index, null);
			return itemstack;
		} else {
			return null;
		}
	}
	
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(handler.getStackInSlot(index)) && ItemStack.areItemStackTagsEqual(stack, handler.getStackInSlot(index));
		handler.setStackInSlot(index, stack);
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		
		if (index == 0 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}
	
	private boolean canSmelt() {
		if (handler.getStackInSlot(0) == null) {
			return false;
		} else
		if (this.fluidSize <= 0) {
			return false;
		} else {
			ItemStack itemstack = OvenRecipes.getOvenResult(handler.getStackInSlot(0));
			if (itemstack == null) return false;
			if (handler.getStackInSlot(2) == null) return true;
			if (!handler.getStackInSlot(2).isItemEqual(itemstack)) return false;
			int result = handler.getStackInSlot(2).stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= handler.getStackInSlot(2).getMaxStackSize();
		}
	}
	
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = OvenRecipes.getOvenResult(handler.getStackInSlot(0));
			
			if (handler.getStackInSlot(2) == null) {
				handler.setStackInSlot(2, itemstack.copy());
			}
			else if (handler.getStackInSlot(2).getItem() == itemstack.getItem()) {
				handler.getStackInSlot(2).stackSize += itemstack.stackSize;
			}
			
			if (handler.getStackInSlot(0).stackSize <= 1) {
				handler.setStackInSlot(0, null);
			} else {
				--handler.getStackInSlot(0).stackSize;	
			}
		}
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public int getCookTime(ItemStack stack) {
		this.totalCookTime = 25;
		return 25;
	}
	
	public int getSizeInventory() {
		return this.ovenItemStacks.length;
	}
	
	public boolean isBurning() {
		if (handler.getStackInSlot(0) != null) {
			return this.canSmelt();
		} else {
			return false;
		}
	}
	
	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null) {
			return 0;
		} else {
			return 300;
		}
	}
	
	public void increaseFill(int amount) {
		this.fluidSize += amount;
	}
	
	public boolean canFill(int amount) {
		Utils.getConsole().info(this.maxCapacity);
		return this.fluidSize + amount <= this.maxCapacity;
	}
	
	public void useFluid(int amount) {
		this.fluidSize -= amount;
	}
	
	public int getFill() {
		return this.fluidSize;
	}

	/*@Override
	public IFluidTankProperties[] getTankProperties() {
		return null;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null) {
			return null;
		}
		return drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack getFluid() {
		return null;
	}

	@Override
	public int getFluidAmount() {
		return fluidSize;
	}

	@Override
	public int getCapacity() {
		return maxCapacity;
	}

	@Override
	public FluidTankInfo getInfo() {
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null) {
			return 0;
		}
		
		FluidStack resourceCopy = resource.copy();
		int totalUsed = 0;
		
		while (resourceCopy.amount > 0) {
			int used = tank.fill(resourceCopy, doFill);
			resourceCopy.amount -= used;
			if (used > 0) {
				this.markDirty();
			}
			
			totalUsed += used;
			this.fluidSize += used;
			
		}
		
		return totalUsed;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		
		FluidStack output = tank.drain(maxDrain, doDrain);
		this.markDirty();
		
		return output;
	}
	
	public void setCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}*/

}
