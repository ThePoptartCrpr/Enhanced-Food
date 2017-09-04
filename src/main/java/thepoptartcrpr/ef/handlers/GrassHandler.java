package thepoptartcrpr.ef.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import thepoptartcrpr.ef.init.EFItems;

public class GrassHandler {
	
	public static void registerSeeds() {
		MinecraftForge.addGrassSeed(new ItemStack(EFItems.cornSeeds), 10);
	}

}
