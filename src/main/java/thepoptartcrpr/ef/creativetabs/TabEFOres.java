package thepoptartcrpr.ef.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thepoptartcrpr.ef.init.EFBlocks;

public class TabEFOres extends CreativeTabs {
	
	public TabEFOres() {
		super("efores");
	}
	
	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(EFBlocks.saltOre);
	}

}
