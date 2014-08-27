package com.github.ForumDevGroup.JungleBattle.handlers;

import com.github.ForumDevGroup.JungleBattle.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.UUID;

public class UUIDHandler {

    // Erstellt die Tabelle
    private static void createTableIfNotExists() {
        try {
            Main.getMySQL().update("CREATE TABLE IF NOT EXISTS uuids (uuid CHAR(36), name VARCHAR(16), PRIMARY KEY uuid (uuid))");
        } catch(Exception e) {}
    }

    // Gibt wieder ob der Spieler player bereits gelistet ist
    private static boolean isListed(Player player) {
        try {
            ResultSet resultSet = Main.getMySQL().query("SELECT * FROM uuids WHERE uuid=\"" + player.getUniqueId().toString() + "\"");
            return resultSet.next();
        } catch(Exception e) {
            return false;
        }
    }

    // Listet den Spieler player, falls er noch nicht gelistet ist
    private static void list(Player player) {
        if(isListed(player))
            return;
        createTableIfNotExists();
        try {
            Main.getMySQL().update("INSERT INTO uuids (uuid, name) VALUES (\"" + player.getUniqueId().toString() + "\", \"" + player.getName() + "\")");
        } catch(Exception e) {}
    }

    // Updatet den Datensatz für den Spieler player
    public static void update(Player player) {
        list(player);
        try {
            Main.getMySQL().update("UPDATE uuids SET name=\"" + player.getName() + "\" WHERE uuid=\"" + player.getUniqueId().toString() + "\"");
        } catch(Exception e) {}
    }

    // Gibt die UUID eines Spielers mit Namen name wieder
    public static UUID getUuidOf(String name) {
        try {
            ResultSet resultSet = Main.getMySQL().query("SELECT uuid FROM uuids WHERE name=\"" + name + "\"");
            resultSet.next();
            return UUID.fromString(resultSet.getString(1));
        } catch(Exception e) {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
    }

    // Gibt den Namen eines Spielers mit der UUID uuid wieder
    public static String getNameOf(UUID uuid) {
        if(uuid.toString().equals("00000000-0000-0000-0000-000000000000"))
            return "No UUID !";
        try {
            ResultSet resultSet = Main.getMySQL().query("SELECT name FROM uuids WHERE uuid=\"" + uuid.toString() + "\"");
            resultSet.next();
            return resultSet.getString(1);
        } catch(Exception e) {
            return "NameNotFound";
        }
    }

}
