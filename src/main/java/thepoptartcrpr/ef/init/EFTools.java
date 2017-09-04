package thepoptartcrpr.ef.init;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.items.EFSword;

public class EFTools {
	
	// Materials
	public static final ToolMaterial stainlessSteelKnifeMaterial = EnumHelper.addToolMaterial(Variables.MODID + ":stainlessSteelKnife", 2, 250, 2F, 4F, 12);
	
	// Knives
	public static ItemSword stainlessSteelKnife;
	
	public static void init() {
		stainlessSteelKnife = new EFSword(stainlessSteelKnifeMaterial, "stainless_steel_knife");
	}
	
	public static void register() {
		registerItem(stainlessSteelKnife);
	}
	
	public static void registerRenders() {
		registerRender(stainlessSteelKnife);
	}
	
	public static void registerItem(Item item) {
		item.setCreativeTab(EnhancedFood.tools);
		GameRegistry.register(item);
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Variables.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
	}

}
