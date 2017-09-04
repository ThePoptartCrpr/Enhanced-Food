package thepoptartcrpr.ef.gui;

import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import thepoptartcrpr.ef.container.ContainerCuttingBoard;
import thepoptartcrpr.ef.container.ContainerOven;
import thepoptartcrpr.ef.tileentity.TileEntityCuttingBoard;
import thepoptartcrpr.ef.tileentity.TileEntityOven;

public class GuiHandler implements IGuiHandler {
	
	public static final int OVEN = 0;
	public static final int CUTTING_BOARD = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case OVEN:
			return new ContainerOven(player.inventory, (TileEntityOven) world.getTileEntity(pos));
		case CUTTING_BOARD:
			return new ContainerCuttingBoard(player.inventory, (TileEntityCuttingBoard) world.getTileEntity(pos));
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case OVEN:
			return new GuiOven(player.inventory, (TileEntityOven) world.getTileEntity(pos));
		case CUTTING_BOARD:
			return new GuiCuttingBoard(player.inventory, (TileEntityCuttingBoard) world.getTileEntity(pos));
		}
		return null;
	}

}
