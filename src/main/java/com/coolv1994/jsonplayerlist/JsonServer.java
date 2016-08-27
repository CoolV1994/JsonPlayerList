package com.coolv1994.jsonplayerlist;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinnie
 */
public class JsonServer {
    private final List<JsonPlayer> players;
    private final String name;
    private String motd;
    private int onlinePlayers;

    public JsonServer(String name) {
        players = new ArrayList<>();
        this.name = name;
        this.motd = "";
        this.onlinePlayers = 0;
    }
    public void setMotd(String motd) {
        this.motd = motd;
    }

    public void addPlayer(JsonPlayer player) {
        players.add(player);
        onlinePlayers++;
    }

    public String getName() {
        return name;
    }

    public int getPlayersOnline() {
        return onlinePlayers;
    }
}
