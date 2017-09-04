package thepoptartcrpr.ef.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thepoptartcrpr.ef.Variables;

public class EFGenericTool extends Item {
	
	private static int maxDamage = 100;
	
	public EFGenericTool(String unlocalizedName, String registryName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, registryName));
		this.setMaxStackSize(1);
		this.setContainerItem(this);
		this.setMaxDamage(100);
		this.setNoRepair();
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		int dmg = stack.getItemDamage();
		if (dmg == maxDamage) {
			return null;
			// return new ItemStack(stack.getItem(), 0, maxDamage);
		}
		ItemStack tr = stack.copy();
		tr.setItemDamage(dmg + 1);
		return tr;
	}

}
