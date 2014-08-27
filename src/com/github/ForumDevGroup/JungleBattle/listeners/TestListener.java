package com.github.ForumDevGroup.JungleBattle.listeners;

import com.github.ForumDevGroup.JungleBattle.interfaces.CountdownPing;
import com.github.ForumDevGroup.JungleBattle.util.Countdown;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onEvent(final PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer().getName() + " hat das Spiel betreten und wird es jetzt wieder verlassen... VERPISS DICH!");
        new Countdown("kick" + event.getPlayer().getName(), 40, "Spieler " + event.getPlayer().getName() + " wird in %T Sekunden gekickt!", new CountdownPing() {
            @Override
            public void ping(int time) {
                if(time == 0)
                    event.getPlayer().kickPlayer("Du wurdest gekickt.");
            }
        }).start();
    }

}
