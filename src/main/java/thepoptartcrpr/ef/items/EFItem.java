package thepoptartcrpr.ef.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import thepoptartcrpr.ef.Variables;

public class EFItem extends Item {
	
	public EFItem(String unlocalizedName, String registryName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, registryName));
	}

}
