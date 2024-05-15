package com.codinglitch.lexiconfig;

import net.fabricmc.api.ModInitializer;

public class FabricLexiconfig implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Lexiconfig.initialize();
    }
}
