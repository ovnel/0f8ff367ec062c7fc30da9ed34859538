package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;

public abstract class Container {
   public List<ItemStack> field_75153_a = Lists.<ItemStack>newArrayList();
   public List<Slot> field_75151_b = Lists.<Slot>newArrayList();
   public int field_75152_c;
   private short field_75150_e;
   private int field_94535_f = -1;
   private int field_94536_g;
   private final Set<Slot> field_94537_h = Sets.<Slot>newHashSet();
   protected List<IContainerListener> field_75149_d = Lists.<IContainerListener>newArrayList();
   private final Set<EntityPlayer> field_75148_f = Sets.<EntityPlayer>newHashSet();

   protected Slot func_75146_a(Slot p_75146_1_) {
      p_75146_1_.field_75222_d = this.field_75151_b.size();
      this.field_75151_b.add(p_75146_1_);
      this.field_75153_a.add((ItemStack)null);
      return p_75146_1_;
   }

   public void func_75132_a(IContainerListener p_75132_1_) {
      if(this.field_75149_d.contains(p_75132_1_)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.field_75149_d.add(p_75132_1_);
         p_75132_1_.func_71110_a(this, this.func_75138_a());
         this.func_75142_b();
      }
   }

   public void func_82847_b(IContainerListener p_82847_1_) {
      this.field_75149_d.remove(p_82847_1_);
   }

   public List<ItemStack> func_75138_a() {
      List<ItemStack> list = Lists.<ItemStack>newArrayList();

      for(int i = 0; i < this.field_75151_b.size(); ++i) {
         list.add(((Slot)this.field_75151_b.get(i)).func_75211_c());
      }

      return list;
   }

   public void func_75142_b() {
      for(int i = 0; i < this.field_75151_b.size(); ++i) {
         ItemStack itemstack = ((Slot)this.field_75151_b.get(i)).func_75211_c();
         ItemStack itemstack1 = (ItemStack)this.field_75153_a.get(i);
         if(!ItemStack.func_77989_b(itemstack1, itemstack)) {
            itemstack1 = itemstack == null?null:itemstack.func_77946_l();
            this.field_75153_a.set(i, itemstack1);

            for(int j = 0; j < this.field_75149_d.size(); ++j) {
               ((IContainerListener)this.field_75149_d.get(j)).func_71111_a(this, i, itemstack1);
            }
         }
      }

   }

   public boolean func_75140_a(EntityPlayer p_75140_1_, int p_75140_2_) {
      return false;
   }

   @Nullable
   public Slot func_75147_a(IInventory p_75147_1_, int p_75147_2_) {
      for(int i = 0; i < this.field_75151_b.size(); ++i) {
         Slot slot = (Slot)this.field_75151_b.get(i);
         if(slot.func_75217_a(p_75147_1_, p_75147_2_)) {
            return slot;
         }
      }

      return null;
   }

   public Slot func_75139_a(int p_75139_1_) {
      return (Slot)this.field_75151_b.get(p_75139_1_);
   }

