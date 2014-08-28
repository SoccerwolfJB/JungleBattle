package com.github.ForumDevGroup.JungleBattle.listeners;

import com.github.ForumDevGroup.JungleBattle.handlers.GameHandler;
import com.github.ForumDevGroup.JungleBattle.handlers.UUIDHandler;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.GameMode.ADVENTURE;

public class TestListener implements Listener {

    @EventHandler
    public void onEvent(final PlayerJoinEvent event) {
        UUIDHandler.update(event.getPlayer());
        event.getPlayer().setGameMode(ADVENTURE);
    }

}
