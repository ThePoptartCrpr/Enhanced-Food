package thepoptartcrpr.ef.recipes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.utils.Utils;

public class CuttingBoardRecipes {
	
	public final Object input;
	public final ItemStack output;
	public final int knifeTime, noTime;
	
	private static final Map<ItemStack, ItemStack> cuttingList = Maps.<ItemStack, ItemStack>newHashMap();
	private static final Map<ItemStack, Integer> knifeTimeList = Maps.<ItemStack, Integer>newHashMap();
	private static final Map<ItemStack, Integer> noTimeList = Maps.<ItemStack, Integer>newHashMap();
	public static ArrayList<CuttingBoardRecipes> recipeList = new ArrayList<CuttingBoardRecipes>();
	
	public static void registerCuttingRecipes() {
		addCuttingRecipe(new ItemStack(EFItems.breadDough), new ItemStack(Items.BREAD), 40, 40);
	}
	
	public CuttingBoardRecipes(ItemStack output, Object input, int knifeTime, int noTime) {
		this.output = output;
		this.input = input;
		this.knifeTime = knifeTime;
		this.noTime = noTime;
	}
	
	@Nullable
	public static ItemStack getCuttingResult(ItemStack stack) {
		/*for (Entry<ItemStack, ItemStack> entry : cuttingList.entrySet()) {
			if (compareItemStacks(stack, (ItemStack)entry.getKey())) {
				return (ItemStack)entry.getValue();
			}
		}*/
		for (int i = 0; i < recipeList.size(); i++) {
			// if (compareItemStacks((ItemStack) recipeList.get(i).input, stack)) {
			if (ItemStack.areItemsEqual((ItemStack) recipeList.get(i).input, stack)) {
				return (ItemStack) recipeList.get(i).output;
			}
		}
		return null;
	}
	
	public static int getCuttingTimeKnife(ItemStack stack) {
		for (int i = 0; i < recipeList.size(); i++) {
			if (ItemStack.areItemsEqual((ItemStack) recipeList.get(i).input, stack)) {
				return recipeList.get(i).knifeTime;
			}
		}
		return 0;
	}
	
	public static int getCuttingTimeNoKnife(ItemStack stack) {
		for (int i = 0; i < recipeList.size(); i++) {
			if (ItemStack.areItemsEqual((ItemStack) recipeList.get(i).input, stack)) {
				return recipeList.get(i).noTime;
			}
		}
		return 0;
	}
	
	public static void addCuttingRecipe(ItemStack output, Object input, int knifeTime, int noTime) {
		ItemStack inputStack = (ItemStack) input;
		if (getCuttingResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			cuttingList.put(inputStack, output);
			CuttingBoardRecipes recipe = new CuttingBoardRecipes(inputStack, output, knifeTime, noTime);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
	public static void addOvenRecipe(Block input, Item output, int knifeTime, int noTime) {
		Item inputBlock = Item.getItemFromBlock(input);
		ItemStack inputStack = new ItemStack(inputBlock);
		ItemStack outputStack = new ItemStack(output);
		if (getCuttingResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			cuttingList.put(inputStack, outputStack);
			outputStack = new ItemStack(output);
			CuttingBoardRecipes recipe = new CuttingBoardRecipes(outputStack, input, knifeTime, noTime);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
	public static void addOvenRecipe(Item input, ItemStack output, int knifeTime, int noTime) {
		ItemStack inputStack = new ItemStack(input);
		if (getCuttingResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			cuttingList.put(inputStack, output);
			CuttingBoardRecipes recipe = new CuttingBoardRecipes(output, inputStack, knifeTime, noTime);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
	public static void addOvenRecipe(Block input, ItemStack output, int knifeTime, int noTime) {
		Item inputBlock = Item.getItemFromBlock(input);
		ItemStack inputStack = new ItemStack(inputBlock);
		if (getCuttingResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			cuttingList.put(inputStack, output);
			CuttingBoardRecipes recipe = new CuttingBoardRecipes(output, input, knifeTime, noTime);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}

}
