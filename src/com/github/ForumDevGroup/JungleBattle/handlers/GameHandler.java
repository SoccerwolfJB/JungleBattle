package com.github.ForumDevGroup.JungleBattle.handlers;

import com.github.ForumDevGroup.JungleBattle.util.GameState;
import com.github.ForumDevGroup.JungleBattle.util.Map;

import java.util.*;

public class GameHandler {
    private static GameState currentState = GameState.VOTING;
    private static List<Map> votableMaps;
    private static HashMap<Integer, Integer> votesForMap;
    private static int numberOfMaps = 3;
    private static Map gameMap;
    private static ArrayList<UUID> hasVoted = new ArrayList<>();
    public static void initMaps()
    {
        votableMaps = MapHandler.getVoteMaps(numberOfMaps);
    }

    public static boolean hasVoted(UUID player)
    {
        return hasVoted.contains(player);
    }

    public static void incrementVote(UUID player, Map map){
        hasVoted.add(player);
        int votes = votesForMap.remove(votableMaps.indexOf(map));
        votesForMap.put(votableMaps.indexOf(map), ++votes);
    }

    public static GameState getGamestate()
    {
        return currentState;
    }

    public static void setGamestate(GameState toSet)
    {
        currentState = toSet;
    }

    public static void disableVoting(){
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

    public static Map getGameMap(){
        return gameMap;
    }
}
