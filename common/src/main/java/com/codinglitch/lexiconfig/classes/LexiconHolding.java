package com.codinglitch.lexiconfig.classes;

import com.codinglitch.lexiconfig.Lexiconfig;

import javax.annotation.Nullable;

public class LexiconHolding {
    @Nullable
    public LexiconPageData getPage(String name) {
        return (LexiconPageData) getEntry(name);
    }

    @Nullable
    public Object getEntry(String name) {
        try {
            return this.getClass().getField(name).get(this);
        } catch (NoSuchFieldException ignored) {
        } catch (IllegalAccessException e) {
            Lexiconfig.warn(e);
        }

        return null;
    }
}
