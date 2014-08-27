package com.github.ForumDevGroup.JungleBattle.handlers;

import com.github.ForumDevGroup.JungleBattle.Main;
import com.github.ForumDevGroup.JungleBattle.util.GameState;
import com.github.ForumDevGroup.JungleBattle.util.Map;

import java.util.*;

public class GameHandler {

    private static GameState currentState = GameState.VOTING;
    private static List<Map> votableMaps;
    private static HashMap<Integer, Integer> votesForMap;
    private static int numberOfMaps = 3;
    private static Map gameMap, lobbyMap;
    private static ArrayList<UUID> hasVoted = new ArrayList<UUID>();

    // Lädt die Lobbymap
    public static void loadLobbyMap() {
        lobbyMap = new Map(Main.instance().getConfig().getString("lobbymap"));
    }

    // Übergibt die Maps zum voten
    public static void initMaps() {
        votableMaps = MapHandler.getVoteMaps(numberOfMaps);
    }

    // Überprüft ob ein Spieler bereits gevotet hat
    public static boolean hasVoted(UUID player) {
        return hasVoted.contains(player);
    }

    // Votet für eien Map und speichert, dass ein Spieler gevotet hat
    public static void incrementVote(UUID player, Map map) {
        hasVoted.add(player);
        int votes = votesForMap.remove(votableMaps.indexOf(map));
        votesForMap.put(votableMaps.indexOf(map), ++votes);
    }

    // Gibt den Game-Status aus
    public static GameState getGamestate() {
        return currentState;
    }

    // Setzt den Game-Status
    public static void setGamestate(GameState toSet) {
        currentState = toSet;
    }

    // Deaktiviert das Voting, setzt die Game-Map als die gevotete Map und setzt einen neuen Game-Status
    public static void disableVoting() {
        setGamestate(GameState.PREPARING);
        List<Map> mapsWithHighestValue = new ArrayList<Map>();
        int highestValue = votesForMap.values().stream().max(Integer::max).get();
        votesForMap.keySet().stream().filter(key -> votesForMap.get(key).equals(highestValue)).forEach(key -> mapsWithHighestValue.add(votableMaps.get(key)));
        int index = 0;
        if(mapsWithHighestValue.size() > 1)
        {
            index = new Random().nextInt(mapsWithHighestValue.size());
        }
        gameMap = votableMaps.get(index);
        votableMaps.clear();
        votesForMap.clear();
    }

    // Gibt die Game-Map aus
    public static Map getGameMap(){
        return gameMap;
    }

    // Gibt die Lobby-Map aus
    public static Map getLobbyMap() {
        return lobbyMap;
    }

}
