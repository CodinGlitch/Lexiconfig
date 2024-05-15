package com.codinglitch.lexiconfig;

import net.fabricmc.api.ClientModInitializer;

public class FabricLexiconfigClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LexiconfigClient.initialize();
    }
}
