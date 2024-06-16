package com.codinglitch.lexiconfig.platform;

import com.codinglitch.lexiconfig.LexiconfigApi;
import com.codinglitch.lexiconfig.Library;
import com.codinglitch.lexiconfig.Lexiconfig;
import com.codinglitch.lexiconfig.platform.services.PlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public void shelveLexicons() {
        FabricLoader.getInstance().invokeEntrypoints(Lexiconfig.ID, Library.class, library -> {
            LexiconfigApi.LIBRARIES.add(library);
            library.shelveLexicons();
        });
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
