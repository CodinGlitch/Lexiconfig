package com.codinglitch.lexiconfig.classes;

import com.codinglitch.lexiconfig.LexiconfigApi;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

public class LexiconSubstrate {
    Map<String, LexiconEntryData<?>> ENTRIES = new HashMap<>();

    public List<LexiconEntryData<?>> getContents(Predicate<LexiconEntryData<?>> criteria) {
        List<LexiconEntryData<?>> entries = new ArrayList<>();
        for (Map.Entry<String, LexiconEntryData<?>> entry : ENTRIES.entrySet()) {
            if (!criteria.test(entry.getValue())) continue;
            entries.add(entry.getValue());
        }

        return entries;
    }

    public <T> Optional<T> getEntry(String name) {
        LexiconEntryData<T> entry = (LexiconEntryData<T>) ENTRIES.get(name);
        if (entry == null) return Optional.empty();

        return entry.get();
    }

    public void catalog() {
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                Object value = field.get(this);
                ENTRIES.put(field.getName(), new LexiconEntryData<>(field, this, value));

                if (value instanceof LexiconSubstrate substrate) {
                    substrate.catalog();
                }
            } catch (IllegalAccessException e) {
                LexiconfigApi.warn(e);
            }
        }
    }

    // sugar
    public Optional<Integer> getInt(String name) {
        return this.getEntry(name);
    }
    public Optional<Float> getFloat(String name) {
        return this.getEntry(name);
    }
    public Optional<Double> getDouble(String name) {
        return this.getEntry(name);
    }
    public Optional<String> getString(String name) {
        return this.getEntry(name);
    }
    public Optional<LexiconPageData> getPage(String name) {
        return this.getEntry(name);
    }
}
