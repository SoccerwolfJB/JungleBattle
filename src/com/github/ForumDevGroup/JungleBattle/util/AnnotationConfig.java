package com.github.ForumDevGroup.JungleBattle.util;

import com.github.ForumDevGroup.JungleBattle.annotations.Path;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class AnnotationConfig {
    public AnnotationConfig(FileConfiguration config) {
        Arrays.asList(this.getClass().getDeclaredFields()).stream().filter(f -> f.isAnnotationPresent(Path.class
        ) && f.getAnnotation(Path.class).getPath() != null).forEach(f -> {
            f.setAccessible(true);
            try {
                f.set(this, config.get(f.getAnnotation(Path.class).getPath()));
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            ;
        });

    }


}
