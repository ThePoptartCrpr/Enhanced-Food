package thepoptartcrpr.ef.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFTools;

public class TabEFTools extends CreativeTabs {
	
	public TabEFTools() {
		super("eftools");
	}
	
	@Override
	public Item getTabIconItem() {
		return EFTools.stainlessSteelKnife;
	}

}
