package thepoptartcrpr.ef.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thepoptartcrpr.ef.init.EFItems;

public class TabEFIngredient extends CreativeTabs {
	
	public TabEFIngredient() {
		super("efingredient");
	}
	
	@Override
	public Item getTabIconItem() {
		return EFItems.salt;
	}

}
