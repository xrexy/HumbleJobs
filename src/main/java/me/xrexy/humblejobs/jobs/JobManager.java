package me.xrexy.humblejobs.jobs;

import me.xrexy.humblejobs.HumbleJobs;
import me.xrexy.humblejobs.utils.Utils;
import me.xrexy.humblejobs.utils.YamlFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class JobManager {
    private static class JobFile extends YamlFile {
        JobFile(String name) {
            createConfig(name);
        }
    }

    private static FileConfiguration config;

    public static void generateJobs() {
        JobFile jobFile = new JobFile("jobs.yml");
        JobManager.config = jobFile.getConfig();

        for (String key : config.getKeys(false)) {
            if (config.get(key) != null) {
                try {
                    final JobEventTypes job = JobEventTypes.valueOf(key.toUpperCase());

                    final HashMap<String, Jobs> jobs = new Jobs(Objects.requireNonNull(config.getConfigurationSection(key))).getJobs();
                    job.setEventName(key);
                    job.setValidBlocks(jobs.get(jobs.keySet().toArray()[0]).getValidBlocks());
                    job.setEventJobs(jobs);
                    job.registerEvent();
                } catch (Exception exc) {
                    Utils.debug(exc);
                    Bukkit.getLogger().log(Level.WARNING, "Couldn't load jobs for section " + key);
                }
            }
        }
    }

    public static FileConfiguration getConfig() {
        return config;
    }
}
