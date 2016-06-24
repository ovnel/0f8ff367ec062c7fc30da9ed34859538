package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityZombie extends EntityMob {
   protected static final IAttribute field_110186_bp = (new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).func_111117_a("Spawn Reinforcements Chance");
   private static final UUID field_110187_bq = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier field_110188_br = new AttributeModifier(field_110187_bq, "Baby speed boost", 0.5D, 1);
   private static final DataParameter<Boolean> field_184737_bv = EntityDataManager.<Boolean>func_187226_a(EntityZombie.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184738_bw = EntityDataManager.<Integer>func_187226_a(EntityZombie.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184739_bx = EntityDataManager.<Boolean>func_187226_a(EntityZombie.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_184740_by = EntityDataManager.<Boolean>func_187226_a(EntityZombie.class, DataSerializers.field_187198_h);
   private final EntityAIBreakDoor field_146075_bs = new EntityAIBreakDoor(this);
   private int field_82234_d;
   private boolean field_146076_bu;
   private float field_146074_bv = -1.0F;
   private float field_146073_bw;

   public EntityZombie(World p_i1745_1_) {
      super(p_i1745_1_);
      this.func_70105_a(0.6F, 1.95F);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIZombieAttack(this, 1.0D, false));
      this.field_70714_bg.func_75776_a(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.func_175456_n();
   }

   protected void func_175456_n() {
      this.field_70714_bg.func_75776_a(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityPigZombie.class}));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(35.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
      this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(2.0D);
      this.func_110140_aT().func_111150_b(field_110186_bp).func_111128_a(this.field_70146_Z.nextDouble() * 0.10000000149011612D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.func_184212_Q().func_187214_a(field_184737_bv, Boolean.valueOf(false));
      this.func_184212_Q().func_187214_a(field_184738_bw, Integer.valueOf(0));
      this.func_184212_Q().func_187214_a(field_184739_bx, Boolean.valueOf(false));
      this.func_184212_Q().func_187214_a(field_184740_by, Boolean.valueOf(false));
   }

   public void func_184733_a(boolean p_184733_1_) {
      this.func_184212_Q().func_187227_b(field_184740_by, Boolean.valueOf(p_184733_1_));
   }

   public boolean func_184734_db() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184740_by)).booleanValue();
   }

   public boolean func_146072_bX() {
      return this.field_146076_bu;
   }

   public void func_146070_a(boolean p_146070_1_) {
      if(this.field_146076_bu != p_146070_1_) {
         this.field_146076_bu = p_146070_1_;
         ((PathNavigateGround)this.func_70661_as()).func_179688_b(p_146070_1_);
         if(p_146070_1_) {
            this.field_70714_bg.func_75776_a(1, this.field_146075_bs);
         } else {
            this.field_70714_bg.func_85156_a(this.field_146075_bs);
         }
      }

   }

   public boolean func_70631_g_() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184737_bv)).booleanValue();
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      if(this.func_70631_g_()) {
         this.field_70728_aV = (int)((float)this.field_70728_aV * 2.5F);
      }

      return super.func_70693_a(p_70693_1_);
   }

   public void func_82227_f(boolean p_82227_1_) {
      this.func_184212_Q().func_187227_b(field_184737_bv, Boolean.valueOf(p_82227_1_));
      if(this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
         iattributeinstance.func_111124_b(field_110188_br);
         if(p_82227_1_) {
            iattributeinstance.func_111121_a(field_110188_br);
         }
      }

      this.func_146071_k(p_82227_1_);
   }

   public ZombieType func_189777_di() {
      return ZombieType.func_190146_a(((Integer)this.func_184212_Q().func_187225_a(field_184738_bw)).intValue());
   }

   public boolean func_82231_m() {
      return this.func_189777_di().func_190154_b();
   }

   public int func_184736_de() {
      return this.func_189777_di().func_190148_c();
   }

   public void func_189778_a(ZombieType p_189778_1_) {
      this.func_184212_Q().func_187227_b(field_184738_bw, Integer.valueOf(p_189778_1_.func_190150_a()));
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if(field_184737_bv.equals(p_184206_1_)) {
         this.func_146071_k(this.func_70631_g_());
      }

      super.func_184206_a(p_184206_1_);
   }

   public void func_70636_d() {
      if(this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K && !this.func_70631_g_() && this.func_189777_di().func_190155_e()) {
         float f = this.func_70013_c(1.0F);
         BlockPos blockpos = this.func_184187_bx() instanceof EntityBoat?(new BlockPos(this.field_70165_t, (double)Math.round(this.field_70163_u), this.field_70161_v)).func_177984_a():new BlockPos(this.field_70165_t, (double)Math.round(this.field_70163_u), this.field_70161_v);
         if(f > 0.5F && this.field_70146_Z.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.field_70170_p.func_175678_i(blockpos)) {
            boolean flag = true;
            ItemStack itemstack = this.func_184582_a(EntityEquipmentSlot.HEAD);
            if(itemstack != null) {
               if(itemstack.func_77984_f()) {
                  itemstack.func_77964_b(itemstack.func_77952_i() + this.field_70146_Z.nextInt(2));
                  if(itemstack.func_77952_i() >= itemstack.func_77958_k()) {
                     this.func_70669_a(itemstack);
                     this.func_184201_a(EntityEquipmentSlot.HEAD, (ItemStack)null);
                  }
               }

               flag = false;
            }

            if(flag) {
               this.func_70015_d(8);
            }
         }
      }

      super.func_70636_d();
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if(super.func_70097_a(p_70097_1_, p_70097_2_)) {
         EntityLivingBase entitylivingbase = this.func_70638_az();
         if(entitylivingbase == null && p_70097_1_.func_76346_g() instanceof EntityLivingBase) {
            entitylivingbase = (EntityLivingBase)p_70097_1_.func_76346_g();
         }

         if(entitylivingbase != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && (double)this.field_70146_Z.nextFloat() < this.func_110148_a(field_110186_bp).func_111126_e() && this.field_70170_p.func_82736_K().func_82766_b("doMobSpawning")) {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            EntityZombie entityzombie = new EntityZombie(this.field_70170_p);

            for(int l = 0; l < 50; ++l) {
               int i1 = i + MathHelper.func_76136_a(this.field_70146_Z, 7, 40) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
               int j1 = j + MathHelper.func_76136_a(this.field_70146_Z, 7, 40) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
               int k1 = k + MathHelper.func_76136_a(this.field_70146_Z, 7, 40) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
               if(this.field_70170_p.func_180495_p(new BlockPos(i1, j1 - 1, k1)).func_185896_q() && this.field_70170_p.func_175671_l(new BlockPos(i1, j1, k1)) < 10) {
                  entityzombie.func_70107_b((double)i1, (double)j1, (double)k1);
                  if(!this.field_70170_p.func_175636_b((double)i1, (double)j1, (double)k1, 7.0D) && this.field_70170_p.func_72917_a(entityzombie.func_174813_aQ(), entityzombie) && this.field_70170_p.func_184144_a(entityzombie, entityzombie.func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(entityzombie.func_174813_aQ())) {
                     this.field_70170_p.func_72838_d(entityzombie);
                     entityzombie.func_70624_b(entitylivingbase);
                     entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entityzombie)), (IEntityLivingData)null);
                     this.func_110148_a(field_110186_bp).func_111121_a(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                     entityzombie.func_110148_a(field_110186_bp).func_111121_a(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                     break;
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void func_70071_h_() {
      if(!this.field_70170_p.field_72995_K && this.func_82230_o()) {
         int i = this.func_82233_q();
         this.field_82234_d -= i;
         if(this.field_82234_d <= 0) {
            this.func_82232_p();
         }
      }

      super.func_70071_h_();
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      boolean flag = super.func_70652_k(p_70652_1_);
      if(flag) {
         float f = this.field_70170_p.func_175649_E(new BlockPos(this)).func_180168_b();
         if(this.func_184614_ca() == null) {
            if(this.func_70027_ad() && this.field_70146_Z.nextFloat() < f * 0.3F) {
               p_70652_1_.func_70015_d(2 * (int)f);
            }

            if(this.func_189777_di() == ZombieType.HUSK && p_70652_1_ instanceof EntityLivingBase) {
               ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76438_s, 140 * (int)f));
            }
         }
      }

      return flag;
   }

   protected SoundEvent func_184639_G() {
      return this.func_189777_di().func_190153_f();
   }

   protected SoundEvent func_184601_bQ() {
      return this.func_189777_di().func_190152_g();
   }

   protected SoundEvent func_184615_bR() {
      return this.func_189777_di().func_190151_h();
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      SoundEvent soundevent = this.func_189777_di().func_190149_i();
      this.func_184185_a(soundevent, 0.15F, 1.0F);
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186383_ah;
   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      super.func_180481_a(p_180481_1_);
      if(this.field_70146_Z.nextFloat() < (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD?0.05F:0.01F)) {
         int i = this.field_70146_Z.nextInt(3);
         if(i == 0) {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151040_l));
         } else {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151037_a));
         }
      }

   }

   public static void func_189779_d(DataFixer p_189779_0_) {
      EntityLiving.func_189752_a(p_189779_0_, "Zombie");
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      if(this.func_70631_g_()) {
         p_70014_1_.func_74757_a("IsBaby", true);
      }

      p_70014_1_.func_74768_a("ZombieType", this.func_189777_di().func_190150_a());
      p_70014_1_.func_74768_a("ConversionTime", this.func_82230_o()?this.field_82234_d:-1);
      p_70014_1_.func_74757_a("CanBreakDoors", this.func_146072_bX());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if(p_70037_1_.func_74767_n("IsBaby")) {
         this.func_82227_f(true);
      }

      if(p_70037_1_.func_74767_n("IsVillager")) {
         if(p_70037_1_.func_150297_b("VillagerProfession", 99)) {
            this.func_189778_a(ZombieType.func_190146_a(p_70037_1_.func_74762_e("VillagerProfession") + 1));
         } else {
            this.func_189778_a(ZombieType.func_190146_a(this.field_70170_p.field_73012_v.nextInt(5) + 1));
         }
      }

      if(p_70037_1_.func_74764_b("ZombieType")) {
         this.func_189778_a(ZombieType.func_190146_a(p_70037_1_.func_74762_e("ZombieType")));
      }

      if(p_70037_1_.func_150297_b("ConversionTime", 99) && p_70037_1_.func_74762_e("ConversionTime") > -1) {
         this.func_82228_a(p_70037_1_.func_74762_e("ConversionTime"));
      }

      this.func_146070_a(p_70037_1_.func_74767_n("CanBreakDoors"));
   }

   public void func_70074_a(EntityLivingBase p_70074_1_) {
      super.func_70074_a(p_70074_1_);
      if((this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) && p_70074_1_ instanceof EntityVillager) {
         if(this.field_70170_p.func_175659_aa() != EnumDifficulty.HARD && this.field_70146_Z.nextBoolean()) {
            return;
         }

         EntityVillager entityvillager = (EntityVillager)p_70074_1_;
         EntityZombie entityzombie = new EntityZombie(this.field_70170_p);
         entityzombie.func_82149_j(p_70074_1_);
         this.field_70170_p.func_72900_e(p_70074_1_);
         entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entityzombie)), new EntityZombie.GroupData(false, true));
         entityzombie.func_189778_a(ZombieType.func_190146_a(entityvillager.func_70946_n() + 1));
         entityzombie.func_82227_f(p_70074_1_.func_70631_g_());
         entityzombie.func_94061_f(entityvillager.func_175446_cd());
         if(entityvillager.func_145818_k_()) {
            entityzombie.func_96094_a(entityvillager.func_95999_t());
            entityzombie.func_174805_g(entityvillager.func_174833_aM());
         }

         this.field_70170_p.func_72838_d(entityzombie);
         this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
      }

   }

   public float func_70047_e() {
      float f = 1.74F;
      if(this.func_70631_g_()) {
         f = (float)((double)f - 0.81D);
      }

      return f;
   }

   protected boolean func_175448_a(ItemStack p_175448_1_) {
      return p_175448_1_.func_77973_b() == Items.field_151110_aK && this.func_70631_g_() && this.func_184218_aH()?false:super.func_175448_a(p_175448_1_);
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      float f = p_180482_1_.func_180170_c();
      this.func_98053_h(this.field_70146_Z.nextFloat() < 0.55F * f);
      if(p_180482_2_ == null) {
         p_180482_2_ = new EntityZombie.GroupData(this.field_70170_p.field_73012_v.nextFloat() < 0.05F, this.field_70170_p.field_73012_v.nextFloat() < 0.05F);
      }

      if(p_180482_2_ instanceof EntityZombie.GroupData) {
         EntityZombie.GroupData entityzombie$groupdata = (EntityZombie.GroupData)p_180482_2_;
         boolean flag = false;
         Biome biome = this.field_70170_p.func_180494_b(new BlockPos(this));
         if(biome instanceof BiomeDesert && this.field_70170_p.func_175678_i(new BlockPos(this)) && this.field_70146_Z.nextInt(5) != 0) {
            this.func_189778_a(ZombieType.HUSK);
            flag = true;
         }

         if(!flag && entityzombie$groupdata.field_142046_b) {
            this.func_189778_a(ZombieType.func_190146_a(this.field_70146_Z.nextInt(5) + 1));
         }

         if(entityzombie$groupdata.field_142048_a) {
            this.func_82227_f(true);
            if((double)this.field_70170_p.field_73012_v.nextFloat() < 0.05D) {
               List<EntityChicken> list = this.field_70170_p.<EntityChicken>func_175647_a(EntityChicken.class, this.func_174813_aQ().func_72314_b(5.0D, 3.0D, 5.0D), EntitySelectors.field_152785_b);
               if(!list.isEmpty()) {
                  EntityChicken entitychicken = (EntityChicken)list.get(0);
                  entitychicken.func_152117_i(true);
                  this.func_184220_m(entitychicken);
               }
            } else if((double)this.field_70170_p.field_73012_v.nextFloat() < 0.05D) {
               EntityChicken entitychicken1 = new EntityChicken(this.field_70170_p);
               entitychicken1.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
               entitychicken1.func_180482_a(p_180482_1_, (IEntityLivingData)null);
               entitychicken1.func_152117_i(true);
               this.field_70170_p.func_72838_d(entitychicken1);
               this.func_184220_m(entitychicken1);
            }
         }
      }

      this.func_146070_a(this.field_70146_Z.nextFloat() < f * 0.1F);
      this.func_180481_a(p_180482_1_);
      this.func_180483_b(p_180482_1_);
      if(this.func_184582_a(EntityEquipmentSlot.HEAD) == null) {
         Calendar calendar = this.field_70170_p.func_83015_S();
         if(calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
            this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(this.field_70146_Z.nextFloat() < 0.1F?Blocks.field_150428_aP:Blocks.field_150423_aK));
            this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
         }
      }

      this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111121_a(new AttributeModifier("Random spawn bonus", this.field_70146_Z.nextDouble() * 0.05000000074505806D, 0));
      double d0 = this.field_70146_Z.nextDouble() * 1.5D * (double)f;
      if(d0 > 1.0D) {
         this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
      }

      if(this.field_70146_Z.nextFloat() < f * 0.05F) {
         this.func_110148_a(field_110186_bp).func_111121_a(new AttributeModifier("Leader zombie bonus", this.field_70146_Z.nextDouble() * 0.25D + 0.5D, 0));
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111121_a(new AttributeModifier("Leader zombie bonus", this.field_70146_Z.nextDouble() * 3.0D + 1.0D, 2));
         this.func_146070_a(true);
      }

      return p_180482_2_;
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_, @Nullable ItemStack p_184645_3_) {
      if(p_184645_3_ != null && p_184645_3_.func_77973_b() == Items.field_151153_ao && p_184645_3_.func_77960_j() == 0 && this.func_82231_m() && this.func_70644_a(MobEffects.field_76437_t)) {
         if(!p_184645_1_.field_71075_bZ.field_75098_d) {
            --p_184645_3_.field_77994_a;
         }

         if(!this.field_70170_p.field_72995_K) {
            this.func_82228_a(this.field_70146_Z.nextInt(2401) + 3600);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void func_82228_a(int p_82228_1_) {
      this.field_82234_d = p_82228_1_;
      this.func_184212_Q().func_187227_b(field_184739_bx, Boolean.valueOf(true));
      this.func_184589_d(MobEffects.field_76437_t);
      this.func_70690_d(new PotionEffect(MobEffects.field_76420_g, p_82228_1_, Math.min(this.field_70170_p.func_175659_aa().func_151525_a() - 1, 0)));
      this.field_70170_p.func_72960_a(this, (byte)16);
   }

   public void func_70103_a(byte p_70103_1_) {
      if(p_70103_1_ == 16) {
         if(!this.func_174814_R()) {
            this.field_70170_p.func_184134_a(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D, SoundEvents.field_187942_hp, this.func_184176_by(), 1.0F + this.field_70146_Z.nextFloat(), this.field_70146_Z.nextFloat() * 0.7F + 0.3F, false);
         }
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   protected boolean func_70692_ba() {
      return !this.func_82230_o();
   }

   public boolean func_82230_o() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184739_bx)).booleanValue();
   }

   protected void func_82232_p() {
      EntityVillager entityvillager = new EntityVillager(this.field_70170_p);
      entityvillager.func_82149_j(this);
      entityvillager.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entityvillager)), (IEntityLivingData)null);
      entityvillager.func_82187_q();
      if(this.func_70631_g_()) {
         entityvillager.func_70873_a(-24000);
      }

      this.field_70170_p.func_72900_e(this);
      entityvillager.func_94061_f(this.func_175446_cd());
      entityvillager.func_70938_b(this.func_184736_de());
      if(this.func_145818_k_()) {
         entityvillager.func_96094_a(this.func_95999_t());
         entityvillager.func_174805_g(this.func_174833_aM());
      }

      this.field_70170_p.func_72838_d(entityvillager);
      entityvillager.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200, 0));
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1027, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
   }

   protected int func_82233_q() {
      int i = 1;
      if(this.field_70146_Z.nextFloat() < 0.01F) {
         int j = 0;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int k = (int)this.field_70165_t - 4; k < (int)this.field_70165_t + 4 && j < 14; ++k) {
            for(int l = (int)this.field_70163_u - 4; l < (int)this.field_70163_u + 4 && j < 14; ++l) {
               for(int i1 = (int)this.field_70161_v - 4; i1 < (int)this.field_70161_v + 4 && j < 14; ++i1) {
                  Block block = this.field_70170_p.func_180495_p(blockpos$mutableblockpos.func_181079_c(k, l, i1)).func_177230_c();
                  if(block == Blocks.field_150411_aY || block == Blocks.field_150324_C) {
                     if(this.field_70146_Z.nextFloat() < 0.3F) {
                        ++i;
                     }

                     ++j;
                  }
               }
            }
         }
      }

      return i;
   }

   public void func_146071_k(boolean p_146071_1_) {
      this.func_146069_a(p_146071_1_?0.5F:1.0F);
   }

   protected final void func_70105_a(float p_70105_1_, float p_70105_2_) {
      boolean flag = this.field_146074_bv > 0.0F && this.field_146073_bw > 0.0F;
      this.field_146074_bv = p_70105_1_;
      this.field_146073_bw = p_70105_2_;
      if(!flag) {
         this.func_146069_a(1.0F);
      }

   }

   protected final void func_146069_a(float p_146069_1_) {
      super.func_70105_a(this.field_146074_bv * p_146069_1_, this.field_146073_bw * p_146069_1_);
   }

   public double func_70033_W() {
      return this.func_70631_g_()?0.0D:-0.35D;
   }

   public void func_70645_a(DamageSource p_70645_1_) {
      super.func_70645_a(p_70645_1_);
      if(p_70645_1_.func_76346_g() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)p_70645_1_.func_76346_g()).func_70830_n() && ((EntityCreeper)p_70645_1_.func_76346_g()).func_70650_aV()) {
         ((EntityCreeper)p_70645_1_.func_76346_g()).func_175493_co();
         this.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 2), 0.0F);
      }

   }

   public String func_70005_c_() {
      return this.func_145818_k_()?this.func_95999_t():this.func_189777_di().func_190145_d().func_150260_c();
   }

   class GroupData implements IEntityLivingData {
      public boolean field_142048_a;
      public boolean field_142046_b;

      private GroupData(boolean p_i2348_2_, boolean p_i2348_3_) {
         this.field_142048_a = p_i2348_2_;
         this.field_142046_b = p_i2348_3_;
      }
   }
}
