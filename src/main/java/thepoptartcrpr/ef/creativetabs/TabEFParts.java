package thepoptartcrpr.ef.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;

public class TabEFParts extends CreativeTabs {
	
	public TabEFParts() {
		super("efparts");
	}
	
	@Override
	public Item getTabIconItem() {
		return EFItems.sawHead;
	}

}
