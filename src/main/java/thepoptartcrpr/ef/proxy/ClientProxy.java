package thepoptartcrpr.ef.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.compatibilities.waila.Waila;
import thepoptartcrpr.ef.gui.GuiHandler;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.init.EFTools;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		Waila.init();
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void registerRenders() {
		EFItems.registerRenders();
		EFBlocks.registerRenders();
		EFTools.registerRenders();
	}

}
