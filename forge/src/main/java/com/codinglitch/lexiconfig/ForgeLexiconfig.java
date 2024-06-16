package com.codinglitch.lexiconfig;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Lexiconfig.ID)
public class ForgeLexiconfig {
    
    public ForgeLexiconfig() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadCompletion);

        Lexiconfig.initialize();
    }

    @SubscribeEvent
    public void loadCompletion(FMLLoadCompleteEvent event) {
        Lexiconfig.postInitialize();
    }
}