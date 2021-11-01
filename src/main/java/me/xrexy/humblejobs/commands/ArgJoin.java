package me.xrexy.humblejobs.commands;

import me.xrexy.humblejobs.commands.utils.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ArgJoin implements CommandInterface {
    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return false;
    }
}
