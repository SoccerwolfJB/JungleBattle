package com.github.ForumDevGroup.JungleBattle.util;

import com.github.ForumDevGroup.JungleBattle.Main;
import com.github.ForumDevGroup.JungleBattle.interfaces.CountdownPing;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class Countdown implements Runnable {

    // HashMap zum speichern aller Countdowns unter einem Namen
    private static HashMap<String, Countdown> countdowns = new HashMap<String, Countdown>();

    // Normaler Konstruktor
    public Countdown(String name, int time, String bcast, CountdownPing ping, int[] bcasttimes) {
        this.name = name;
        this.timer = time;
        this.bcast = bcast;
        this.ping = ping;
        this.bcasttimes = bcasttimes;
        countdowns.put(this.name, this);
    }

    // "Standard" Konstruktor
    public Countdown(String name, int time, String bcast, CountdownPing ping) {
        this(name, time, bcast, ping, new int[] {1200, 600, 300, 180, 120, 60, 30, 20, 15, 10, 5, 4, 3, 2, 1});
    }

    private String name;
    private int timer;
    private String bcast;
    private CountdownPing ping;
    private int[] bcasttimes;

    private int id = -1;

    // Startet einen Countdown
    public void start() {
        this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance(), this, 0L, 20L);
    }

    // Stoppt einen Countdown
    public void stop() {
        Bukkit.getScheduler().cancelTask(this.id);
        this.timer = 0;
        this.id = -1;
        countdowns.remove(this.name);
    }

    // Wird jede Sekunde aufgerufen - siehe Runnable
    @Override
    public void run() {
        this.timer = this.timer - 1;
        this.ping.ping(timer);
        for(int bcasttime : bcasttimes) {
            if (bcasttime == timer) {
                Util.bcast(this.bcast.replace("%T", "" + this.timer));
            }
        }
        if(timer <= 0)
            stop();
    }

    public static Countdown getCountdown(String name) throws NullPointerException {
        if(!countdowns.containsKey(name))
            throw new NullPointerException("Countdown \"" + name + "\" couldn't be found.");
        return countdowns.get(name);
    }

}
