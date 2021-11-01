package me.xrexy.humblejobs.commands;

import me.xrexy.humblejobs.HumbleJobs;
import me.xrexy.humblejobs.commands.utils.CommandInterface;
import me.xrexy.humblejobs.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandInterface {
    @Override
    public String getCommand() {
        return HumbleJobs.getInstance().MAIN_COMMAND;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Utils.sendMultilineMessage((Player) sender, "messages.help");
        return true;
    }
}
