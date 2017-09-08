package thepoptartcrpr.ef.compatibilities.jei.categories.cuttingboard;

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
import thepoptartcrpr.ef.init.EFTools;
import thepoptartcrpr.ef.recipes.CuttingBoardRecipes;
import thepoptartcrpr.ef.utils.Utils;

public class CuttingBoardRecipeCategory extends EFRecipeCategory<CuttingBoardRecipes, CuttingBoardRecipeWrapper> {
	
	public static ResourceLocation backgroundImage = new ResourceLocation(Variables.MODID + ":textures/gui/container/cutting_board.png");
	
	public CuttingBoardRecipeCategory(IGuiHelper helper) {
		super("table_cutting_board","tile.table_cutting_board.name", helper.createDrawable(backgroundImage, 8, 13, 142, 60), CuttingBoardRecipes.class, new ItemStack(EFBlocks.tableCuttingBoard));
	}
	
	@Override
	@Deprecated
	public void setRecipe(IRecipeLayout recipe, CuttingBoardRecipeWrapper wrapper) {
		
	}

	@Override
	public void setRecipe(IRecipeLayout recipe, CuttingBoardRecipeWrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipe.getItemStacks();
		guiItemStacks.init(0, false, 35, 21);
		guiItemStacks.init(1, true, 107, 21);
		guiItemStacks.init(2, false, 20, 20);
		guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
		guiItemStacks.set(1, ingredients.getOutputs(ItemStack.class));
		guiItemStacks.set(2, new ItemStack(EFTools.stainlessSteelKnife));
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CuttingBoardRecipes recipes) {
		return new CuttingBoardRecipeWrapper(recipes);
	}
}
