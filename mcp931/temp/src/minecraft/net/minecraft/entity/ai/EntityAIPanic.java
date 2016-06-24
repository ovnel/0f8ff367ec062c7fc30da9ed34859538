package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIPanic extends EntityAIBase {
   private final EntityCreature field_75267_a;
   protected double field_75265_b;
   private double field_75266_c;
   private double field_75263_d;
   private double field_75264_e;

   public EntityAIPanic(EntityCreature p_i1645_1_, double p_i1645_2_) {
      this.field_75267_a = p_i1645_1_;
      this.field_75265_b = p_i1645_2_;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if(this.field_75267_a.func_70643_av() == null && !this.field_75267_a.func_70027_ad()) {
         return false;
      } else if(!this.field_75267_a.func_70027_ad()) {
         Vec3d vec3d = RandomPositionGenerator.func_75463_a(this.field_75267_a, 5, 4);
         if(vec3d == null) {
            return false;
         } else {
            this.field_75266_c = vec3d.field_72450_a;
            this.field_75263_d = vec3d.field_72448_b;
            this.field_75264_e = vec3d.field_72449_c;
            return true;
         }
      } else {
         BlockPos blockpos = this.func_188497_a(this.field_75267_a.field_70170_p, this.field_75267_a, 5, 4);
         if(blockpos == null) {
            return false;
         } else {
            this.field_75266_c = (double)blockpos.func_177958_n();
            this.field_75263_d = (double)blockpos.func_177956_o();
            this.field_75264_e = (double)blockpos.func_177952_p();
            return true;
         }
      }
   }

   public void func_75249_e() {
      this.field_75267_a.func_70661_as().func_75492_a(this.field_75266_c, this.field_75263_d, this.field_75264_e, this.field_75265_b);
   }

   public boolean func_75253_b() {
      return !this.field_75267_a.func_70661_as().func_75500_f();
   }

   private BlockPos func_188497_a(World p_188497_1_, Entity p_188497_2_, int p_188497_3_, int p_188497_4_) {
      BlockPos blockpos = new BlockPos(p_188497_2_);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = blockpos.func_177958_n();
      int j = blockpos.func_177956_o();
      int k = blockpos.func_177952_p();
      float f = (float)(p_188497_3_ * p_188497_3_ * p_188497_4_ * 2);
      BlockPos blockpos1 = null;

      for(int l = i - p_188497_3_; l <= i + p_188497_3_; ++l) {
         for(int i1 = j - p_188497_4_; i1 <= j + p_188497_4_; ++i1) {
            for(int j1 = k - p_188497_3_; j1 <= k + p_188497_3_; ++j1) {
               blockpos$mutableblockpos.func_181079_c(l, i1, j1);
               IBlockState iblockstate = p_188497_1_.func_180495_p(blockpos$mutableblockpos);
               Block block = iblockstate.func_177230_c();
               if(block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
                  float f1 = (float)((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));
                  if(f1 < f) {
                     f = f1;
                     blockpos1 = new BlockPos(blockpos$mutableblockpos);
                  }
               }
            }
         }
      }

      return blockpos1;
   }
}
