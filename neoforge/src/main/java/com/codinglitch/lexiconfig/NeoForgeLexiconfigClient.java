package com.codinglitch.lexiconfig;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Lexiconfig.ID)
public class NeoForgeLexiconfigClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LexiconfigClient.initialize();
    }
}
