package com.github.ForumDevGroup.JungleBattle.util;

import com.github.ForumDevGroup.JungleBattle.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

public class Config {

    // Hier wird die Config wenn sie ausgelesen wird eingespeichert
    private static HashMap<String, Object> setting = new HashMap<>();

    // Findet weitere Pfade und gibt diese aus
    private static Set<String> getEntrys(String path) {
        try {
            Set<String> s = Main.instance().getConfig().getConfigurationSection(path).getKeys(true);
            return s;
        } catch (NullPointerException e) {
            return null;
        }
    }

    // Lädt die Settings der Main-Config
    public static void initSettings() {
        initSettings(Main.instance().getConfig(), "");
    }

    // Liest Settings einer Config aus und speichert sie mit ihrem Pfad in eine HashMap
    public static void initSettings(FileConfiguration cfg, String defaullt) {
        Set<String> entrys = getEntrys(defaullt);
        if (entrys == null)
            return;
        Stream<String> entrysS = entrys.stream();
        entrysS.forEach(s -> {
            if ((cfg.get(s) != null) && (cfg.getConfigurationSection(s) == null)) setting.put(s, cfg.get(s));
            initSettings(cfg, s);
        });
    }

    // Gibt die HashMap der Settings aus
    public static HashMap<String, Object> getSettings() {
        return setting;
    }

}
