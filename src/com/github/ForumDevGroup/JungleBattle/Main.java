package com.github.ForumDevGroup.JungleBattle;

import com.github.ForumDevGroup.JungleBattle.util.MySQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.github.ForumDevGroup.JungleBattle.network.LanguageAdapter;

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
        // Neue Instanz von MySQL wird erstellt
        try {
            mySQL = loadDBConnFromCfg();
            this.getLogger().info("Connected to database.");
        } catch(SQLException e) {
            this.getLogger().warning("Could not connect to database.");
            e.printStackTrace();
        }
        // Missing comment??
        ProtocolLibrary.getProtocolManager().addPacketListener(new LanguageAdapter());
        this.getLogger().info("Loaded plugin successfully.");
	}

	@Override
	public void onDisable() {
        this.getLogger().info("Stopped plugin successfully.");
	}

    // Die in der onEnable() Methode erstellte Instanz wird ausgegeben
	public static Main instance() {
		return instance;
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
