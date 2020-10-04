package com.freedville.minecraft.mods;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Creates a random ItemStack drop for each type of block destroyed.
 */
public class RandomItemStackSelector {

	private Random random = new Random();
    private static final Logger LOGGER = LogManager.getLogger();
	
	private List<ItemStack> itemsList = new ArrayList<ItemStack>();
	private Map<String, Integer> dropIndexByName = new HashMap<>();
	
	public RandomItemStackSelector() {
		reset();
	}

	private void reset() {
		dropIndexByName = new HashMap<>();
		itemsList = new ArrayList<ItemStack>();
		
		try {
			//Load by reflection!
			//Treat the class like an enumeration - look for instances of Item defined on Items class
			Class<?> clazz = Class.forName("net.minecraft.item.Items");
			Field fieldList[] = clazz.getDeclaredFields();
			for(Field field : fieldList) {
				if(field.getType().getName().contains("net.minecraft.item.Item")) {
					Object fieldValue = field.get(null);
					if(fieldValue != null) {
						//This will be something like Items.ACACIA_BOAT
						Item item = (Item) fieldValue;
						itemsList.add(new ItemStack(item, 1));
					}
				}
			}
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			
			//For unit test -- you can't load the class without first a registry being established
			//You can test the random behavior with this static list of drops
			itemsList.add(new ItemStack(Items.DIAMOND, 1));
			itemsList.add(new ItemStack(Items.ACACIA_LOG, 1));
			itemsList.add(new ItemStack(Items.BEDROCK, 1));
			itemsList.add(new ItemStack(Items.BLACK_CONCRETE, 1));
			itemsList.add(new ItemStack(Items.ARMOR_STAND, 1));
			itemsList.add(new ItemStack(Items.ENDER_PEARL, 1));
			itemsList.add(new ItemStack(Items.IRON_ORE, 1));
			itemsList.add(new ItemStack(Items.COBBLESTONE, 1));
			itemsList.add(new ItemStack(Items.YELLOW_BED, 1));
			itemsList.add(new ItemStack(Items.DIRT, 1));
			itemsList.add(new ItemStack(Items.GOLD_INGOT, 1));
			itemsList.add(new ItemStack(Items.SADDLE, 1));
			itemsList.add(new ItemStack(Items.PIG_SPAWN_EGG, 1));
			itemsList.add(new ItemStack(Items.CARROT_ON_A_STICK, 1));
			itemsList.add(new ItemStack(Items.GOLDEN_APPLE, 1));
		}
	}
	
	public ItemStack getItemStackForType(String type) {
		Integer index = dropIndexByName.get(type);
		if(index == null) {
			LOGGER.info(type + " has no random drop set yet!");
			index = random.nextInt(itemsList.size());
			dropIndexByName.put(type, index);
			LOGGER.info(type + " will now drop " + itemsList.get(index).toString());
		}
		
		return itemsList.get(index);
	}
	
	public static void main(String[] args) {
		//Unit test/demo - will throw an error due to no registry, but we
		//load a static list of items for testing
		RandomItemStackSelector fixture = new RandomItemStackSelector();
		String type1 = "grass";
		System.out.println("Test when empty");
		System.out.println(fixture.getItemStackForType(type1));
		
		System.out.println("Test when already set");
		System.out.println(fixture.getItemStackForType(type1));
		
		String type2 = "dirt";
		System.out.println("Test another type");
		System.out.println(fixture.getItemStackForType(type2));
		
	}
}
