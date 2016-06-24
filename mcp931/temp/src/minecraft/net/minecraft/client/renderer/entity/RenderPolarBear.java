package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.util.ResourceLocation;

public class RenderPolarBear extends RenderLiving<EntityPolarBear> {
   private static final ResourceLocation field_190090_a = new ResourceLocation("textures/entity/bear/polarbear.png");

   public RenderPolarBear(RenderManager p_i47132_1_, ModelBase p_i47132_2_, float p_i47132_3_) {
      super(p_i47132_1_, p_i47132_2_, p_i47132_3_);
   }

   protected ResourceLocation func_110775_a(EntityPolarBear p_110775_1_) {
      return field_190090_a;
   }

   public void func_76986_a(EntityPolarBear p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected void func_77041_b(EntityPolarBear p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(1.2F, 1.2F, 1.2F);
      super.func_77041_b(p_77041_1_, p_77041_2_);
   }
}
