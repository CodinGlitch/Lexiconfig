package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.events.RevisionEvent;
import com.codinglitch.lexiconfig.events.CatalogEvent;
import com.codinglitch.lexiconfig.platform.Services;

import java.nio.file.Path;
import java.util.*;

public class Lexiconfig extends LexiconfigApi {
    public static final Lexiconfig API = new Lexiconfig();
    public static final String ID = "lexiconfig";

    public static <T> T loadService(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Lexiconfig.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    @Override
    public Path getConfigPath(Location location) {
        return Services.PLATFORM.getConfigPath();
    }

    public static void revise() {
        info("Starting lexicon revision!");
        API.callEvent(EventType.PRE_REVISION, new RevisionEvent());

        for (LexiconData lexicon : SHELVED_LEXICONS) {
            API.callEvent(EventType.PRE_LEXICON_REVISION, new RevisionEvent.Lexicon(lexicon));

            debug("Revising lexicon {}..", lexicon.getName());
            lexicon.load();
            lexicon.save();

            API.callEvent(EventType.POST_LEXICON_REVISION, new RevisionEvent.Lexicon(lexicon));
        }

        API.callEvent(EventType.POST_REVISION, new RevisionEvent());
        info("Finished lexicon revision!");
    }

    public static void publish() {
        info("Starting lexicon publishing!");
        API.callEvent(EventType.PRE_REVISION, new RevisionEvent());

        for (LexiconData lexicon : SHELVED_LEXICONS) {
            API.callEvent(EventType.PRE_LEXICON_REVISION, new RevisionEvent.Lexicon(lexicon));

            debug("Publishing lexicon {}..", lexicon.getName());
            lexicon.save();
            lexicon.load();

            API.callEvent(EventType.POST_LEXICON_REVISION, new RevisionEvent.Lexicon(lexicon));
        }

        API.callEvent(EventType.POST_REVISION, new RevisionEvent());
        info("Finished lexicon publishing!");
    }

    static {
        info("Beginning lexicon shelving!");
        Services.PLATFORM.shelveLexicons(); // static initializer since initialize sometimes fires too late
    }

    public static void initialize() {

    }

    public static void postInitialize() {
        API.callEvent(EventType.PRE_CATALOG, new CatalogEvent());

        for (LexiconData lexicon : SHELVED_LEXICONS) {
            info("Cataloging lexicon {}!", lexicon);
            lexicon.catalog();

            lexicon.load();
            lexicon.save();
        }

        API.callEvent(EventType.POST_CATALOG, new CatalogEvent());
        info("Lexiconfig cataloging completed!");
    }
}