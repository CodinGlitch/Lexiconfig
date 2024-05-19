package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.platform.Services;
import com.codinglitch.lexiconfig.LexiconfigApi;

import java.nio.file.Path;
import java.util.*;

public class Lexiconfig extends LexiconfigApi {
    public static final LexiconfigApi API = new Lexiconfig();
    public static final String ID = "lexiconfig";

    private static final List<LexiconData> REGISTERED_LEXICONS = new ArrayList<>();
    private static final Map<Event, Runnable> LISTENERS = new HashMap<>();

    public static <T> T loadService(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Lexiconfig.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    @Override
    public void registerLexicon(LexiconData lexicon) {
        REGISTERED_LEXICONS.add(lexicon);

        Lexiconfig.info("Registering lexicon {}", lexicon.getClass().getName());

        lexicon.load();
        lexicon.save();
    }

    @Override
    public void registerListener(Event eventType, Runnable listener) {
        LISTENERS.put(eventType, listener);
    }

    @Override
    public Path getConfigPath() {
        return Services.PLATFORM.getConfigPath();
    }


    public void reload() {
        LISTENERS.forEach((event, runnable) -> {
            if (event == Event.RELOAD) runnable.run();
        });
    }

    public static void initialize() {
        PRE_REGISTERED_LEXICONS.forEach(API::registerLexicon);
    }
}