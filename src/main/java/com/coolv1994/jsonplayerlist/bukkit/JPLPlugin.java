package com.coolv1994.jsonplayerlist.bukkit;

import com.coolv1994.jsonplayerlist.IPlugin;
import com.coolv1994.jsonplayerlist.JsonPlayer;
import com.coolv1994.jsonplayerlist.JsonServer;
import com.coolv1994.jsonplayerlist.JsonStats;
import com.coolv1994.jsonplayerlist.PostTask;
import com.coolv1994.jsonplayerlist.Utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Vinnie
 */
public class JPLPlugin extends JavaPlugin implements IPlugin {

    @Override
    public JsonStats getStats() {
        JsonStats stats = new JsonStats();
        stats.setMaxPlayers(getServer().getMaxPlayers());
        JsonServer server = new JsonServer(getServer().getServerName());
        server.setMotd(getServer().getMotd());
        for (Player player : getServer().getOnlinePlayers()) {
            server.addPlayer(new JsonPlayer(player.getUniqueId(), player.getName(), player.getDisplayName()));
        }
        stats.addServer(server);
        return stats;
    }

    @Override
    public String getConfigString(String path) {
        return getConfig().getString(path);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        Utils.setPlugin(this);
        Utils.postStatus(true);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new PostTask(),
                getConfig().getInt("wait") * 20, getConfig().getInt("time") * 20);
    }

    @Override
    public void onDisable() {
        Utils.postStatus(false);
    }
}
