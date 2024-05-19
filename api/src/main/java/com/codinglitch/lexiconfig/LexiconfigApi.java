package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.classes.LexiconData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class LexiconfigApi {
    protected static final List<LexiconData> PRE_REGISTERED_LEXICONS = new ArrayList<>();

    public static LexiconfigApi INSTANCE;

    public LexiconfigApi() {
        INSTANCE = this;
    }

    public enum Event {
        RELOAD
    }

    private static Logger LOGGER = LogManager.getLogger(LexiconfigApi.class);
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

    protected abstract void registerLexicon(LexiconData lexicon);
    public static void register(LexiconData lexicon) {
        if (LexiconfigApi.INSTANCE != null) {
            LexiconfigApi.INSTANCE.registerLexicon(lexicon);
            return;
        }

        PRE_REGISTERED_LEXICONS.add(lexicon);
    }

    public abstract Path getConfigPath();

    public abstract void registerListener(Event eventType, Runnable listener);
}