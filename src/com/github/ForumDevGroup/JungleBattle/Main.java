package com.github.ForumDevGroup.JungleBattle;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    // Instanz der Hauptklasse
    private static Main instance;

	@Override
	public void onEnable() {
        // Hauptklasse wird an die Instanz übergeben
        this.instance = this;
        /*
         * Hier kommt noch das Zeug rein :)
         */
        this.getLogger().info("Loaded plugin successfully.");
	}

	@Override
	public void onDisable() {
        this.getLogger().info("Stopped plugin successfully.");
	}

}
