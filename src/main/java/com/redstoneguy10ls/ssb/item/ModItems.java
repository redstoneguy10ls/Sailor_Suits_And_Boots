package com.redstoneguy10ls.ssb.item;

import com.redstoneguy10ls.ssb.Ssb;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Ssb.MOD_ID);

    public static final RegistryObject<Item> BLACK_BRONZE_CROWN = ITEMS.register("black_bronze_crown",
            () -> new ClothesItem(ModArmorMaterials.BLACK_BRONZE_CROWN, ArmorItem.Type.HELMET, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
