package net.minecraft.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBone extends BlockRotatedPillar {
   public BlockBone() {
      super(Material.field_151576_e);
      this.func_149647_a(CreativeTabs.field_78030_b);
      this.func_149711_c(2.0F);
      this.func_149672_a(SoundType.field_185851_d);
   }
}
