package com.github.ForumDevGroup.JungleBattle.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.com.google.common.reflect.ClassPath;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.github.ForumDevGroup.JungleBattle.Main;

public class Registerer {

    // Alle Listener aus einem Package werden ausgefiltert und registriert
    public static void registerListener(String packagePath) {
        // Alle Klassen des Packages werden durchgegangen
        for (Class<?> cls : getClasses(packagePath)) {
            // Von jeder Klasse nochmals alle Interfaces
            for(Class<?> iface : cls.getInterfaces()) {
                // Überprüfung ob der Name des Interfaces "Listener" ist
                if (iface.getPackage().getName().equals("org.bukkit.event.Listener")) {
                    try {
                        // Neue Instanz des gefundenen Listeners wird erstellt
                        Listener listener = (Listener) cls.newInstance();
                        // Und diese dann registriert
                        Bukkit.getPluginManager().registerEvents(listener, Main.instance());
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Alle Comamnds aus einem Package werden ausgefiltert und registriert
    public static void registerCommands(String packagePath) {
        // Alle Klassen des Packages werden durchgegangen
        for (Class<?> cls : getClasses(packagePath)) {
            // Es wird überprüft ob der Name der Klasse mit "Command" anfängt
            if (cls.getName().toLowerCase().startsWith("command")) {
                // Alle Interfaces der Klasse werden durchgegangen
                for (Class<?> iface : cls.getInterfaces()) {
                    // Überprüfung ob der Name des Interfaces "CommandExecutor" ist
                    if (iface.getName().equalsIgnoreCase("CommandExecutor")) {
                        try {
                            // Neue Instanz des gefundenen CommandExecutors wird erstellt
                            CommandExecutor executor = (CommandExecutor) cls.newInstance();
                            // Und dann für den gefundenen Command als Executor gesetzt
                            Main.instance().getCommand(cls.getName().toLowerCase().replace("command", "").toLowerCase()).setExecutor(executor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    // Alle Adapter aus einem Package werden rausgefiltert und zum als PacketListener zum ProtocolManager geaddet
    public static void registerAdapters(String packagePath) {
        for (Class<?> cls : getClasses(packagePath)) {
            if (cls.getName().toLowerCase().endsWith("adapter")) {
                if (cls.getSuperclass().getName().equalsIgnoreCase("PacketAdapter")) {
                    try {
                        PacketAdapter adapter = (PacketAdapter) cls.newInstance();
                        ProtocolLibrary.getProtocolManager().addPacketListener(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    // Alle Klassen aus einem Package werden eingelesen und in einer Liste abgespeichert
    private static List<Class<?>> getClasses(String packageName) {
        packageName = packageName + ".";
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses()) {
                if(classInfo.getName().toLowerCase().startsWith(packageName)) {
                    System.out.print("Equals: " + classInfo.getName());
                    list.add(classInfo.load());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
		return list;
	}

}
