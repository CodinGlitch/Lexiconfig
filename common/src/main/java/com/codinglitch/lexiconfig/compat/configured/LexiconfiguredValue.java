package com.codinglitch.lexiconfig.compat.configured;

import com.codinglitch.lexiconfig.Library;
import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.classes.LexiconEntryData;
import com.mrcrayfish.configured.api.ConfigType;
import com.mrcrayfish.configured.api.IConfigEntry;
import com.mrcrayfish.configured.api.IConfigValue;
import com.mrcrayfish.configured.api.IModConfig;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class LexiconfiguredValue<T> implements IConfigValue<T> {
    public final LexiconEntryData<T> entry;
    public T value;

    public LexiconfiguredValue(LexiconEntryData<T> entry) {
        this.entry = entry;
        this.value = entry.get().orElse(entry.defaultValue);
    }

    public void update() {
        this.entry.set(value);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getDefault() {
        return entry.defaultValue;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean isValid(T value) {
        return true;
    }

    @Override
    public boolean isDefault() {
        return value == entry.defaultValue;
    }

    @Override
    public boolean isChanged() {
        return value.equals(entry.get().orElse(null));
    }

    @Override
    public void restore() {
        value = entry.defaultValue;
    }

    @Nullable
    @Override
    public Component getComment() {
        return Component.literal(entry.getComment());
    }

    @Nullable
    @Override
    public String getTranslationKey() {
        return "";
    }

    @Nullable
    @Override
    public Component getValidationHint() {
        return null;
    }

    @Override
    public String getName() {
        return entry.field.getName();
    }

    @Override
    public void cleanCache() {

    }

    @Override
    public boolean requiresWorldRestart() {
        return false;
    }

    @Override
    public boolean requiresGameRestart() {
        return false;
    }
}
