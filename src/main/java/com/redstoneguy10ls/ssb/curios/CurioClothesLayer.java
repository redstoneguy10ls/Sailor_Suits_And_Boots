package com.redstoneguy10ls.ssb.curios;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.redstoneguy10ls.ssb.item.ClothesItem;
import com.redstoneguy10ls.ssb.item.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public class CurioClothesLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M>
{
    public final A innerModel;
    public final A outerModel;
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();

    public CurioClothesLayer(RenderLayerParent<T, M> pRenderer, A innerModel, A outerModel) {
        super(pRenderer);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    }

    public void render(ItemStack itemStack, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer,
                       int pPackedLight, @NotNull T player) {
        this.renderArmorPiece2(itemStack, pMatrixStack, pBuffer, player, ArmorItem.Type.CHESTPLATE, pPackedLight, this.getArmorModel(ArmorItem.Type.CHESTPLATE));
        this.renderArmorPiece2(itemStack, pMatrixStack, pBuffer, player, ArmorItem.Type.LEGGINGS, pPackedLight, this.getArmorModel(ArmorItem.Type.LEGGINGS));
        this.renderArmorPiece2(itemStack, pMatrixStack, pBuffer, player, ArmorItem.Type.BOOTS, pPackedLight, this.getArmorModel(ArmorItem.Type.BOOTS));
        this.renderArmorPiece2(itemStack, pMatrixStack, pBuffer, player, ArmorItem.Type.HELMET, pPackedLight, this.getArmorModel(ArmorItem.Type.HELMET));
    }
    private A getArmorModel(ArmorItem.Type slot) {
        return this.usesInnerModel(slot) ? this.innerModel : this.outerModel;
    }
    private boolean usesInnerModel(ArmorItem.Type pSlot) {
        return pSlot.equals(ArmorItem.Type.LEGGINGS);
    }

    private void renderArmorPiece2(ItemStack itemstack, PoseStack poseStack, MultiBufferSource bufferSource,
                                   T entity, ArmorItem.Type armorType, int light, A playerModel) {
        if(itemstack.getItem() instanceof ClothesItem clothesItem){
            if(clothesItem.getEquivalentSlot().equals(armorType)) {
                this.getParentModel().copyPropertiesTo(playerModel);
                this.setPartVisibility(playerModel, armorType.getSlot());
                Model model = this.getArmorModelHook(entity, itemstack, armorType.getSlot(), playerModel);
                boolean flag1 = itemstack.hasFoil();


                this.renderModel(poseStack, bufferSource, light, flag1, model, this.getArmorResource2(entity, itemstack, armorType, null));
            }
        }

    }

    public ResourceLocation getArmorResource2(net.minecraft.world.entity.Entity entity,
                                              ItemStack stack, ArmorItem.Type slot, @Nullable String type) {
        ClothesItem item = (ClothesItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png",
                domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
        ForgeHooksClient.getArmorTexture(entity, stack, s1, slot.getSlot(), type); // TODO jdk remove?
        ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
        }

        return resourcelocation;
    }


    public void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                            boolean hasFoil, Model model, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(armorResource), false, hasFoil);
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    protected void setPartVisibility(A pModel, EquipmentSlot pSlot) {
        pModel.setAllVisible(false);
        switch (pSlot) {
            case HEAD -> {
                pModel.head.visible = true;
                pModel.hat.visible = true;
            }
            case CHEST -> {
                pModel.body.visible = true;
                pModel.rightArm.visible = true;
                pModel.leftArm.visible = true;
            }
            case LEGS -> {
                pModel.body.visible = true;
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
            }
            case FEET -> {
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
            }
        }
    }
    protected Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
        return ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
    }


}
