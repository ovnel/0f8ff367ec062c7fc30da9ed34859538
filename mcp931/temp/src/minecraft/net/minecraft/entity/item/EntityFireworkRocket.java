package net.minecraft.entity.item;

import com.google.common.base.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {
   private static final DataParameter<Optional<ItemStack>> field_184566_a = EntityDataManager.<Optional<ItemStack>>func_187226_a(EntityFireworkRocket.class, DataSerializers.field_187196_f);
   private int field_92056_a;
   private int field_92055_b;

   public EntityFireworkRocket(World p_i1762_1_) {
      super(p_i1762_1_);
      this.func_70105_a(0.25F, 0.25F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184566_a, Optional.<T>absent());
   }

   public boolean func_70112_a(double p_70112_1_) {
      return p_70112_1_ < 4096.0D;
   }

   public EntityFireworkRocket(World p_i1763_1_, double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, @Nullable ItemStack p_i1763_8_) {
      super(p_i1763_1_);
      this.field_92056_a = 0;
      this.func_70105_a(0.25F, 0.25F);
      this.func_70107_b(p_i1763_2_, p_i1763_4_, p_i1763_6_);
      int i = 1;
      if(p_i1763_8_ != null && p_i1763_8_.func_77942_o()) {
         this.field_70180_af.func_187227_b(field_184566_a, Optional.<T>of(p_i1763_8_));
         NBTTagCompound nbttagcompound = p_i1763_8_.func_77978_p();
         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("Fireworks");
         i += nbttagcompound1.func_74771_c("Flight");
      }

      this.field_70159_w = this.field_70146_Z.nextGaussian() * 0.001D;
      this.field_70179_y = this.field_70146_Z.nextGaussian() * 0.001D;
      this.field_70181_x = 0.05D;
      this.field_92055_b = 10 * i + this.field_70146_Z.nextInt(6) + this.field_70146_Z.nextInt(7);
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
      if(this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
         this.field_70177_z = (float)(MathHelper.func_181159_b(p_70016_1_, p_70016_5_) * 57.2957763671875D);
         this.field_70125_A = (float)(MathHelper.func_181159_b(p_70016_3_, (double)f) * 57.2957763671875D);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }

   }

   public void func_70071_h_() {
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      super.func_70071_h_();
      this.field_70159_w *= 1.15D;
      this.field_70179_y *= 1.15D;
      this.field_70181_x += 0.04D;
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 57.2957763671875D);

      for(this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)f) * 57.2957763671875D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
         ;
      }

      while(this.field_70125_A - this.field_70127_C >= 180.0F) {
         this.field_70127_C += 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B < -180.0F) {
         this.field_70126_B -= 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B >= 180.0F) {
         this.field_70126_B += 360.0F;
      }

      this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
      this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
      if(this.field_92056_a == 0 && !this.func_174814_R()) {
         this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187631_bo, SoundCategory.AMBIENT, 3.0F, 1.0F);
      }

      ++this.field_92056_a;
      if(this.field_70170_p.field_72995_K && this.field_92056_a % 2 < 2) {
         this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.field_70165_t, this.field_70163_u - 0.3D, this.field_70161_v, this.field_70146_Z.nextGaussian() * 0.05D, -this.field_70181_x * 0.5D, this.field_70146_Z.nextGaussian() * 0.05D, new int[0]);
      }

      if(!this.field_70170_p.field_72995_K && this.field_92056_a > this.field_92055_b) {
         this.field_70170_p.func_72960_a(this, (byte)17);
         this.func_70106_y();
      }

   }

   public void func_70103_a(byte p_70103_1_) {
      if(p_70103_1_ == 17 && this.field_70170_p.field_72995_K) {
         ItemStack itemstack = (ItemStack)((Optional)this.field_70180_af.func_187225_a(field_184566_a)).orNull();
         NBTTagCompound nbttagcompound = null;
         if(itemstack != null && itemstack.func_77942_o()) {
            nbttagcompound = itemstack.func_77978_p().func_74775_l("Fireworks");
         }

         this.field_70170_p.func_92088_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, nbttagcompound);
      }

      super.func_70103_a(p_70103_1_);
   }

   public static void func_189656_a(DataFixer p_189656_0_) {
      p_189656_0_.func_188258_a(FixTypes.ENTITY, new ItemStackData("FireworksRocketEntity", new String[]{"FireworksItem"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74768_a("Life", this.field_92056_a);
      p_70014_1_.func_74768_a("LifeTime", this.field_92055_b);
      ItemStack itemstack = (ItemStack)((Optional)this.field_70180_af.func_187225_a(field_184566_a)).orNull();
      if(itemstack != null) {
         p_70014_1_.func_74782_a("FireworksItem", itemstack.func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_92056_a = p_70037_1_.func_74762_e("Life");
      this.field_92055_b = p_70037_1_.func_74762_e("LifeTime");
      NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("FireworksItem");
      if(nbttagcompound != null) {
         ItemStack itemstack = ItemStack.func_77949_a(nbttagcompound);
         if(itemstack != null) {
            this.field_70180_af.func_187227_b(field_184566_a, Optional.<T>of(itemstack));
         }
      }

   }

   public float func_70013_c(float p_70013_1_) {
      return super.func_70013_c(p_70013_1_);
   }

   public int func_70070_b(float p_70070_1_) {
      return super.func_70070_b(p_70070_1_);
   }

   public boolean func_70075_an() {
      return false;
   }
}
