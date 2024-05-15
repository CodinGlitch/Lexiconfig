package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.classes.LexiconData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Lexiconfig {
    public static final String ID = "lexiconfig";

    public enum Event {
        RELOAD
    }

    private static final List<LexiconData> REGISTERED_LEXICONS = new ArrayList<>();
    private static final Map<Event, Runnable> LISTENERS = new HashMap<>();

    public static <T> T loadService(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Lexiconfig.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    // -- Logging -- \\
    private static Logger LOGGER = LogManager.getLogger(ID);
    public static void info(Object object, Object... substitutions) {
        LOGGER.info(String.valueOf(object), substitutions);
    }
    public static void debug(Object object, Object... substitutions) {
        LOGGER.debug(String.valueOf(object), substitutions);
    }
    public static void warn(Object object, Object... substitutions) {
            LOGGER.warn(String.valueOf(object), substitutions);
    }
    public static void error(Object object, Object... substitutions) {
        LOGGER.error(String.valueOf(object), substitutions);
    }


    public static void register(LexiconData lexicon) {
        REGISTERED_LEXICONS.add(lexicon);

        lexicon.load();
        lexicon.save();
    }

    public static void registerListener(Event eventType, Runnable listener) {
        LISTENERS.put(eventType, listener);
    }

    public static void reload() {
        LISTENERS.forEach((event, runnable) -> {
            if (event == Event.RELOAD) runnable.run();
        });
    }

    public static void initialize() {

    }
}