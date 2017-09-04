package thepoptartcrpr.ef.items;

import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import thepoptartcrpr.ef.Variables;

public class EFSword extends ItemSword {
	
	public EFSword(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, unlocalizedName));
	}

}
