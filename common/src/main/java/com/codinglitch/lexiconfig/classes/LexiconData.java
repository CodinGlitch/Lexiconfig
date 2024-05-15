package com.codinglitch.lexiconfig.classes;

import com.codinglitch.lexiconfig.annotations.Lexicon;
import com.codinglitch.lexiconfig.annotations.LexiconEntry;
import com.codinglitch.lexiconfig.annotations.LexiconPage;
import com.codinglitch.lexiconfig.platform.Services;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlWriter;

import java.lang.reflect.Field;
import java.nio.file.Path;

public abstract class LexiconData extends LexiconHolding {
    public LexiconData() {
    }

    public Path getPath() {
        Lexicon annotation = this.getClass().getAnnotation(Lexicon.class);
        return Services.PLATFORM.getConfigPath().resolve(annotation.name() + ".toml");
    }

    private void parse(Object object, String path, CommentedFileConfig config, boolean writing) {
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(LexiconEntry.class)) {
                LexiconEntry entry = field.getAnnotation(LexiconEntry.class);
                String fieldPath = entry.path().equals("") ? field.getName() : entry.path();
                String fullPath = path.equals("") ? fieldPath : path+"."+fieldPath;

                try {
                    Object value = field.get(object);
                    if (writing) {
                        config.set(fullPath, value);
                        config.setComment(fullPath, entry.comment());
                    } else {
                        field.set(object, config.getOrElse(fullPath, value));
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field! " + e);
                }
            } else if (field.isAnnotationPresent(LexiconPage.class)) {
                LexiconPage page = field.getAnnotation(LexiconPage.class);
                String fieldPath = page.path().equals("") ? field.getName() : page.path();
                String fullPath = path.equals("") ? fieldPath : path+"."+fieldPath;

                try {
                    parse(field.get(object), fullPath, config, writing);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field! " + e);
                }
            }
        }
    }

    public void save() {
        Path path = getPath();
        CommentedFileConfig config = CommentedFileConfig.of(path);

        parse(this, "", config, true);

        config.save();
        config.close();
    }
    public void load() {
        Path path = getPath();
        CommentedFileConfig config = CommentedFileConfig.of(path);
        config.load();

        parse(this, "", config, false);

        config.close();
    }

    public TomlWriter writer = new TomlWriter();
}
