package com.redstoneguy10ls.ssb;


import com.mojang.logging.LogUtils;
import com.redstoneguy10ls.ssb.event.ClientEventHandler;
import com.redstoneguy10ls.ssb.item.ModCreativeModTabs;
import com.redstoneguy10ls.ssb.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;


import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static vazkii.patchouli.forge.network.ForgeNetworkHandler.CHANNEL;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ssb.MOD_ID)
public class Ssb {
    public static final String MOD_ID = "ssb";

    public static final String CURIOS_ID = "curios";
    public static final Logger LOGGER = LogUtils.getLogger();


    public Ssb() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ClientEventHandler.init(modEventBus);



        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }



    private static <T> void register(int id, Class<T> cls, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, NetworkEvent.Context> handler) {
        CHANNEL.registerMessage(id, cls, encoder, decoder, (packet, context) -> {
            context.get().setPacketHandled(true);
            handler.accept(packet, context.get());
        });
    }

    private static <T> void register(int id, Class<T> cls, Supplier<T> factory, BiConsumer<T, NetworkEvent.Context> handler) {
        CHANNEL.registerMessage(id, cls, (packet, buffer) -> {
        }, buffer -> factory.get(), (packet, context) -> {
            context.get().setPacketHandled(true);
            handler.accept(packet, context.get());
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {


    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}