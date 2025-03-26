package com.codinglitch.lexiconfig;

import com.codinglitch.lexiconfig.annotations.Lexicon;
import com.codinglitch.lexiconfig.annotations.LexiconEntry;
import com.codinglitch.lexiconfig.annotations.LexiconPage;
import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.classes.LexiconPageData;

@Lexicon(name = Lexiconfig.ID)
public class LexiconfigConfig extends LexiconData {
    @LexiconPage(comment = "This is a test category.")
    public Category testCategory = new Category();

    @LexiconEntry(comment = "This is a global field.")
    public Integer globalField = 8;

    public static class Category extends LexiconPageData {
        @LexiconEntry(comment = "This is a page entry.")
        public Integer pageEntry = 8;
    }
}