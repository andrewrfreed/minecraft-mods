package com.freedville.minecraft.mods;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//https://thebookofmodding.ml/adding-custom-items/
public class RegistryHandler {
    // create DeferredRegister object
    public static final DeferredRegister<Item>  ITEMS  = DeferredRegister.create(ForgeRegistries.ITEMS, FreedvilleExploreMod.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FreedvilleExploreMod.MODID);

 
    public static final RegistryObject<Block> SUPERIUM_BLOCK = BLOCKS.register("superium", () ->
	new Block(
        Block.Properties
        	.create(Material.IRON)
        	.sound(SoundType.STONE)
        	.harvestLevel(4)
        	.harvestTool(ToolType.PICKAXE)
        	.hardnessAndResistance(10.f, 2000.0f)
			)
	);


    // register item
    public static final RegistryObject<Item> SUPERIUM_ITEM = ITEMS.register("superium",
		() -> new BlockItem(
				SUPERIUM_BLOCK.get(), 
				new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)
			)
	);
    
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () ->
            new Block(
                    Block.Properties
                            .create(Material.IRON)
                            .hardnessAndResistance(5.0f, 6.0f)
                            .sound(SoundType.STONE)
                            .harvestLevel(1)
                            .harvestTool(ToolType.PICKAXE)
            )
    );
    
    public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () ->
            new BlockItem(
                    COPPER_ORE.get(),
                    new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)
            )
    );
    
    //public static final RegistryObject<Item> SUPERIUM = 		 createAndRegister("superium", ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Item> NETHERANIUM_INGOT = createAndRegister("netheranium_ingot", ItemGroup.MATERIALS);
    public static final RegistryObject<Item> NETHERANIUM_SCRAP = createAndRegister("netheranium_scrap", ItemGroup.MATERIALS);
    
    private static RegistryObject<Item> createAndRegister(String name, ItemGroup group) {
    	return ITEMS.register(name, () ->
        	new Item(
                new Item.Properties().group(group)
            )
        );
    }
    
    
    public static void init() {
        // attach DeferredRegister to the event bus
    	BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        //You cannot create instances of registry entries (e.g. Block) in static initializers.
//        
//        //Superium can be placed - but superium items have the wrong resource pack
//        //  The name is "Air" and the icon is undefined
//        net.minecraft.item.Item.Properties itemProperties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
//        net.minecraft.block.AbstractBlock.Properties blockProperties = net.minecraft.block.AbstractBlock.Properties.create(Material.IRON);
//        
//        final SuperiumBlock superiumBlock = new SuperiumBlock(blockProperties);
//        
//        /* BlockItem */ superiumItem = new BlockItem(superiumBlock, itemProperties);
//        //superiumItem.setRegistryName(FreedvilleExploreMod.MODID, "superium"); 	
//        ITEMS.register("superium", () -> superiumItem);
//        //superiumBlock.setRegistryName(FreedvilleExploreMod.MODID, "superium");
//        BLOCKS.register("superium", () -> superiumBlock);

    }

    // register item - copper block - from online example
    public static final RegistryObject<Item> COPPER = ITEMS.register("copper", () ->
            new Item(
                    new Item.Properties().group(ItemGroup.MATERIALS)
            )
    );

}
