package me.xrexy.humblejobs.commands.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {
    String getCommand();

    boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);
}
