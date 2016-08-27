package com.coolv1994.jsonplayerlist.bukkit;

import com.coolv1994.jsonplayerlist.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author CoolV1994
 */
public class CommandSync implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils.postPlayerList();
        return true;
    }

}
