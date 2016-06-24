package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;

public final class NBTUtil {
   @Nullable
   public static GameProfile func_152459_a(NBTTagCompound p_152459_0_) {
      String s = null;
      String s1 = null;
      if(p_152459_0_.func_150297_b("Name", 8)) {
         s = p_152459_0_.func_74779_i("Name");
      }

      if(p_152459_0_.func_150297_b("Id", 8)) {
         s1 = p_152459_0_.func_74779_i("Id");
      }

      if(StringUtils.func_151246_b(s) && StringUtils.func_151246_b(s1)) {
         return null;
      } else {
         UUID uuid;
         try {
            uuid = UUID.fromString(s1);
         } catch (Throwable var12) {
            uuid = null;
         }

         GameProfile gameprofile = new GameProfile(uuid, s);
         if(p_152459_0_.func_150297_b("Properties", 10)) {
            NBTTagCompound nbttagcompound = p_152459_0_.func_74775_l("Properties");

            for(String s2 : nbttagcompound.func_150296_c()) {
               NBTTagList nbttaglist = nbttagcompound.func_150295_c(s2, 10);

               for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                  NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
                  String s3 = nbttagcompound1.func_74779_i("Value");
                  if(nbttagcompound1.func_150297_b("Signature", 8)) {
                     gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.func_74779_i("Signature")));
                  } else {
                     gameprofile.getProperties().put(s2, new Property(s2, s3));
                  }
               }
            }
         }

         return gameprofile;
      }
   }

   public static NBTTagCompound func_180708_a(NBTTagCompound p_180708_0_, GameProfile p_180708_1_) {
      if(!StringUtils.func_151246_b(p_180708_1_.getName())) {
         p_180708_0_.func_74778_a("Name", p_180708_1_.getName());
      }

      if(p_180708_1_.getId() != null) {
         p_180708_0_.func_74778_a("Id", p_180708_1_.getId().toString());
      }

      if(!p_180708_1_.getProperties().isEmpty()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();

         for(String s : p_180708_1_.getProperties().keySet()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(Property property : p_180708_1_.getProperties().get(s)) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               nbttagcompound1.func_74778_a("Value", property.getValue());
               if(property.hasSignature()) {
                  nbttagcompound1.func_74778_a("Signature", property.getSignature());
               }

               nbttaglist.func_74742_a(nbttagcompound1);
            }

            nbttagcompound.func_74782_a(s, nbttaglist);
         }

         p_180708_0_.func_74782_a("Properties", nbttagcompound);
      }

      return p_180708_0_;
   }

   @VisibleForTesting
   public static boolean func_181123_a(NBTBase p_181123_0_, NBTBase p_181123_1_, boolean p_181123_2_) {
      if(p_181123_0_ == p_181123_1_) {
         return true;
      } else if(p_181123_0_ == null) {
         return true;
      } else if(p_181123_1_ == null) {
         return false;
      } else if(!p_181123_0_.getClass().equals(p_181123_1_.getClass())) {
         return false;
      } else if(p_181123_0_ instanceof NBTTagCompound) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)p_181123_0_;
         NBTTagCompound nbttagcompound1 = (NBTTagCompound)p_181123_1_;

         for(String s : nbttagcompound.func_150296_c()) {
            NBTBase nbtbase1 = nbttagcompound.func_74781_a(s);
            if(!func_181123_a(nbtbase1, nbttagcompound1.func_74781_a(s), p_181123_2_)) {
               return false;
            }
         }

         return true;
      } else if(p_181123_0_ instanceof NBTTagList && p_181123_2_) {
         NBTTagList nbttaglist = (NBTTagList)p_181123_0_;
         NBTTagList nbttaglist1 = (NBTTagList)p_181123_1_;
         if(nbttaglist.func_74745_c() == 0) {
            return nbttaglist1.func_74745_c() == 0;
         } else {
            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               NBTBase nbtbase = nbttaglist.func_179238_g(i);
               boolean flag = false;

               for(int j = 0; j < nbttaglist1.func_74745_c(); ++j) {
                  if(func_181123_a(nbtbase, nbttaglist1.func_179238_g(j), p_181123_2_)) {
                     flag = true;
                     break;
                  }
               }

               if(!flag) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return p_181123_0_.equals(p_181123_1_);
      }
   }

   public static NBTTagCompound func_186862_a(UUID p_186862_0_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74772_a("M", p_186862_0_.getMostSignificantBits());
      nbttagcompound.func_74772_a("L", p_186862_0_.getLeastSignificantBits());
      return nbttagcompound;
   }

   public static UUID func_186860_b(NBTTagCompound p_186860_0_) {
      return new UUID(p_186860_0_.func_74763_f("M"), p_186860_0_.func_74763_f("L"));
   }

   public static BlockPos func_186861_c(NBTTagCompound p_186861_0_) {
      return new BlockPos(p_186861_0_.func_74762_e("X"), p_186861_0_.func_74762_e("Y"), p_186861_0_.func_74762_e("Z"));
   }

   public static NBTTagCompound func_186859_a(BlockPos p_186859_0_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74768_a("X", p_186859_0_.func_177958_n());
      nbttagcompound.func_74768_a("Y", p_186859_0_.func_177956_o());
      nbttagcompound.func_74768_a("Z", p_186859_0_.func_177952_p());
      return nbttagcompound;
   }

   public static IBlockState func_190008_d(NBTTagCompound p_190008_0_) {
      if(!p_190008_0_.func_150297_b("Name", 8)) {
         return Blocks.field_150350_a.func_176223_P();
      } else {
         Block block = (Block)Block.field_149771_c.func_82594_a(new ResourceLocation(p_190008_0_.func_74779_i("Name")));
         IBlockState iblockstate = block.func_176223_P();
         if(p_190008_0_.func_150297_b("Properties", 10)) {
            NBTTagCompound nbttagcompound = p_190008_0_.func_74775_l("Properties");
            BlockStateContainer blockstatecontainer = block.func_176194_O();

            for(String s : nbttagcompound.func_150296_c()) {
               IProperty<?> iproperty = blockstatecontainer.func_185920_a(s);
               if(iproperty != null) {
                  iblockstate = func_190007_a(iblockstate, iproperty, nbttagcompound.func_74779_i(s));
               }
            }
         }

         return iblockstate;
      }
   }

   private static <T extends Comparable<T>> IBlockState func_190007_a(IBlockState p_190007_0_, IProperty<T> p_190007_1_, String p_190007_2_) {
      return p_190007_0_.func_177226_a(p_190007_1_, (Comparable)p_190007_1_.func_185929_b(p_190007_2_).get());
   }

   public static NBTTagCompound func_190009_a(NBTTagCompound p_190009_0_, IBlockState p_190009_1_) {
      p_190009_0_.func_74778_a("Name", ((ResourceLocation)Block.field_149771_c.func_177774_c(p_190009_1_.func_177230_c())).toString());
      if(!p_190009_1_.func_177228_b().isEmpty()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();

         for(Entry<IProperty<?>, Comparable<?>> entry : p_190009_1_.func_177228_b().entrySet()) {
            IProperty<?> iproperty = (IProperty)entry.getKey();
            nbttagcompound.func_74778_a(iproperty.func_177701_a(), func_190010_a(iproperty, (Comparable)entry.getValue()));
         }

         p_190009_0_.func_74782_a("Properties", nbttagcompound);
      }

      return p_190009_0_;
   }

   private static <T extends Comparable<T>> String func_190010_a(IProperty<T> p_190010_0_, Comparable<?> p_190010_1_) {
      return p_190010_0_.func_177702_a(p_190010_1_);
   }
}
