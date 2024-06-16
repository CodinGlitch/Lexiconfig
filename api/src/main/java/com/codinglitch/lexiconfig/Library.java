package com.codinglitch.lexiconfig;

/**
 * This is the library class where all lexicons should be stored and shelved.
 * Create a new class extending this one, override {@code shelveLexicons}, and add the {@code LexiconLibrary} annotation.
 *
 * @see com.codinglitch.lexiconfig.annotations.LexiconLibrary
 */
public abstract class Library {
    public abstract void shelveLexicons();
}
