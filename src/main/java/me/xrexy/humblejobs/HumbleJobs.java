package me.xrexy.humblejobs;

import me.xrexy.humblejobs.commands.ArgCheck;
import me.xrexy.humblejobs.commands.ArgJoin;
import me.xrexy.humblejobs.commands.ArgReset;
import me.xrexy.humblejobs.commands.MainCommand;
import me.xrexy.humblejobs.commands.utils.CommandHandler;
import me.xrexy.humblejobs.commands.utils.MainTabCompleter;
import me.xrexy.humblejobs.jobs.JobManager;
import me.xrexy.humblejobs.players.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class HumbleJobs extends JavaPlugin {
    private static HumbleJobs instance;
    private static Economy econ = null;
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    public VersionManager versionManager;
    public final String MAIN_COMMAND = "humblejobs";

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setInstance(this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        JobManager.generateJobs();

        registerCommands();

        pluginManager.registerEvents(new PlayerManager(), this);

        if (Bukkit.getOnlinePlayers().size() > 0)
            Bukkit.getOnlinePlayers().forEach(PlayerManager::addPlayer);

        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[1].replace(".", "");

        versionManager = new VersionManager(version);
    }

    // TODO onDisable

    private void registerCommands() {
        CommandHandler commandHandler = new CommandHandler(MAIN_COMMAND);

        PluginCommand mainPluginCommand = getCommand(MAIN_COMMAND);

        if (mainPluginCommand == null) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled, couldn't load commands!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // MAIN COMMAND
        mainPluginCommand.setExecutor(commandHandler);

        MainCommand mainCommand = new MainCommand();
        commandHandler.register(mainCommand.getCommand(), mainCommand);

        // SUB-COMMANDS
        ArgCheck check = new ArgCheck();
        commandHandler.register(check.getCommand(), check);

        ArgReset reset = new ArgReset();
        commandHandler.register(reset.getCommand(), reset);

        ArgJoin join = new ArgJoin();
        commandHandler.register(join.getCommand(), join);

        mainPluginCommand.setTabCompleter(new MainTabCompleter(commandHandler));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getLogger().severe(String.format("[%s] - No economy plugin found!", getDescription().getName()));
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEcon() {
        return HumbleJobs.econ;
    }

    private static void setInstance(HumbleJobs instance) {
        HumbleJobs.instance = instance;
    }

    public static HumbleJobs getInstance() {
        return instance;
    }


}
