package de.ellpeck.nyx.client.renderer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.client.model.NyxModelLaser;
import de.ellpeck.nyx.entity.NyxEntityLaser;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxRendererLaser extends Render<NyxEntityLaser> {
    private static final ResourceLocation LASER = new ResourceLocation(Nyx.ID, "textures/entities/laser.png");
    private final NyxModelLaser model = new NyxModelLaser();

    public NyxRendererLaser(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(NyxEntityLaser entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y + 0.1F, (float) z);
        GlStateManager.rotate(entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        bindTexture(LASER);
        this.model.render();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.depthMask(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.model.render();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.getBrightnessForRender() % 65536, entity.getBrightnessForRender() / 65536);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(NyxEntityLaser entity) {
        return LASER;
    }
}
