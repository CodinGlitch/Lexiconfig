package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.classes.LexiconData;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public abstract class LexiconfigApi {
    protected static final List<LexiconData> SHELVED_LEXICONS = new ArrayList<>();
    protected static final Map<Consumer<LexiconEvent>, EventType> LISTENERS = new HashMap<>();

    public static final List<Library> LIBRARIES = new ArrayList<>();

    public static LexiconfigApi INSTANCE;

    public LexiconfigApi() {
        INSTANCE = this;
    }

    public abstract void info(Object object, Object... substitutions);
    public abstract void debug(Object object, Object... substitutions);
    public abstract void warn(Object object, Object... substitutions);
    public abstract void error(Object object, Object... substitutions);

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
     * These are the possible locations to store a lexicon at.
     */
    public enum Location {
        COMMON,
        CLIENT,
        SERVER,
        WORLD
    }

    /**
     * These are the possible extensions to save the config as.
     */
    public enum Extension {
        TOML(".toml");

        public final String file;

        Extension(String file) {
            this.file = file;
        }
    }

    /**
     * This method is used to shelve a lexicon to be registered for reloading events, etc. and should be called within the {@code shelveLexicons} method of a {@code LexiconLibrary}.
     * @see Library
     * @see Library#shelveLexicons
     * @param library The library to shelve the lexicon in
     * @param lexicon The lexicon to shelve
     */
    public static void shelveLexicon(Library library, LexiconData lexicon) {
        library.shelve(lexicon);
        SHELVED_LEXICONS.add(lexicon);
        INSTANCE.info("Shelved lexicon {}!", lexicon);
    }

    /**
     * Searches for a library with the matching mod ID.
     * @see Library
     * @see Library#shelveLexicons
     * @param id The id of the mod to search for
     * @return The library, if found
     */
    public static Optional<Library> findLibrary(String id) {
        for (Library library : LIBRARIES) {
            if (id.equals(library.getName())) return Optional.of(library);
        }

        return Optional.empty();
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
    public abstract Path getConfigPath(Location location);
}