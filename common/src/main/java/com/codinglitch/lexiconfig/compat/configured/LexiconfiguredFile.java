package com.codinglitch.lexiconfig.compat.configured;

import com.codinglitch.lexiconfig.Lexiconfig;
import com.codinglitch.lexiconfig.Library;
import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.classes.LexiconEntryData;
import com.mrcrayfish.configured.api.ConfigType;
import com.mrcrayfish.configured.api.IConfigEntry;
import com.mrcrayfish.configured.api.IConfigValue;
import com.mrcrayfish.configured.api.IModConfig;
import com.mrcrayfish.configured.util.ConfigHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class LexiconfiguredFile implements IModConfig {
    public final Library library;
    public final LexiconData lexicon;

    public LexiconfiguredFile(Library library, LexiconData lexicon) {
        this.library = library;
        this.lexicon = lexicon;
    }

    @Override
    public void update(IConfigEntry entry) {
        Set<IConfigValue<?>> values = ConfigHelper.getChangedValues(entry);
        if (values.isEmpty()) return;

        for (IConfigValue<?> value : values) {
            if (value instanceof LexiconfiguredValue<?> lexiconfiguredValue) {
                lexiconfiguredValue.update();
            }
        }

        Lexiconfig.publish();
    }

    @Override
    public IConfigEntry getRoot() {
        try {
            Field field = this.getClass().getDeclaredField("lexicon");
            return new LexiconfiguredEntry<>(new LexiconEntryData<>(field, this, lexicon));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConfigType getType() {
        return switch (lexicon.getLocation()) {
            case COMMON -> ConfigType.UNIVERSAL;
            case CLIENT -> ConfigType.CLIENT;
            case SERVER -> ConfigType.SERVER;
            case WORLD -> ConfigType.WORLD;
        };
    }

    @Override
    public String getFileName() {
        return lexicon.getName() + lexicon.getExtension().file;
    }

    @Override
    public String getModId() {
        return library.getName();
    }

    @Override
    public void loadWorldConfig(Path path, Consumer<IModConfig> result) throws IOException {

    }
}
