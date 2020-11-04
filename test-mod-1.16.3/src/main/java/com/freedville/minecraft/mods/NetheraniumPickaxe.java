package com.freedville.minecraft.mods;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.crafting.Ingredient;

public class NetheraniumPickaxe extends PickaxeItem  {

	public NetheraniumPickaxe(){
	    super(netheraniumTier, netheraniumTier.getMaxUses(), 0, new Properties().group(ItemGroup.TOOLS));
	    //this.setRegistryName(FreedvilleExploreMod.MODID, "netheranium_pickaxe");
	}
	
	public static IItemTier netheraniumTier = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 75;
        }

        @Override
        public float getEfficiency() {
            return 5;
        }

        @Override
        public float getAttackDamage() {
            return 0;
        }

        @Override
        public int getHarvestLevel() {
            return 1;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return null;
        }
    };
}