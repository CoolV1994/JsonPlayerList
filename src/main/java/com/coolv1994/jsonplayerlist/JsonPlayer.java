package com.coolv1994.jsonplayerlist;

import java.util.UUID;

/**
 *
 * @author Vinnie
 */
public class JsonPlayer {
    private final UUID uuid;
    private final String name;
    private final String displayName;

    public JsonPlayer(UUID uuid, String name, String displayName) {
        this.uuid = uuid;
        this.name = name;
        this.displayName = displayName;
    }
}
