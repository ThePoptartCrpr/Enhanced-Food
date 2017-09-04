package thepoptartcrpr.ef.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.gui.GuiHandler;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.init.EFTools;
import thepoptartcrpr.ef.utils.Utils;

public class BlockTable extends Block {
	
	public BlockTable(String unlocalizedName, String registryName, Material material, float hardness, float resistance) {
		super(material);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, registryName));
		this.setHardness(hardness);
		this.setResistance(resistance);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && !playerIn.isSneaking()) {
			ItemStack knifeStack = new ItemStack(EFItems.cuttingBoard);
			ItemStack bowlStack = new ItemStack(EFItems.mixingBowl);
			if (ItemStack.areItemStacksEqual(heldItem, knifeStack)) {
				IBlockState newState = EFBlocks.tableCuttingBoard.getDefaultState();
				worldIn.setBlockState(pos, newState);
				ItemStack newStack = heldItem.copy();
				--newStack.stackSize;
				playerIn.setHeldItem(hand, newStack);
				if (playerIn.getHeldItem(hand).stackSize <= 0)
					playerIn.setHeldItem(hand, null);
			} else 
			if (ItemStack.areItemsEqual(heldItem, bowlStack)) {
				IBlockState newState = EFBlocks.tableMixingBowl.getDefaultState();
				worldIn.setBlockState(pos, newState);
				ItemStack newStack = heldItem.copy();
				--newStack.stackSize;
				playerIn.setHeldItem(hand, newStack);
				if (playerIn.getHeldItem(hand).stackSize <= 0)
					playerIn.setHeldItem(hand, null);
			}
		}
		return true;
	}
	
}
