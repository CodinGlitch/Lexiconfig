package com.codinglitch.lexiconfig;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Lexiconfig.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoForgeLexiconfigClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LexiconfigClient.initialize();
    }
}
