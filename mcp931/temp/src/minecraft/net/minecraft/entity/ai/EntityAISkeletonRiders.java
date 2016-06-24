package net.minecraft.entity.ai;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;

public class EntityAISkeletonRiders extends EntityAIBase {
   private final EntityHorse field_188516_a;

   public EntityAISkeletonRiders(EntityHorse p_i46797_1_) {
      this.field_188516_a = p_i46797_1_;
   }

   public boolean func_75250_a() {
      return this.field_188516_a.field_70170_p.func_175636_b(this.field_188516_a.field_70165_t, this.field_188516_a.field_70163_u, this.field_188516_a.field_70161_v, 10.0D);
   }

   public void func_75246_d() {
      DifficultyInstance difficultyinstance = this.field_188516_a.field_70170_p.func_175649_E(new BlockPos(this.field_188516_a));
      this.field_188516_a.func_184784_x(false);
      this.field_188516_a.func_184778_a(HorseType.SKELETON);
      this.field_188516_a.func_110234_j(true);
      this.field_188516_a.func_70873_a(0);
      this.field_188516_a.field_70170_p.func_72942_c(new EntityLightningBolt(this.field_188516_a.field_70170_p, this.field_188516_a.field_70165_t, this.field_188516_a.field_70163_u, this.field_188516_a.field_70161_v, true));
      EntitySkeleton entityskeleton = this.func_188514_a(difficultyinstance, this.field_188516_a);
      entityskeleton.func_184220_m(this.field_188516_a);

      for(int i = 0; i < 3; ++i) {
         EntityHorse entityhorse = this.func_188515_a(difficultyinstance);
         EntitySkeleton entityskeleton1 = this.func_188514_a(difficultyinstance, entityhorse);
         entityskeleton1.func_184220_m(entityhorse);
         entityhorse.func_70024_g(this.field_188516_a.func_70681_au().nextGaussian() * 0.5D, 0.0D, this.field_188516_a.func_70681_au().nextGaussian() * 0.5D);
      }

   }

   private EntityHorse func_188515_a(DifficultyInstance p_188515_1_) {
      EntityHorse entityhorse = new EntityHorse(this.field_188516_a.field_70170_p);
      entityhorse.func_180482_a(p_188515_1_, (IEntityLivingData)null);
      entityhorse.func_70107_b(this.field_188516_a.field_70165_t, this.field_188516_a.field_70163_u, this.field_188516_a.field_70161_v);
      entityhorse.field_70172_ad = 60;
      entityhorse.func_110163_bv();
      entityhorse.func_184778_a(HorseType.SKELETON);
      entityhorse.func_110234_j(true);
      entityhorse.func_70873_a(0);
      entityhorse.field_70170_p.func_72838_d(entityhorse);
      return entityhorse;
   }

   private EntitySkeleton func_188514_a(DifficultyInstance p_188514_1_, EntityHorse p_188514_2_) {
      EntitySkeleton entityskeleton = new EntitySkeleton(p_188514_2_.field_70170_p);
      entityskeleton.func_180482_a(p_188514_1_, (IEntityLivingData)null);
      entityskeleton.func_70107_b(p_188514_2_.field_70165_t, p_188514_2_.field_70163_u, p_188514_2_.field_70161_v);
      entityskeleton.field_70172_ad = 60;
      entityskeleton.func_110163_bv();
      if(entityskeleton.func_184582_a(EntityEquipmentSlot.HEAD) == null) {
         entityskeleton.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(Items.field_151028_Y));
      }

      EnchantmentHelper.func_77504_a(entityskeleton.func_70681_au(), entityskeleton.func_184614_ca(), (int)(5.0F + p_188514_1_.func_180170_c() * (float)entityskeleton.func_70681_au().nextInt(18)), false);
      EnchantmentHelper.func_77504_a(entityskeleton.func_70681_au(), entityskeleton.func_184582_a(EntityEquipmentSlot.HEAD), (int)(5.0F + p_188514_1_.func_180170_c() * (float)entityskeleton.func_70681_au().nextInt(18)), false);
      entityskeleton.field_70170_p.func_72838_d(entityskeleton);
      return entityskeleton;
   }
}
