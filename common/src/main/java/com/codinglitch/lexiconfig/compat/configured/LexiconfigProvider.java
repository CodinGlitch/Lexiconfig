package com.codinglitch.lexiconfig.compat.configured;

import com.codinglitch.lexiconfig.Lexiconfig;
import com.codinglitch.lexiconfig.Library;
import com.codinglitch.lexiconfig.classes.LexiconData;
import com.mrcrayfish.configured.api.IModConfig;
import com.mrcrayfish.configured.api.IModConfigProvider;
import com.mrcrayfish.configured.api.ModContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LexiconfigProvider implements IModConfigProvider {
    @Override
    public Set<IModConfig> getConfigurationsForMod(ModContext modContext) {
        Optional<Library> optionalLibrary = Lexiconfig.findLibrary(modContext.modId());
        if (optionalLibrary.isEmpty()) return Set.of();

        Library library = optionalLibrary.get();
        Set<IModConfig> configs = new HashSet<>();
        for (LexiconData lexicon : library.LEXICONS) {
            configs.add(new LexiconfiguredFile(library, lexicon));
        }

        return configs;
    }
}
