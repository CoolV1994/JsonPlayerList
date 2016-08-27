package com.coolv1994.jsonplayerlist.bungee;

import com.coolv1994.jsonplayerlist.IPlugin;
import com.coolv1994.jsonplayerlist.JsonPlayer;
import com.coolv1994.jsonplayerlist.JsonServer;
import com.coolv1994.jsonplayerlist.JsonStats;
import com.coolv1994.jsonplayerlist.PostTask;
import com.coolv1994.jsonplayerlist.Utils;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 *
 * @author Vinnie
 */
public class JPLPlugin extends Plugin implements IPlugin {
    private Configuration config;

    @Override
    public JsonStats getStats() {
        JsonStats stats = new JsonStats();
        stats.setMaxPlayers(getProxy().getConfigurationAdapter().getInt("player_limit", 0));
        for (Map.Entry<String, ServerInfo> serverEntry : getProxy().getServers().entrySet()) {
            JsonServer server = new JsonServer(serverEntry.getValue().getName());
            server.setMotd(serverEntry.getValue().getMotd());
            for (ProxiedPlayer player : serverEntry.getValue().getPlayers()) {
                server.addPlayer(new JsonPlayer(player.getUniqueId(), player.getName(), player.getDisplayName()));
            }
            stats.addServer(server);
        }
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
        getProxy().getScheduler().schedule(this, new PostTask(),
                getConfig().getInt("wait"), getConfig().getInt("time"), TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        Utils.postStatus(false);
    }

    public Configuration getConfig() {
        return config;
    }

    public void reloadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Error loading config.", ex);
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .save(config, new File(getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Error saving config.", ex);
        }
    }

    public void saveDefaultConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException ex) {
                getLogger().log(Level.SEVERE, "Error saving default config.", ex);
            }
        }
    }
}
