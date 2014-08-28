package com.github.ForumDevGroup.JungleBattle.listeners;

import com.github.ForumDevGroup.JungleBattle.handlers.GameHandler;
import com.github.ForumDevGroup.JungleBattle.handlers.UUIDHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onEvent(final PlayerJoinEvent event) {
        UUIDHandler.update(event.getPlayer());
    }

}
