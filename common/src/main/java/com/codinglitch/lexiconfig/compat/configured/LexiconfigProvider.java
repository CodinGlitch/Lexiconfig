package com.codinglitch.lexiconfig.compat.configured;

import com.codinglitch.lexiconfig.Lexiconfig;
import com.codinglitch.lexiconfig.LexiconfigApi;
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
        LexiconfigApi.INSTANCE.debug("Attempting to find configured configs for mod {}", modContext.modId());

        Optional<Library> optionalLibrary = Lexiconfig.findLibrary(modContext.modId());
        if (optionalLibrary.isEmpty()) return Set.of();

        LexiconfigApi.INSTANCE.info("Providing configured configs for valid mod {}", modContext.modId());

        Library library = optionalLibrary.get();
        Set<IModConfig> configs = new HashSet<>();
        for (LexiconData lexicon : library.LEXICONS) {
            configs.add(new LexiconfiguredFile(library, lexicon));
            LexiconfigApi.INSTANCE.info("Loaded lexiconfigured library", lexicon.getName());
        }

        return configs;
    }
}
