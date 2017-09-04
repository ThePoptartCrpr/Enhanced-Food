package thepoptartcrpr.ef.compatibilities.jei;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import thepoptartcrpr.ef.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public abstract class EFRecipeCategory<T, W extends IRecipeWrapper> implements IRecipeCategory<W>, IRecipeHandler<T> {

	public String unlocalizedName;
	public String name;
	
	private final IDrawable backgroundImage;
	private final Class<T> recipeClass;
	
	public EFRecipeCategory(String unlocalizedName, String name, IDrawable backgroundImage, Class<T> recipeClass, ItemStack... displayStacks) {
		this.unlocalizedName = unlocalizedName;
		this.name = I18n.format(name);
		this.backgroundImage = backgroundImage;
		this.recipeClass = recipeClass;
		for (ItemStack stack : displayStacks) {
			JEIHelper.modRegistry.addRecipeCategoryCraftingItem(stack, getRecipeCategoryUid());
		}
	}
	
	@Nullable
	@Override
	public IDrawable getIcon() {
		return null;
	}
	
	@Override
	public String getUid() {
		return Variables.MODID + "." + unlocalizedName;
	}
	
	@Override
	public String getTitle() {
		return name;
	}
	
	@Override
	public IDrawable getBackground() {
		return backgroundImage;
	}
	
	@Override
	public void drawExtras(Minecraft mc) {
		
	}
	
	@Override
	public void drawAnimations(Minecraft mc) {
		
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return Collections.emptyList();
	}
	
	@Override
	public Class<T> getRecipeClass() {
		return this.recipeClass;
	}
	
	@Override
	public String getRecipeCategoryUid() {
		return Variables.MODID + "." + unlocalizedName;
	}
	
	@Override
	public String getRecipeCategoryUid(@Nonnull T recipe) {
		return Variables.MODID + "." + unlocalizedName;
	}
	
	@Override
	public boolean isRecipeValid(T recipe) {
		return true;
	}
	
}
