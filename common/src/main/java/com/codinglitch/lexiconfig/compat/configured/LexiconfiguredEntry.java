package com.codinglitch.lexiconfig.compat.configured;

import com.codinglitch.lexiconfig.classes.LexiconData;
import com.codinglitch.lexiconfig.classes.LexiconEntryData;
import com.codinglitch.lexiconfig.classes.LexiconPageData;
import com.codinglitch.lexiconfig.classes.LexiconSubstrate;
import com.mrcrayfish.configured.api.IConfigEntry;
import com.mrcrayfish.configured.api.IConfigValue;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LexiconfiguredEntry<T> implements IConfigEntry {
    public final LexiconEntryData<T> entry;

    public List<LexiconfiguredEntry<?>> children = null;
    public LexiconfiguredValue<T> value;

    public LexiconfiguredEntry(LexiconEntryData<T> entry) {
        this.entry = entry;
    }

    @Override
    public List<IConfigEntry> getChildren() {
        if (entry.get().orElse(null) instanceof LexiconSubstrate substrate) {
            List<LexiconEntryData<?>> entries = substrate.getContents(Objects::nonNull);
            if (children == null) {
                children = entries.stream().map(LexiconfiguredEntry::new).collect(Collectors.toList());
            }

            return children.stream().collect(Collectors.toUnmodifiableList()); // ??? okay??
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
        if (value == null) {
            value = new LexiconfiguredValue<>(entry);
        }

        return value;
    }

    @Override
    public String getEntryName() {
        return entry.getName();
    }

    @Nullable
    @Override
    public Component getTooltip() {
        return Component.literal(entry.getComment());
    }

    @Nullable
    @Override
    public String getTranslationKey() {
        return "";
    }
}
