package thepoptartcrpr.ef.items.tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.utils.Utils;

public class EFStainlessSteelKnife extends ItemSword {
	
	public EFStainlessSteelKnife(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, unlocalizedName));
	}
	
	/*@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add(TextFormatting.GRAY + Utils.getLang().localize("stainless_steel_knife.tooltip"));
	}*/

}
