package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;

public class ItemStackHelper {
   @Nullable
   public static ItemStack func_188382_a(ItemStack[] p_188382_0_, int p_188382_1_, int p_188382_2_) {
      if(p_188382_1_ >= 0 && p_188382_1_ < p_188382_0_.length && p_188382_0_[p_188382_1_] != null && p_188382_2_ > 0) {
         ItemStack itemstack = p_188382_0_[p_188382_1_].func_77979_a(p_188382_2_);
         if(p_188382_0_[p_188382_1_].field_77994_a == 0) {
            p_188382_0_[p_188382_1_] = null;
         }

         return itemstack;
      } else {
         return null;
      }
   }

   @Nullable
   public static ItemStack func_188383_a(ItemStack[] p_188383_0_, int p_188383_1_) {
      if(p_188383_1_ >= 0 && p_188383_1_ < p_188383_0_.length) {
         ItemStack itemstack = p_188383_0_[p_188383_1_];
         p_188383_0_[p_188383_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }
}
