package com.github.ForumDevGroup.JungleBattle.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        event.setJoinMessage("a player joined");
    }

}
