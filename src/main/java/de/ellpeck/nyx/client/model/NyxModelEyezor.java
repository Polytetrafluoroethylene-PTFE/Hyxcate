package de.ellpeck.nyx.client.model;

import de.ellpeck.nyx.entity.NyxEntityEyezor;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class NyxModelEyezor extends ModelZombie {
    private final ModelRenderer arms;
    private final ModelRenderer base;

    public NyxModelEyezor() {
        textureWidth = 64;
        textureHeight = 64;

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, 1.0F, 0.0F);
        arms.cubeList.add(new ModelBox(arms, 40, 0, 4.01F, -1.0F, -2.5F, 3, 12, 5, 0.0F, false));
        arms.cubeList.add(new ModelBox(arms, 40, 0, -7.01F, -1.0F, -2.5F, 3, 12, 5, 0.0F, true));
        arms.cubeList.add(new ModelBox(arms, 24, 16, 4.01F, 11.0F, -2.5F, 3, 4, 5, 0.0F, false));
        arms.cubeList.add(new ModelBox(arms, 24, 16, -7.01F, 11.0F, -2.5F, 3, 4, 5, 0.0F, true));

        base = new ModelRenderer(this);
        base.setRotationPoint(0.0F, 24.0F, 0.0F);
        base.cubeList.add(new ModelBox(base, 16, 0, -4.0F, -24.0F, -2.0F, 8, 12, 4, 0.0F, false));
        base.cubeList.add(new ModelBox(base, 40, 17, -4.0F, -24.0F, -2.0F, 8, 12, 4, 0.55F, false));
        base.cubeList.add(new ModelBox(base, 58, -3, 0.0F, -24.0F, 2.0F, 0, 9, 3, 0.0F, false));
        base.cubeList.add(new ModelBox(base, 0, 32, -4.0F, -32.0F, -4.0F, 8, 8, 8, 0.0F, false));
        base.cubeList.add(new ModelBox(base, 0, 48, -4.0F, -32.0F, -4.0F, 8, 8, 8, 0.33F, false));
        base.cubeList.add(new ModelBox(base, 14, 1, -1.0F, -29.0F, -4.0F, 2, 2, 0, 0.1F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        arms.render(f5);
        base.render(f5);
        bipedLeftLeg.render(f5);
        bipedRightLeg.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        boolean flag = entityIn instanceof NyxEntityEyezor && ((NyxEntityEyezor) entityIn).isArmsRaised();
        float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
        this.arms.rotateAngleZ = 0.0F;
        this.arms.rotateAngleY = -(0.1F - f * 0.6F);
        float f2 = -(float) Math.PI / (flag ? 1.5F : 2.25F);
        this.arms.rotateAngleX = f2;
        this.arms.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.arms.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.arms.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
