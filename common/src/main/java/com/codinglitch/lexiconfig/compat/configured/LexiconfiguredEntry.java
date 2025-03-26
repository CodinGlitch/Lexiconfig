package com.codinglitch.lexiconfig.compat.configured;

import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.classes.LexiconEntryData;
import com.codinglitch.lexiconfig.classes.LexiconPageData;
import com.codinglitch.lexiconfig.classes.LexiconSubstrate;
import com.mrcrayfish.configured.api.IConfigEntry;
import com.mrcrayfish.configured.api.IConfigValue;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LexiconfiguredEntry<T> implements IConfigEntry {
    public final LexiconEntryData<T> entry;

    public LexiconfiguredEntry(LexiconEntryData<T> entry) {
        this.entry = entry;
    }

    @Override
    public List<IConfigEntry> getChildren() {
        if (entry.get().orElse(null) instanceof LexiconSubstrate substrate) {
            List<LexiconEntryData<?>> entries = substrate.getContents(Objects::nonNull);
            return entries.stream().map(LexiconfiguredEntry::new).collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public boolean isRoot() {
        return entry.get().orElse(null) instanceof LexiconData;
    }

    @Override
    public boolean isLeaf() {
        return !(entry.get().orElse(null) instanceof LexiconSubstrate);
    }

    @Nullable
    @Override
    public IConfigValue<?> getValue() {
        return new LexiconfiguredValue<>(entry);
    }

    @Override
    public String getEntryName() {
        return entry.getName();
    }

    @Nullable
    @Override
    public Component getTooltip() {
        return null;
    }

    @Nullable
    @Override
    public String getTranslationKey() {
        return null;
    }
}
