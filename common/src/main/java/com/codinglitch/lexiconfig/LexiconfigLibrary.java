package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.annotations.LexiconLibrary;

@LexiconLibrary(name = Lexiconfig.ID)
public class LexiconfigLibrary extends Library {
    public static LexiconfigConfig CONFIG = new LexiconfigConfig();

    @Override
    public void shelveLexicons() {
        LexiconfigApi.shelveLexicon(this, CONFIG);
    }
}
