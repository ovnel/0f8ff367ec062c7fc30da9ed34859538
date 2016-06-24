package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryCraftResult implements IInventory {
   private final ItemStack[] field_70467_a = new ItemStack[1];

   public int func_70302_i_() {
      return 1;
   }

   @Nullable
   public ItemStack func_70301_a(int p_70301_1_) {
      return this.field_70467_a[0];
   }

   public String func_70005_c_() {
      return "Result";
   }

   public boolean func_145818_k_() {
      return false;
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.func_145818_k_()?new TextComponentString(this.func_70005_c_()):new TextComponentTranslation(this.func_70005_c_(), new Object[0]));
   }

   @Nullable
   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      return ItemStackHelper.func_188383_a(this.field_70467_a, 0);
   }

   @Nullable
   public ItemStack func_70304_b(int p_70304_1_) {
      return ItemStackHelper.func_188383_a(this.field_70467_a, 0);
   }

   public void func_70299_a(int p_70299_1_, @Nullable ItemStack p_70299_2_) {
      this.field_70467_a[0] = p_70299_2_;
   }

   public int func_70297_j_() {
      return 64;
   }

   public void func_70296_d() {
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      return true;
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }

   public int func_174887_a_(int p_174887_1_) {
      return 0;
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
   }

   public int func_174890_g() {
      return 0;
   }

   public void func_174888_l() {
      for(int i = 0; i < this.field_70467_a.length; ++i) {
         this.field_70467_a[i] = null;
      }

   }
}
