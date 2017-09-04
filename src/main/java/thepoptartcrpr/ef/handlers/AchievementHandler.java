package thepoptartcrpr.ef.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.init.EFItems;

public class AchievementHandler {
	
	private static List<Achievement> achievements = new ArrayList<Achievement>();
	private Achievement parent;
	
	public static Achievement achievementCrop = createAchievement("crop", 0, 0, EFItems.cornSeeds);
	public static Achievement achievementHarvest = createAchievementWithParent("harvest", 0, 2, EFItems.corn, achievementCrop);
	// public static Achievement achievementHarvest = createAchievement("harvest", 0, 2, EFItems.corn);
	public static Achievement achievementCook = createAchievementWithParent("cook", 2, 4, EFItems.breadDough, achievementHarvest);
	
	public static void registerAchievements() {
		Achievement[] achievementArray = new Achievement[achievements.size()];
		for(Achievement achievement : achievements) {
			achievement.registerStat();
			achievementArray[achievements.indexOf(achievement)] = achievement;
		}
		AchievementPage.registerAchievementPage(new AchievementPage(Variables.NAME, achievementArray));
	}
	
	private static Achievement createAchievement(String name, int column, int row, Item item) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, item, (Achievement)null);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievement(String name, int column, int row, Block block) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, block, (Achievement)null);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievement(String name, int column, int row, ItemStack stack) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, stack, (Achievement)null);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievementWithParent(String name, int column, int row, Item item, Achievement parent) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, item, parent);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievementWithParent(String name, int column, int row, Block block, Achievement parent) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, block, parent);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievementWithParent(String name, int column, int row, ItemStack stack, Achievement parent) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, stack, parent);
		achievements.add(achievement);
		return achievement;
	}
	
}
