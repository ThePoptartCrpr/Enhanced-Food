package thepoptartcrpr.ef.blocks.machines;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.gui.GuiHandler;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.init.EFTools;
import thepoptartcrpr.ef.tileentity.TileEntityCuttingBoard;
import thepoptartcrpr.ef.tileentity.TileEntityOven;
import thepoptartcrpr.ef.utils.Utils;

public class BlockTableCuttingBoard extends Block implements ITileEntityProvider {
	
	public static final PropertyBool KNIFE = PropertyBool.create("knife");
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockTableCuttingBoard(String unlocalizedName, String registryName, Material material, float hardness, float resistance) {
		super(material);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, registryName));
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setDefaultState(this.blockState.getBaseState().withProperty(KNIFE, Boolean.valueOf(false)));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		boolean knife = state.getValue(KNIFE);
		int meta;
		if (!knife) {
			meta = 0;
		} else {
			meta = 1;
		}
		return meta;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean knife;
		if (meta == 0) {
			knife = false;
		} else {
			knife = true;
		}
		return this.getDefaultState().withProperty(KNIFE, knife);
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
	
	/*public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		boolean knife = state.getValue(KNIFE);
		if (!knife) {
			return Item.getItemFromBlock(EFBlocks.tableCuttingBoard); 
		} else {
			return Item.getItemFromBlock(EFBlocks.tableCuttingBoard) + EFTools.stainlessSteelKnife;
		}
	}*/
	
    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	boolean knife = state.getValue(KNIFE);
    	ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
    	drops.add(new ItemStack(EFBlocks.tableCuttingBoard));
    	if (knife) {
    		drops.add(new ItemStack(EFTools.stainlessSteelKnife));
    	}
    	return drops;
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {KNIFE});
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && !playerIn.isSneaking()) {
			ItemStack toolStack = new ItemStack(EFTools.stainlessSteelKnife);
			if (ItemStack.areItemStacksEqual(heldItem, toolStack)) {
				if (worldIn.getBlockState(pos) == this.getDefaultState()) {
				Utils.getConsole().info(ItemStack.areItemStacksEqual(heldItem, toolStack));
				// IBlockState newState = worldIn.getBlockState(pos).withProperty(KNIFE, true);
				// worldIn.setBlockState(pos, newState);
				TileEntity tileEntity = worldIn.getTileEntity(pos);
				((TileEntityCuttingBoard) tileEntity).setKnife(true);
				ItemStack newStack = heldItem.copy();
				--newStack.stackSize;
				playerIn.setHeldItem(hand, newStack);
				if (playerIn.getHeldItem(hand).stackSize <= 0)
					playerIn.setHeldItem(hand, null);
				}
			} else {
				playerIn.openGui(EnhancedFood.instance, GuiHandler.CUTTING_BOARD, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
	
	public static void setKnife(boolean knife, World world, BlockPos pos) {
		IBlockState iBlockState = world.getBlockState(pos);
		TileEntity tileEntity = world.getTileEntity(pos);
		
		world.setBlockState(pos, EFBlocks.tableCuttingBoard.getDefaultState().withProperty(KNIFE, knife));
		((TileEntityCuttingBoard) tileEntity).setKnife(knife);
		
		if (tileEntity != null) {
			tileEntity.validate();
			world.setTileEntity(pos, tileEntity);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return super.createTileEntity(worldIn, getStateFromMeta(meta));
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCuttingBoard();
	}

}
