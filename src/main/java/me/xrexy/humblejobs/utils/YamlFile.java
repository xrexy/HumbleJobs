package me.xrexy.humblejobs.utils;

import me.xrexy.humblejobs.HumbleJobs;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class YamlFile {
    private static final HumbleJobs humbleJobs = HumbleJobs.getInstance();
    private static File file;
    private static FileConfiguration config;

    public void createConfig(String name) {
        file = new File(humbleJobs.getDataFolder(), name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            humbleJobs.saveResource(name, false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
