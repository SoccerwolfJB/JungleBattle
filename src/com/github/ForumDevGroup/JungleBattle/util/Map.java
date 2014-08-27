package com.github.ForumDevGroup.JungleBattle.util;

import com.github.ForumDevGroup.JungleBattle.Main;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Map {

    private World world;
    private String name;
    private List<String> authors;

    public static HashMap<String, Map> mapsMap = new HashMap<String, Map>();

    // Konstruktor, bei dem die Welt direkt geladen wird
    public Map(String name) {
        this(name, true);
    }

    // Konstruktor: Wenn creation == true: Welt wird geladen :3
    public Map(String name, boolean creation) {
        this.name = name;
        if(creation)
            this.loadWorld();
        // Attribute werden geladen
        File file = new File("maps" + File.separatorChar + name, "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Main.instance().getLogger().info("Loaded the attributes of Map \"" + name + "\" from config.");
        mapsMap.put(name.toLowerCase(), this);
        if(creation) Main.instance().getLogger().info("Loaded Map \"" + name + "\"");
        else Main.instance().getLogger().info("Queued Map \"" + name + "\". Ready to load.");
    }

    // Lädt die Welt der Map (auch im Nachhinein)
    public void loadWorld() {
        this.world = Bukkit.createWorld(new WorldCreator("/maps/" + name + "/world"));
        this.world.setAutoSave(false);
        this.world.setDifficulty(Difficulty.PEACEFUL);
    }

    // Setzt den Namen der Map
    public void setName(String name) {
        this.name = name;
    }

    // Setzt die Erstellter der Map
    public void setAuthors(List<String> list) {
        this.authors = list;
    }

    // Gibt die Welt zurück
    public World getWorld() {
        return this.world;
    }

    // Gibt den Namen der Map zurück
    public String getName() {
        return this.name;
    }

    // Gibt die Mapersteller zurück
    public List<String> getAuthors() {
        return this.authors;
    }

}
