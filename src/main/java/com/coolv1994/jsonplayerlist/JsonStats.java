package com.coolv1994.jsonplayerlist;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinnie
 */
public class JsonStats {
    private final List<JsonServer> servers;
    private int maxPlayers;
    private int onlinePlayers;

    public JsonStats() {
        servers = new ArrayList<>();
        maxPlayers = 0;
        onlinePlayers = 0;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void addServer(JsonServer server) {
        servers.add(server);
        onlinePlayers += server.getPlayersOnline();
    }
}
