package com.codinglitch.lexiconfig.classes;

import com.codinglitch.lexiconfig.LexiconfigApi;
import com.codinglitch.lexiconfig.annotations.LexiconEntry;

import java.lang.reflect.Field;
import java.util.Optional;

public class LexiconEntryData<T> {
    public final Field field;
    public final Object owner;

    public final T defaultValue;

    public LexiconEntryData(Field field, Object owner, T defaultValue) {
        this.field = field;
        this.owner = owner;
        this.defaultValue = defaultValue;
    }

    public Optional<T> get() {
        try {
            return Optional.ofNullable((T) field.get(owner));
        } catch (IllegalAccessException e) {
            LexiconfigApi.INSTANCE.warn(e);
        }

        return Optional.empty();
    }
    public void set(T value) {
        try {
            field.set(owner, value);
        } catch (IllegalAccessException e) {
            LexiconfigApi.INSTANCE.warn(e);
        }
    }

    public void reset() {
        this.set(this.defaultValue);
    }

    public String getName() {
        return field.getName();
    }
    public String getComment() {
        LexiconEntry entry = field.getAnnotation(LexiconEntry.class);
        if (entry == null) return "";
        return entry.comment();
    }
    public String getLang() {
        LexiconEntry entry = field.getAnnotation(LexiconEntry.class);
        if (entry == null) return "";
        return entry.lang();
    }
}
