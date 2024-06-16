package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.classes.LexiconData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class LexiconfigApi {
    protected static final List<LexiconData> SHELVED_LEXICONS = new ArrayList<>();
    protected static final Map<Consumer<LexiconEvent>, EventType> LISTENERS = new HashMap<>();

    public static final List<Library> LIBRARIES = new ArrayList<>();

    public static LexiconfigApi INSTANCE;

    public LexiconfigApi() {
        INSTANCE = this;
    }

    /**
     * Do not use these.
     */
    private static Logger LOGGER = LogManager.getLogger("Lexiconfig");
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

    /**
     * These are all the available event types for use inside the {@code registerListener} method.
     * @see com.codinglitch.lexiconfig.LexiconfigApi#registerListener(EventType, Consumer)
     */
    public enum EventType {
        PRE_CATALOG,
        POST_CATALOG,

        PRE_REVISION,
        POST_REVISION,
        PRE_LEXICON_REVISION,
        POST_LEXICON_REVISION
    }

    /**
     * This method is used to shelve a lexicon to be registered for reloading events, etc. and should be called within the {@code shelveLexicons} method of a {@code LexiconLibrary}.
     * @see Library
     * @see Library#shelveLexicons
     * @param lexicon The lexicon to shelve
     */
    public static void shelveLexicon(LexiconData lexicon) {
        SHELVED_LEXICONS.add(lexicon);
        info("Shelved lexicon {}!", lexicon);
    }

    /**
     * This used to register a listener of a certain type, which is fired in various parts of the lifecycle.
     * @see com.codinglitch.lexiconfig.LexiconfigApi.EventType
     * @see com.codinglitch.lexiconfig.events
     */
    public static <E extends LexiconEvent> void registerListener(EventType eventType, Consumer<E> listener) {
        LISTENERS.put((Consumer<LexiconEvent>) listener, eventType);
    }

    protected void callEvent(EventType eventType, LexiconEvent event) {
        LISTENERS.forEach((runnable, type) -> {
            if (type == eventType) runnable.accept(event);
        });
    }

    /**
     * This is used to retrieve the configuration folder path, dependent on the modloader.
     * @return The path of the config folder
     */
    public abstract Path getConfigPath();
}