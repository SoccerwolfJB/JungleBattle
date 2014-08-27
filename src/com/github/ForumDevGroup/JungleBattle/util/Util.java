package com.github.ForumDevGroup.JungleBattle.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Util {

    // Alle Spieler werden ausgegeben - Verursacht beim compilen aber keine Fehlermeldungen :3
    @SuppressWarnings("cannot find symbol")
    public static Player[] getPlayers() {
        return Bukkit.getOnlinePlayers();
    }

    // Hier bald mal die Languages einbauen :3
    public static void bcast(String message) {
        Arrays.asList(getPlayers()).forEach((Player player) -> player.sendMessage(message));
    }

}
