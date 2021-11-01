package me.xrexy.humblejobs.commands.utils;

import lombok.Getter;
import me.xrexy.humblejobs.jobs.Jobs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MainTabCompleter implements TabCompleter {
    private final String main_command;
    private final ArrayList<String> subcommands = new ArrayList<>();

    public MainTabCompleter(CommandHandler commandHandler) {
        main_command = commandHandler.getMainCommand();

        for (String subcommand : commandHandler.commands.keySet())
            if (!subcommand.equalsIgnoreCase(main_command))
                subcommands.add(subcommand);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase(main_command)) {
            if (args.length == 1)
                return subcommands;

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("join"))
                    return Jobs.jobNames;
            }

            if (args.length > 2)
                return new ArrayList<>();
        }
        return null;
    }
}
