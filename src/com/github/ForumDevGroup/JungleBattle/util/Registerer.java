package com.github.ForumDevGroup.JungleBattle.util;

import com.github.ForumDevGroup.JungleBattle.Main;
import net.minecraft.util.com.google.common.reflect.ClassPath;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class Registerer {

    /*
     * Kommentare folgen.. :)
     */
    public static void registerListener(String packagePath) {
        try {
            for (Class<?> cls : getClasses(packagePath)) {
                for(Class<?> iface : cls.getInterfaces()) {
                    if (iface.getName().toLowerCase().contains("listener")) {
                        try {
                            Listener listener = (Listener) cls.newInstance();
                            Bukkit.getPluginManager().registerEvents(listener, Main.instance());
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch(Exception e) {}
    }

    public static void registerCommands(String packagePath) {
        try {
            for (Class<?> cls : getClasses(packagePath)) {
                if (cls.getName().toLowerCase().startsWith("command")) {
                    for (Class<?> iface : cls.getInterfaces()) {
                        if (iface.isInterface() && iface.getName().equalsIgnoreCase("CommandExecutor")) {
                            try {
                                CommandExecutor executor = (CommandExecutor) cls.newInstance();
                                Main.instance().getCommand(cls.getName().toLowerCase().replace("command", "").toLowerCase()).setExecutor(executor);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {}
    }

    private static List<Class<?>> getClasses(String packageName) {
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses(packageName)) {
                list.add(Class.forName(classInfo.getName()));
            }
        } catch(Exception e) {
            // e.printStackTrace();
        }
        return list;
    }

}