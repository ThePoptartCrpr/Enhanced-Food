package thepoptartcrpr.ef.compatibilities.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import thepoptartcrpr.ef.blocks.crops.BlockCornCrop;
import thepoptartcrpr.ef.blocks.machines.BlockTable;
import thepoptartcrpr.ef.blocks.machines.BlockTableCuttingBoard;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.utils.Utils;

public class Waila implements IWailaDataProvider {
	
	private static Waila w = new Waila();
	
	public static void init() {
		FMLInterModComms.sendMessage("Waila", "register", "thepoptartcrpr.ef.compatibilities.waila.Waila.callbackRegister");
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound nbt, World world,
			BlockPos pos) {
		if (te != null) {
			te.deserializeNBT(nbt);
		}
		return nbt;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		if (!config.getConfig("general.showcrop") || accessor.getBlock() == null || !(accessor.getBlock() instanceof BlockCornCrop)) {
			return tip;
		}
		
		float mature = 7;
		final int growth = accessor.getMetadata();
		final float growthData = (growth / mature) * 100.0F;
		tip.clear();
		
		if (growthData < 100.0F) {
			tip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthData));
		} else {
			tip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.growth"), LangUtil.translateG("hud.msg.mature")));
		}
		return tip;
		
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return tip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getBlock() instanceof BlockCornCrop) {
			return new ItemStack(EFItems.corn);
		} else
		if (accessor.getBlock() instanceof BlockTableCuttingBoard) {
			return new ItemStack(EFBlocks.tableCuttingBoard);
		} else {
			return null;
		}
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> tip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return tip;
	}
	
	public static void callbackRegister(IWailaRegistrar registrar) {
		
		// Body providers
		registrar.registerBodyProvider(w, BlockCornCrop.class);
		registrar.registerStackProvider(w, BlockCornCrop.class);
		
		// Stack providers
		registrar.registerStackProvider(w, BlockTableCuttingBoard.class);
	}

}
