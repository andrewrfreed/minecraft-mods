package com.freedville.minecraft.mods;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext.Builder;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class RandomDropEvent {

	private Random random = new Random();
	
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public RandomDropEvent() {
    	LOGGER.info("Initalized start!");
		LOGGER.info("Initalized end!");
    }
    
    private void dropRandomItems(Entity entity, Item item) {
    	dropRandomItems(entity, item, "unknown");
    }
    
    private void dropRandomItems(Entity entity, Item item, String source) {
    	LOGGER.info(source + " will make entity " + entity.getEntityString() + " do a drop!");
    	
    	//Random number between 0 and 9
    	int numDrops = random.nextInt(10);
		ItemStack itemStackDrop = new ItemStack(Items.DIAMOND, numDrops);
    	entity.entityDropItem(itemStackDrop);
    	
    	LOGGER.info(source + " made entity  " + entity.getEntityString() + " dropped " + numDrops + " " + item.getName().getString() + "!");
    }
    
	@SubscribeEvent //Seems to fire twice and only the second firing drops the item
	public void dropDiamondsOnLivingAttack(LivingAttackEvent event){
		if(event.getSource().getTrueSource() instanceof PlayerEntity) {
			LOGGER.info("LivingAttackEvent by player  " + event.getResult() + " and " + event.getPhase());
			dropRandomItems(event.getEntity(), Items.DIAMOND, "LivingAttackEvent");
		}
		else {
			//Those animals are vicious to each other!!!
			LOGGER.info("LivingAttackEvent but it wasn't the player!");
		}
	}    
    
	@SubscribeEvent //untested
	public void playerThrowsDiamondsOnBlockBreak(BlockEvent.BreakEvent event){
		LOGGER.info("BreakEvent  " + event.getResult());
		ItemStack itemStackIn = new ItemStack(Items.DIAMOND, 4);
		event.getPlayer().dropItem(itemStackIn, true);
		
		//The block that we broke
		//event.getState().getBlock();
		
		//TODO: Block drops are handled by Looting tables, which use JSON configuration.

		LOGGER.info("Player should have thrown four diamonds!");
	}
	
//	@SubscribeEvent //untested
//	public void dropDiamondsOnExplosion(ExplosionEvent event){
//		LOGGER.info("ExplosionEvent  " + event.getResult());
//
//		//event.getEntity().entityDropItem(Items.DIAMOND);
//		//LOGGER.info("Entity should have dropped a diamond!");
//	}
//    
//	@SubscribeEvent //untested
//	public void dropDiamondsOnPickup(EntityItemPickupEvent event){
//		Entity entity = event.getEntity();
//		ItemEntity item = event.getItem();
//		LOGGER.info("EntityItemPickupEvent Entity " + entity.getEntityString());
//		LOGGER.info("EntityItemPickupEvent Item " + item.getEntityString());
//
//		//event.getEntity().entityDropItem(Items.DIAMOND);
//		//LOGGER.info("Entity should have dropped a diamond!");
//	}
//    
//	@SubscribeEvent // (mostly occurs in map init, but also when they move)
//	public void dropDiamondsOnGenericLiving(LivingEvent event){
//		Entity entity = event.getEntity();
//		LOGGER.info("LivingEvent Entity " + entity.getEntityString());
//
//		//event.getEntity().entityDropItem(Items.DIAMOND);
//		LOGGER.info("Entity should have dropped a diamond!");
//	}
    
	@SubscribeEvent //untested (occurs more rarely)
	public void dropDiamondsOnDeath(LivingDeathEvent event){
		if(event.getSource().getTrueSource() instanceof PlayerEntity) {
			LOGGER.info("LivingDeathEvent by player  " + event.getResult());
			dropRandomItems(event.getEntity(), Items.DIAMOND, "LivingDeathEvent");
		}
		else {
			//Those animals are vicious to each other!!!
			LOGGER.info("LivingDeathEvent but it wasn't the player!");
		}
	}
    
	@SubscribeEvent //Setting down the crafting table made this fire, but it doesn't actually drop diamonds!
	public void dropDiamondsOnDestroyItem(PlayerDestroyItemEvent event){
		Entity entity = event.getEntity();
		LOGGER.info("PlayerDestroyItemEvent Entity " + entity.getEntityString());

		//Can't actually figure out where the dropped items go!
		dropRandomItems(event.getEntity(), Items.DIAMOND);
	}
	
//	@SubscribeEvent //Don't listen to BlockEvent !! It fires all the time!
//	public void dropDiamonds(BlockEvent event){
//		LOGGER.info("Block event start!");
//		LOGGER.info("Block event end!");
//	}

}
