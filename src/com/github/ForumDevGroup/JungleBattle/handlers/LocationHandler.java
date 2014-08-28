package com.github.ForumDevGroup.JungleBattle.handlers;

import com.github.ForumDevGroup.JungleBattle.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationHandler {

    private static void createFile(File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(Exception e) {}
        }
    }

    private static void save(File file, FileConfiguration cfg) {
        try {
            cfg.save(file);
        } catch(IOException e) {}
    }

    public static void saveLoc(Location location, String name, Map map) {
        File file = new File("maps" + File.separatorChar + map.getName(), "locations.yml");
        createFile(file);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String path = name + ".";
        cfg.set(path + "x", location.getBlockX() + 0.5D);
        cfg.set(path + "y", location.getBlockY() + 0.25D);
        cfg.set(path + "z", location.getBlockZ() + 0.5D);
        cfg.set(path + "yaw", location.getYaw());
        cfg.set(path + "pitch", location.getPitch());
        save(file, cfg);
    }

    public static Location getLoc(String name, Map map) {
        File file = new File("maps" + File.separatorChar + map.getName(), "locations.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String path = name + ".";
        if(cfg.get(name) == null)
            return null;
        return new Location(
                map.getWorld(),
                cfg.getDouble(path + "x"),
                cfg.getDouble(path + "y"),
                cfg.getDouble(path + "z"),
                (float) cfg.getDouble(path + "yaw"),
                (float) cfg.getDouble(path + "pitch")
        );
    }

    public static void delLoc(String name, Map map) {
        File file = new File("maps" + File.separatorChar + map.getName(), "locations.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(name, null);
        save(file, cfg);
    }

}