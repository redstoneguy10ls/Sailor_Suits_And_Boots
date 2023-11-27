package com.redstoneguy10ls.ssb.item;

import com.redstoneguy10ls.ssb.Ssb;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Ssb.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SSB_TAB = CREATIVE_MODE_TABS.register("ssb_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BLACK_BRONZE_CROWN.get()))
                    .title(Component.translatable("creativetab.ssb_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BLACK_BRONZE_CROWN.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
