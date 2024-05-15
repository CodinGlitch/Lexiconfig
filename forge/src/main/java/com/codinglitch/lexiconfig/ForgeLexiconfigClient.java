package com.codinglitch.lexiconfig;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Lexiconfig.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeLexiconfigClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LexiconfigClient.initialize();
    }
}
