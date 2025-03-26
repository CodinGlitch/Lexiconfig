package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.annotations.LexiconLibrary;
import com.codinglitch.lexiconfig.classes.LexiconData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is the library class where all lexicons should be stored and shelved.
 * Create a new class extending this one, override {@code shelveLexicons}, and add the {@code LexiconLibrary} annotation.
 *
 * @see com.codinglitch.lexiconfig.annotations.LexiconLibrary
 */
public abstract class Library {
    public List<LexiconData> LEXICONS = new ArrayList<>();

    public String getName() {
        LexiconLibrary annotation = this.getClass().getAnnotation(LexiconLibrary.class);
        return annotation.name();
    }

    public abstract void shelveLexicons();
    public void shelve(LexiconData lexicon) {
        LEXICONS.add(lexicon);
    }

    public Optional<LexiconData> browse(String name) {
        for (LexiconData lexicon : LEXICONS) {
            if (name.equals(lexicon.getName())) return Optional.of(lexicon);
        }

        return Optional.empty();
    }
}
