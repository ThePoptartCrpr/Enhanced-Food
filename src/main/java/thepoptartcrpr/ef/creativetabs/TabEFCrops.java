package thepoptartcrpr.ef.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thepoptartcrpr.ef.init.EFItems;

public class TabEFCrops extends CreativeTabs {

	public TabEFCrops() {
		super("efcrops");
	}
	
	@Override
	public Item getTabIconItem() {
		return EFItems.corn;
	}

}
