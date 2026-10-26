package com.codinglitch.lexiconfig;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FabricLexiconfig implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Lexiconfig.initialize();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricLexiconfig.onPostInitialize());
    }

    public static void onPostInitialize() {
        Lexiconfig.postInitialize();
    }
}
