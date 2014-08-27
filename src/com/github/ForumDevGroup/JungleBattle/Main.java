package com.github.ForumDevGroup.JungleBattle;

import com.github.ForumDevGroup.JungleBattle.util.MySQL;
import com.github.ForumDevGroup.JungleBattle.util.Registerer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin {

    // Instanz der Hauptklasse
    private static Main instance;
    // MySQL Instanz
    private static MySQL mySQL;

	@Override
	public void onEnable() {
        // Hauptklasse wird an die Instanz übergeben
        instance = this;
        // Default Config wird geladen
        this.saveDefaultConfig();
        this.getLogger().info("Loaded default config.");
        // Listeners laden
        Registerer.registerListeners("com.github.ForumDevGroup.JungleBattle.listeners");
        Registerer.registerCommands("com.github.ForumDevGroup.JungleBattle.commands");
        Registerer.registerAdapters("com.github.ForumDevGroup.JungleBattle.network");
        //Language.initTranslations();
        // Neue Instanz von MySQL wird erstellt
        try {
            mySQL = loadDBConnFromCfg();
            this.getLogger().info("Successfully connected to the database.");
        } catch(SQLException e) {
            this.getLogger().warning("Could not connect to the database. Are the connection-informations in the config correct?");
            e.printStackTrace();
        }
        this.getLogger().info("Loaded plugin successfully.");
	}

	@Override
	public void onDisable() {
        this.getLogger().info("Stopped plugin successfully.");
	}

    // Funktion mit der die in der onEnable() Methode erstellte MySQL Instanz ausgegeben wird
	public static Main instance() {
		return instance;
	}

    // Funktion mit der die in der onEnable() Methode erstellte MySQL Instanz ausgegeben wird
    public static MySQL getMySQL() {
        return mySQL;
    }

    // Funktion zum laden einer neuen MySQL Instanz
    private MySQL loadDBConnFromCfg() throws SQLException {
        FileConfiguration cfg = this.getConfig();
        return new MySQL(
                cfg.getString("db.host"),
                cfg.getString("db.dbname"),
                cfg.getString("db.username"),
                cfg.getString("db.password")
        );
    }

}
