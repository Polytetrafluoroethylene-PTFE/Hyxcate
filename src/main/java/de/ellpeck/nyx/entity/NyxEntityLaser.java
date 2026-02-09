package de.ellpeck.nyx.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class NyxEntityLaser extends EntityThrowable {
    private float damage;

    public NyxEntityLaser(World world) {
        super(world);
    }

    public NyxEntityLaser(World world, EntityLivingBase entity, float damageAmount) {
        super(world, entity);
        this.rotationPitch = entity.rotationPitch;
        this.rotationYaw = entity.renderYawOffset;
        this.damage = damageAmount;
    }

    public NyxEntityLaser(final World world, final double x, final double y, final double z) {
        super(world, x, y, z);
    }

    public NyxEntityLaser(final World world, EntityPlayer player, float damageAmount) {
        super(world, player);
        this.rotationPitch = -player.rotationPitch;
        this.rotationYaw = -player.rotationYaw;
        this.damage = damageAmount;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        EntityLivingBase shooter = this.thrower;
        Entity target = result.entityHit;

        if (!this.world.isRemote) {
            if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                if (shooter != null && target instanceof EntityLivingBase && target != shooter && !target.isOnSameTeam(shooter)) {
                    if (target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, shooter).setProjectile(), this.damage)) {
                        target.setFire(1);
                        this.applyEnchantments(shooter, target);
                    }
                }
            } else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.ticksExisted > 70) {
            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }
}
