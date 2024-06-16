package com.codinglitch.lexiconfig;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Lexiconfig.ID)
public class NeoForgeLexiconfig {

    public NeoForgeLexiconfig() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadCompletion);

        Lexiconfig.initialize();
    }

    @SubscribeEvent
    public void loadCompletion(FMLLoadCompleteEvent event) {
        Lexiconfig.postInitialize();
    }
}