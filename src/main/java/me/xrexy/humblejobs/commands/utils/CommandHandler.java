package me.xrexy.humblejobs.commands.utils;

import me.xrexy.humblejobs.HumbleJobs;
import me.xrexy.humblejobs.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {
    public final HashMap<String, CommandInterface> commands = new HashMap<>();

    public void register(String name, CommandInterface cmd) {
        commands.put(name, cmd);
    }

    public CommandInterface getExecutor(String name) {
        return commands.get(name);
    }

    private final String mainCommand;

    public CommandHandler(String mainCommand) {
        this.mainCommand = mainCommand;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                execute(mainCommand, sender, cmd, commandLabel, args);
                return true;
            }

            if (args.length > 0) {
                if (commands.containsKey(args[0])) {
                    execute(args[0], sender, cmd, commandLabel, args);
                } else
                    Utils.sendMessage(((Player) sender), "messages.invalid-args");

                return true;
            }

        } else {
            sender.sendMessage(Utils.process("%prefix% &cOnly players can execute this command!"));
            return true;
        }
        return false;
    }

    private void execute(String command, CommandSender sender, Command cmd, String commandLabel, String[] args) {
        getExecutor(command).onCommand(sender, cmd, commandLabel, args);
    }

    public String getMainCommand() {
        return mainCommand;
    }
}
