
package com.redstoneguy10ls.ssb.event;

import com.redstoneguy10ls.ssb.curios.ClothesCurioRenderer;
import com.redstoneguy10ls.ssb.item.ModItems;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientEventHandler {

    public static void init(IEventBus bus) {
        bus.addListener(ClientEventHandler::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ModItems.BLACK_BRONZE_CROWN.get(), () -> new ClothesCurioRenderer());
    }
}
