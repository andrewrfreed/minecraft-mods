package com.freedville.minecraft.mods;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlock extends Block {
	final String MODID = "OurMod";

	// https://github.com/TheGreyGhost/MinecraftByExample/blob/master/src/main/java/minecraftbyexample/mbe01_block_simple/BlockSimple.java
	public ModBlock() {
		//// look at Block.Properties for further options
		// typically useful: hardnessAndResistance(), harvestLevel(), harvestTool()
		super(Block.Properties.create(Material.ROCK));
	}
}
