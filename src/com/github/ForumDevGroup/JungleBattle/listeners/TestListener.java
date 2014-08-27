package com.github.ForumDevGroup.JungleBattle.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer.getName() + " hat das Spiel betreten und wird es jetzt wieder verlassen... VERPISS DICH!");
    }

}
