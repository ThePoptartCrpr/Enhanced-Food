package thepoptartcrpr.ef.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart.Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.gui.GuiHandler;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.tileentity.TileEntityOven;
import thepoptartcrpr.ef.utils.Utils;

public class BlockOven extends BlockMachine implements ITileEntityProvider {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	private static final EnumFacing[] VALID_FACING = { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
	
	public BlockOven(String unlocalizedName) {
		super(unlocalizedName);
		// this.setUnlocalizedName(unlocalizedName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.valueOf(false)));
		// this.setRegistryName(new ResourceLocation(Variables.MODID, unlocalizedName));
		// this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	// @Override
	// public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		// TileEntityOven te = (TileEntityOven) world.getTileEntity(pos);
		// ItemStackHandler handler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		// return Utils.calculateRedstone(handler);
	// }
	
	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		int meta = EnumFacing.values().length + facing.ordinal();
		return meta;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		// Utils.log("Block placed");
		// Utils.getConsole().info(BlockPistonBase.getFacingFromEntity(pos, placer));
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, Boolean.valueOf(false));
	}
	
	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos) {
		return VALID_FACING;
	}
	
	// @Override
	// public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
		// 	float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
	// }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.values()[meta % EnumFacing.values().length];
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return super.createTileEntity(worldIn, getStateFromMeta(meta));
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityOven();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityOven te = (TileEntityOven) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			if (stack != null)
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && !playerIn.isSneaking()) {
			playerIn.openGui(EnhancedFood.instance, GuiHandler.OVEN, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public static void setState(EnumFacing facing, boolean active, World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		// EnumFacing currentFacing = getStateFromMeta(5);
		
		worldIn.setBlockState(pos, EFBlocks.oven.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ACTIVE, active));
		
		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING,ACTIVE});
	}

}