   @Nullable
   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);
      return slot != null?slot.func_75211_c():null;
   }

   @Nullable
   public ItemStack func_184996_a(int p_184996_1_, int p_184996_2_, ClickType p_184996_3_, EntityPlayer p_184996_4_) {
      ItemStack itemstack = null;
      InventoryPlayer inventoryplayer = p_184996_4_.field_71071_by;
      if(p_184996_3_ == ClickType.QUICK_CRAFT) {
         int i = this.field_94536_g;
         this.field_94536_g = func_94532_c(p_184996_2_);
         if((i != 1 || this.field_94536_g != 2) && i != this.field_94536_g) {
            this.func_94533_d();
         } else if(inventoryplayer.func_70445_o() == null) {
            this.func_94533_d();
         } else if(this.field_94536_g == 0) {
            this.field_94535_f = func_94529_b(p_184996_2_);
            if(func_180610_a(this.field_94535_f, p_184996_4_)) {
               this.field_94536_g = 1;
               this.field_94537_h.clear();
            } else {
               this.func_94533_d();
            }
         } else if(this.field_94536_g == 1) {
            Slot slot = (Slot)this.field_75151_b.get(p_184996_1_);
            if(slot != null && func_94527_a(slot, inventoryplayer.func_70445_o(), true) && slot.func_75214_a(inventoryplayer.func_70445_o()) && inventoryplayer.func_70445_o().field_77994_a > this.field_94537_h.size() && this.func_94531_b(slot)) {
               this.field_94537_h.add(slot);
            }
         } else if(this.field_94536_g == 2) {
            if(!this.field_94537_h.isEmpty()) {
               ItemStack itemstack3 = inventoryplayer.func_70445_o().func_77946_l();
               int j = inventoryplayer.func_70445_o().field_77994_a;

               for(Slot slot1 : this.field_94537_h) {
                  if(slot1 != null && func_94527_a(slot1, inventoryplayer.func_70445_o(), true) && slot1.func_75214_a(inventoryplayer.func_70445_o()) && inventoryplayer.func_70445_o().field_77994_a >= this.field_94537_h.size() && this.func_94531_b(slot1)) {
                     ItemStack itemstack1 = itemstack3.func_77946_l();
                     int k = slot1.func_75216_d()?slot1.func_75211_c().field_77994_a:0;
                     func_94525_a(this.field_94537_h, this.field_94535_f, itemstack1, k);
                     if(itemstack1.field_77994_a > itemstack1.func_77976_d()) {
                        itemstack1.field_77994_a = itemstack1.func_77976_d();
                     }

                     if(itemstack1.field_77994_a > slot1.func_178170_b(itemstack1)) {
                        itemstack1.field_77994_a = slot1.func_178170_b(itemstack1);
                     }

                     j -= itemstack1.field_77994_a - k;
                     slot1.func_75215_d(itemstack1);
                  }
               }

               itemstack3.field_77994_a = j;
               if(itemstack3.field_77994_a <= 0) {
                  itemstack3 = null;
               }

               inventoryplayer.func_70437_b(itemstack3);
            }

            this.func_94533_d();
         } else {
            this.func_94533_d();
         }
      } else if(this.field_94536_g != 0) {
         this.func_94533_d();
      } else if((p_184996_3_ == ClickType.PICKUP || p_184996_3_ == ClickType.QUICK_MOVE) && (p_184996_2_ == 0 || p_184996_2_ == 1)) {
         if(p_184996_1_ == -999) {
            if(inventoryplayer.func_70445_o() != null) {
               if(p_184996_2_ == 0) {
                  p_184996_4_.func_71019_a(inventoryplayer.func_70445_o(), true);
                  inventoryplayer.func_70437_b((ItemStack)null);
               }

               if(p_184996_2_ == 1) {
                  p_184996_4_.func_71019_a(inventoryplayer.func_70445_o().func_77979_a(1), true);
                  if(inventoryplayer.func_70445_o().field_77994_a == 0) {
                     inventoryplayer.func_70437_b((ItemStack)null);
                  }
               }
            }
         } else if(p_184996_3_ == ClickType.QUICK_MOVE) {
            if(p_184996_1_ < 0) {
               return null;
            }

            Slot slot6 = (Slot)this.field_75151_b.get(p_184996_1_);
            if(slot6 != null && slot6.func_82869_a(p_184996_4_)) {
               ItemStack itemstack8 = slot6.func_75211_c();
               if(itemstack8 != null && itemstack8.field_77994_a <= 0) {
                  itemstack = itemstack8.func_77946_l();
                  slot6.func_75215_d((ItemStack)null);
               }

               ItemStack itemstack11 = this.func_82846_b(p_184996_4_, p_184996_1_);
               if(itemstack11 != null) {
                  Item item = itemstack11.func_77973_b();
                  itemstack = itemstack11.func_77946_l();
                  if(slot6.func_75211_c() != null && slot6.func_75211_c().func_77973_b() == item) {
                     this.func_75133_b(p_184996_1_, p_184996_2_, true, p_184996_4_);
                  }
               }
            }
         } else {
            if(p_184996_1_ < 0) {
               return null;
            }

            Slot slot7 = (Slot)this.field_75151_b.get(p_184996_1_);
            if(slot7 != null) {
               ItemStack itemstack9 = slot7.func_75211_c();
               ItemStack itemstack12 = inventoryplayer.func_70445_o();
               if(itemstack9 != null) {
                  itemstack = itemstack9.func_77946_l();
               }

               if(itemstack9 == null) {
                  if(itemstack12 != null && slot7.func_75214_a(itemstack12)) {
                     int l2 = p_184996_2_ == 0?itemstack12.field_77994_a:1;
                     if(l2 > slot7.func_178170_b(itemstack12)) {
                        l2 = slot7.func_178170_b(itemstack12);
                     }

                     slot7.func_75215_d(itemstack12.func_77979_a(l2));
                     if(itemstack12.field_77994_a == 0) {
                        inventoryplayer.func_70437_b((ItemStack)null);
                     }
                  }
               } else if(slot7.func_82869_a(p_184996_4_)) {
                  if(itemstack12 == null) {
                     if(itemstack9.field_77994_a > 0) {
                        int k2 = p_184996_2_ == 0?itemstack9.field_77994_a:(itemstack9.field_77994_a + 1) / 2;
                        inventoryplayer.func_70437_b(slot7.func_75209_a(k2));
                        if(itemstack9.field_77994_a <= 0) {
                           slot7.func_75215_d((ItemStack)null);
                        }

                        slot7.func_82870_a(p_184996_4_, inventoryplayer.func_70445_o());
                     } else {
                        slot7.func_75215_d((ItemStack)null);
                        inventoryplayer.func_70437_b((ItemStack)null);
                     }
                  } else if(slot7.func_75214_a(itemstack12)) {
                     if(itemstack9.func_77973_b() == itemstack12.func_77973_b() && itemstack9.func_77960_j() == itemstack12.func_77960_j() && ItemStack.func_77970_a(itemstack9, itemstack12)) {
                        int j2 = p_184996_2_ == 0?itemstack12.field_77994_a:1;
                        if(j2 > slot7.func_178170_b(itemstack12) - itemstack9.field_77994_a) {
                           j2 = slot7.func_178170_b(itemstack12) - itemstack9.field_77994_a;
                        }

                        if(j2 > itemstack12.func_77976_d() - itemstack9.field_77994_a) {
                           j2 = itemstack12.func_77976_d() - itemstack9.field_77994_a;
                        }

                        itemstack12.func_77979_a(j2);
                        if(itemstack12.field_77994_a == 0) {
                           inventoryplayer.func_70437_b((ItemStack)null);
                        }

                        itemstack9.field_77994_a += j2;
                     } else if(itemstack12.field_77994_a <= slot7.func_178170_b(itemstack12)) {
                        slot7.func_75215_d(itemstack12);
                        inventoryplayer.func_70437_b(itemstack9);
                     }
                  } else if(itemstack9.func_77973_b() == itemstack12.func_77973_b() && itemstack12.func_77976_d() > 1 && (!itemstack9.func_77981_g() || itemstack9.func_77960_j() == itemstack12.func_77960_j()) && ItemStack.func_77970_a(itemstack9, itemstack12)) {
                     int i2 = itemstack9.field_77994_a;
                     if(i2 > 0 && i2 + itemstack12.field_77994_a <= itemstack12.func_77976_d()) {
                        itemstack12.field_77994_a += i2;
                        itemstack9 = slot7.func_75209_a(i2);
                        if(itemstack9.field_77994_a == 0) {
                           slot7.func_75215_d((ItemStack)null);
                        }

                        slot7.func_82870_a(p_184996_4_, inventoryplayer.func_70445_o());
                     }
                  }
               }

               slot7.func_75218_e();
            }
         }
      } else if(p_184996_3_ == ClickType.SWAP && p_184996_2_ >= 0 && p_184996_2_ < 9) {
         Slot slot5 = (Slot)this.field_75151_b.get(p_184996_1_);
         ItemStack itemstack7 = inventoryplayer.func_70301_a(p_184996_2_);
         if(itemstack7 != null && itemstack7.field_77994_a <= 0) {
            itemstack7 = null;
            inventoryplayer.func_70299_a(p_184996_2_, (ItemStack)null);
         }

         ItemStack itemstack10 = slot5.func_75211_c();
         if(itemstack7 != null || itemstack10 != null) {
            if(itemstack7 == null) {
               if(slot5.func_82869_a(p_184996_4_)) {
                  inventoryplayer.func_70299_a(p_184996_2_, itemstack10);
                  slot5.func_75215_d((ItemStack)null);
                  slot5.func_82870_a(p_184996_4_, itemstack10);
               }
            } else if(itemstack10 == null) {
               if(slot5.func_75214_a(itemstack7)) {
                  int k1 = slot5.func_178170_b(itemstack7);
                  if(itemstack7.field_77994_a > k1) {
                     slot5.func_75215_d(itemstack7.func_77979_a(k1));
                  } else {
                     slot5.func_75215_d(itemstack7);
                     inventoryplayer.func_70299_a(p_184996_2_, (ItemStack)null);
                  }
               }
            } else if(slot5.func_82869_a(p_184996_4_) && slot5.func_75214_a(itemstack7)) {
               int l1 = slot5.func_178170_b(itemstack7);
               if(itemstack7.field_77994_a > l1) {
                  slot5.func_75215_d(itemstack7.func_77979_a(l1));
                  slot5.func_82870_a(p_184996_4_, itemstack10);
                  if(!inventoryplayer.func_70441_a(itemstack10)) {
                     p_184996_4_.func_71019_a(itemstack10, true);
                  }
               } else {
                  slot5.func_75215_d(itemstack7);
                  inventoryplayer.func_70299_a(p_184996_2_, itemstack10);
                  slot5.func_82870_a(p_184996_4_, itemstack10);
               }
            }
         }
      } else if(p_184996_3_ == ClickType.CLONE && p_184996_4_.field_71075_bZ.field_75098_d && inventoryplayer.func_70445_o() == null && p_184996_1_ >= 0) {
         Slot slot4 = (Slot)this.field_75151_b.get(p_184996_1_);
         if(slot4 != null && slot4.func_75216_d()) {
            if(slot4.func_75211_c().field_77994_a > 0) {
               ItemStack itemstack6 = slot4.func_75211_c().func_77946_l();
               itemstack6.field_77994_a = itemstack6.func_77976_d();
               inventoryplayer.func_70437_b(itemstack6);
            } else {
               slot4.func_75215_d((ItemStack)null);
            }
         }
      } else if(p_184996_3_ == ClickType.THROW && inventoryplayer.func_70445_o() == null && p_184996_1_ >= 0) {
         Slot slot3 = (Slot)this.field_75151_b.get(p_184996_1_);
         if(slot3 != null && slot3.func_75216_d() && slot3.func_82869_a(p_184996_4_)) {
            ItemStack itemstack5 = slot3.func_75209_a(p_184996_2_ == 0?1:slot3.func_75211_c().field_77994_a);
            slot3.func_82870_a(p_184996_4_, itemstack5);
            p_184996_4_.func_71019_a(itemstack5, true);
         }
      } else if(p_184996_3_ == ClickType.PICKUP_ALL && p_184996_1_ >= 0) {
         Slot slot2 = (Slot)this.field_75151_b.get(p_184996_1_);
         ItemStack itemstack4 = inventoryplayer.func_70445_o();
         if(itemstack4 != null && (slot2 == null || !slot2.func_75216_d() || !slot2.func_82869_a(p_184996_4_))) {
            int i1 = p_184996_2_ == 0?0:this.field_75151_b.size() - 1;
            int j1 = p_184996_2_ == 0?1:-1;

            for(int i3 = 0; i3 < 2; ++i3) {
               for(int j3 = i1; j3 >= 0 && j3 < this.field_75151_b.size() && itemstack4.field_77994_a < itemstack4.func_77976_d(); j3 += j1) {
                  Slot slot8 = (Slot)this.field_75151_b.get(j3);
                  if(slot8.func_75216_d() && func_94527_a(slot8, itemstack4, true) && slot8.func_82869_a(p_184996_4_) && this.func_94530_a(itemstack4, slot8) && (i3 != 0 || slot8.func_75211_c().field_77994_a != slot8.func_75211_c().func_77976_d())) {
                     int l = Math.min(itemstack4.func_77976_d() - itemstack4.field_77994_a, slot8.func_75211_c().field_77994_a);
                     ItemStack itemstack2 = slot8.func_75209_a(l);
                     itemstack4.field_77994_a += l;
                     if(itemstack2.field_77994_a <= 0) {
                        slot8.func_75215_d((ItemStack)null);
                     }

                     slot8.func_82870_a(p_184996_4_, itemstack2);
                  }
               }
            }
         }

         this.func_75142_b();
      }

      return itemstack;
   }

   public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_) {
      return true;
   }

   protected void func_75133_b(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_) {
      this.func_184996_a(p_75133_1_, p_75133_2_, ClickType.QUICK_MOVE, p_75133_4_);
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      InventoryPlayer inventoryplayer = p_75134_1_.field_71071_by;
      if(inventoryplayer.func_70445_o() != null) {
         p_75134_1_.func_71019_a(inventoryplayer.func_70445_o(), false);
         inventoryplayer.func_70437_b((ItemStack)null);
      }

   }

   public void func_75130_a(IInventory p_75130_1_) {
      this.func_75142_b();
   }

   public void func_75141_a(int p_75141_1_, ItemStack p_75141_2_) {
      this.func_75139_a(p_75141_1_).func_75215_d(p_75141_2_);
   }

   public void func_75131_a(ItemStack[] p_75131_1_) {
      for(int i = 0; i < p_75131_1_.length; ++i) {
         this.func_75139_a(i).func_75215_d(p_75131_1_[i]);
      }

   }

   public void func_75137_b(int p_75137_1_, int p_75137_2_) {
   }

   public short func_75136_a(InventoryPlayer p_75136_1_) {
      ++this.field_75150_e;
      return this.field_75150_e;
   }

   public boolean func_75129_b(EntityPlayer p_75129_1_) {
      return !this.field_75148_f.contains(p_75129_1_);
   }

   public void func_75128_a(EntityPlayer p_75128_1_, boolean p_75128_2_) {
      if(p_75128_2_) {
         this.field_75148_f.remove(p_75128_1_);
      } else {
         this.field_75148_f.add(p_75128_1_);
      }

   }

   public abstract boolean func_75145_c(EntityPlayer var1);

   protected boolean func_75135_a(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
      boolean flag = false;
      int i = p_75135_2_;
      if(p_75135_4_) {
         i = p_75135_3_ - 1;
      }

      if(p_75135_1_.func_77985_e()) {
         while(p_75135_1_.field_77994_a > 0 && (!p_75135_4_ && i < p_75135_3_ || p_75135_4_ && i >= p_75135_2_)) {
            Slot slot = (Slot)this.field_75151_b.get(i);
            ItemStack itemstack = slot.func_75211_c();
            if(itemstack != null && func_184997_a(p_75135_1_, itemstack)) {
               int j = itemstack.field_77994_a + p_75135_1_.field_77994_a;
               if(j <= p_75135_1_.func_77976_d()) {
                  p_75135_1_.field_77994_a = 0;
                  itemstack.field_77994_a = j;
                  slot.func_75218_e();
                  flag = true;
               } else if(itemstack.field_77994_a < p_75135_1_.func_77976_d()) {
                  p_75135_1_.field_77994_a -= p_75135_1_.func_77976_d() - itemstack.field_77994_a;
                  itemstack.field_77994_a = p_75135_1_.func_77976_d();
                  slot.func_75218_e();
                  flag = true;
               }
            }

            if(p_75135_4_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      if(p_75135_1_.field_77994_a > 0) {
         if(p_75135_4_) {
            i = p_75135_3_ - 1;
         } else {
            i = p_75135_2_;
         }

         while(!p_75135_4_ && i < p_75135_3_ || p_75135_4_ && i >= p_75135_2_) {
            Slot slot1 = (Slot)this.field_75151_b.get(i);
            ItemStack itemstack1 = slot1.func_75211_c();
            if(itemstack1 == null) {
               slot1.func_75215_d(p_75135_1_.func_77946_l());
               slot1.func_75218_e();
               p_75135_1_.field_77994_a = 0;
               flag = true;
               break;
            }

            if(p_75135_4_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      return flag;
   }

   private static boolean func_184997_a(ItemStack p_184997_0_, ItemStack p_184997_1_) {
      return p_184997_1_.func_77973_b() == p_184997_0_.func_77973_b() && (!p_184997_0_.func_77981_g() || p_184997_0_.func_77960_j() == p_184997_1_.func_77960_j()) && ItemStack.func_77970_a(p_184997_0_, p_184997_1_);
   }

   public static int func_94529_b(int p_94529_0_) {
      return p_94529_0_ >> 2 & 3;
   }

   public static int func_94532_c(int p_94532_0_) {
      return p_94532_0_ & 3;
   }

   public static int func_94534_d(int p_94534_0_, int p_94534_1_) {
      return p_94534_0_ & 3 | (p_94534_1_ & 3) << 2;
   }

   public static boolean func_180610_a(int p_180610_0_, EntityPlayer p_180610_1_) {
      return p_180610_0_ == 0?true:(p_180610_0_ == 1?true:p_180610_0_ == 2 && p_180610_1_.field_71075_bZ.field_75098_d);
   }

   protected void func_94533_d() {
      this.field_94536_g = 0;
      this.field_94537_h.clear();
   }

   public static boolean func_94527_a(Slot p_94527_0_, ItemStack p_94527_1_, boolean p_94527_2_) {
      boolean flag = p_94527_0_ == null || !p_94527_0_.func_75216_d();
      if(p_94527_0_ != null && p_94527_0_.func_75216_d() && p_94527_1_ != null && p_94527_1_.func_77969_a(p_94527_0_.func_75211_c()) && ItemStack.func_77970_a(p_94527_0_.func_75211_c(), p_94527_1_)) {
         flag |= p_94527_0_.func_75211_c().field_77994_a + (p_94527_2_?0:p_94527_1_.field_77994_a) <= p_94527_1_.func_77976_d();
      }

      return flag;
   }

   public static void func_94525_a(Set<Slot> p_94525_0_, int p_94525_1_, ItemStack p_94525_2_, int p_94525_3_) {
      switch(p_94525_1_) {
      case 0:
         p_94525_2_.field_77994_a = MathHelper.func_76141_d((float)p_94525_2_.field_77994_a / (float)p_94525_0_.size());
         break;
      case 1:
         p_94525_2_.field_77994_a = 1;
         break;
      case 2:
         p_94525_2_.field_77994_a = p_94525_2_.func_77973_b().func_77639_j();
      }

      p_94525_2_.field_77994_a += p_94525_3_;
   }

   public boolean func_94531_b(Slot p_94531_1_) {
      return true;
   }

   public static int func_178144_a(@Nullable TileEntity p_178144_0_) {
      return p_178144_0_ instanceof IInventory?func_94526_b((IInventory)p_178144_0_):0;
   }

   public static int func_94526_b(@Nullable IInventory p_94526_0_) {
      if(p_94526_0_ == null) {
         return 0;
      } else {
         int i = 0;
         float f = 0.0F;

         for(int j = 0; j < p_94526_0_.func_70302_i_(); ++j) {
            ItemStack itemstack = p_94526_0_.func_70301_a(j);
            if(itemstack != null) {
               f += (float)itemstack.field_77994_a / (float)Math.min(p_94526_0_.func_70297_j_(), itemstack.func_77976_d());
               ++i;
            }
         }

         f = f / (float)p_94526_0_.func_70302_i_();
         return MathHelper.func_76141_d(f * 14.0F) + (i > 0?1:0);
      }
   }
}
