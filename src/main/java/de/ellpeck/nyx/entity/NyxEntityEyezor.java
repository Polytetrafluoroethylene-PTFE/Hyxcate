package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.init.NyxLootTables;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class NyxEntityEyezor extends EntityZombie implements IRangedAttackMob {
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(NyxEntityEyezor.class, DataSerializers.VARINT);

    public NyxEntityEyezor(World world) {
        super(world);
        this.isImmuneToFire = true;
        this.dataManager.set(TYPE, world.rand.nextInt(4) + 1);
    }

    public NyxEntityEyezor(World world, int type) {
        super(world);
        this.isImmuneToFire = true;
        this.dataManager.set(TYPE, type);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, 1);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        // TODO: Turn into unique hybrid AI
        this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.0D, 30, 16.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        return NyxLootTables.EYEZOR;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("type", this.dataManager.get(TYPE));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(TYPE, compound.getInteger("type"));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        double d0 = target.posY + (double) target.getEyeHeight() - 2.0D;
        double d1 = target.posX + target.motionX - this.posX;
        double d2 = d0 - this.posY;
        double d3 = target.posZ + target.motionZ - this.posZ;
        NyxEntityLaser laser = new NyxEntityLaser(this.world, this, 1.0F);
        laser.shoot(d1, d2, d3, 1.0F, 1.0F);
        this.world.playSound(null, this.posX, this.posY, this.posZ, NyxSoundEvents.RANDOM_LASER.getSoundEvent(), SoundCategory.HOSTILE, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.spawnEntity(laser);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
