package thepoptartcrpr.ef.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;

public class TabEFMachines extends CreativeTabs {
	
	public TabEFMachines() {
		super("efmachines");
	}
	
	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(EFBlocks.oven);
	}

}
