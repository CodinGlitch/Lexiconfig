package com.codinglitch.lexiconfig.platform;

import com.codinglitch.lexiconfig.LexiconfigApi;
import com.codinglitch.lexiconfig.Library;
import com.codinglitch.lexiconfig.annotations.LexiconLibrary;
import com.codinglitch.lexiconfig.platform.services.PlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements PlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public Path getConfigPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public void shelveLexicons() {
        ModList.get().getAllScanData().forEach(data -> {
            data.getAnnotations().forEach(annotation -> {
                if (annotation.annotationType().getClassName().equals(LexiconLibrary.class.getName())) {
                    try {
                        Class<Library> clazz = (Class<Library>) Class.forName(annotation.memberName());
                        Library library = clazz.getDeclaredConstructor().newInstance();
                        LexiconfigApi.LIBRARIES.add(library);

                        library.shelveLexicons();
                    } catch (Exception e) {
                        LexiconfigApi.warn("Something went wrong while shelving lexicons! {}", e);
                    }
                }
            });
        });
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }
}