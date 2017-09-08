package thepoptartcrpr.ef.compatibilities.jei.categories.oven;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.blocks.EFFluid;
import thepoptartcrpr.ef.compatibilities.jei.EFRecipeCategory;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.recipes.OvenRecipes;
import thepoptartcrpr.ef.utils.Utils;

public class OvenRecipeCategory extends EFRecipeCategory<OvenRecipes, OvenRecipeWrapper> {
	
	public static ResourceLocation backgroundImage = new ResourceLocation(Variables.MODID + ":textures/gui/container/oven.png");
	private final IDrawable fuelOverlay;
	
	public OvenRecipeCategory(IGuiHelper helper) {
		super("oven","tile.oven.name", helper.createDrawable(backgroundImage, 8, 13, 142, 60), OvenRecipes.class, new ItemStack(EFBlocks.oven));
		fuelOverlay = helper.createDrawable(backgroundImage, 176, 31, 16, 47, -2, 2, -2, 2);
	}
	
	@Override
	@Deprecated
	public void setRecipe(IRecipeLayout recipe, OvenRecipeWrapper wrapper) {
		
	}

	@Override
	public void setRecipe(IRecipeLayout recipe, OvenRecipeWrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipe.getItemStacks();
		guiItemStacks.init(0, false, 35, 21);
		guiItemStacks.init(1, true, 107, 21);
		guiItemStacks.init(2, false, 20, 20);
		guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
		guiItemStacks.set(1, ingredients.getOutputs(ItemStack.class));
		guiItemStacks.set(2, UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, EFFluid.fuel));
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(OvenRecipes recipes) {
		return new OvenRecipeWrapper(recipes);
	}

}
