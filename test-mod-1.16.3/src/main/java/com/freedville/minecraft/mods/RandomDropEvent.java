package com.freedville.minecraft.mods;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

	@SubscribeEvent
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
	//This doesn't fire when you destroy blocks - that is BlockEvent.BreakEvent
	public void dropDiamondsOnDestroyItem(PlayerDestroyItemEvent event){
		Entity entity = event.getEntity();
		LOGGER.info("PlayerDestroyItemEvent Entity " + entity.getEntityString());

		//It appears these items go directly to the player.
		//That may be because on setting down the crafting table, the entity is the player.
		//I suspect "PlayerDestroyItemEvent" may contain a bug - I don't know what's being destroyed!
		dropRandomItems(event.getEntity(), Items.DIAMOND, "PlayerDestroyItemEvent");
	}
	
    
	//Don't listen to BlockEvent !! It fires all the time!
	//But you can listen to break events.
	//TODO: Block drops are handled by Looting tables, which use JSON configuration.
	@SubscribeEvent 
	public void playerThrowsDiamondsOnBlockBreak(BlockEvent.BreakEvent event){
		LOGGER.info("BreakEvent  " + event.getResult());
		ItemStack itemStackIn = new ItemStack(Items.DIAMOND, 4);
		event.getPlayer().dropItem(itemStackIn, true);
		
		//The block that we broke
		//event.getState().getBlock();

		LOGGER.info("Player should have thrown four diamonds!");
	}

}
