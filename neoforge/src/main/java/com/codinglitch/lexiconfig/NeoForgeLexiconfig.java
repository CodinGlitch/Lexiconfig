package com.codinglitch.lexiconfig;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@EventBusSubscriber(modid = Lexiconfig.ID)
@Mod(Lexiconfig.ID)
public class NeoForgeLexiconfig {
    
    public NeoForgeLexiconfig() {
        Lexiconfig.initialize();
    }

    @SubscribeEvent
    public static void loadCompletion(FMLLoadCompleteEvent event) {
        Lexiconfig.postInitialize();
    }
}