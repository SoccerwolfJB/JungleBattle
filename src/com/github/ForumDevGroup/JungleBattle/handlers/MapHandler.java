package com.github.ForumDevGroup.JungleBattle.handlers;

import com.github.ForumDevGroup.JungleBattle.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MapHandler {

    /*
     * Alle hier ausgegebenen Maps sind noch nicht geladen!
     * Diese müssen im Nachhinein mit .loadMap() geladen werden.
     */

    private static Random random = new Random();

    // Lediglich der Filenamefilter um zB die Lobby-Map oder Systemdateien auszufiltern
    private static FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            if(name.startsWith(".") || name.equalsIgnoreCase("lobby"))
                return false;
            return true;
        }
    };

    // Gibt eine zufällige Map aus dem "Maps" Ordner des Servers aus
    public static Map getRandomMap() {
        File[] files = new File("maps").listFiles(filter);
        int mapI = random.nextInt(files.length);
        return new Map(files[mapI].getName(), false);
    }

    // Gibt eine zufällige von gegebenen Maps aus
    public static Map getRandomMap(Set<Map> list) {
        return (Map) list.toArray()[new Random().nextInt(list.toArray().length)];
    }

    // Gibt so viele Maps zum voten aus wie gewünscht
    public static List<Map> getVoteMaps(int count) {
        File[] files = new File("maps").listFiles(filter);
        ArrayList<Map> list = new ArrayList<Map>();
        int i = random.nextInt(files.length - (count - 1));
        while(i < count) {
            list.add(new Map(files[i].getName(), false));
            i++;
        }
        return list;
    }

}
