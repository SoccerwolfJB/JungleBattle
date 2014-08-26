package com.github.ForumDevGroup.JungleBattle;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.github.ForumDevGroup.JungleBattle.network.LanguageAdapter;

public class Main extends JavaPlugin {

    // Instanz der Hauptklasse
    private static Main instance;

	@Override
	public void onEnable() {
        // Hauptklasse wird an die Instanz übergeben
        instance = this;
        /*
         * Hier kommt noch das Zeug rein :)
         */
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
	
}
