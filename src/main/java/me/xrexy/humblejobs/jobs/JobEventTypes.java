package me.xrexy.humblejobs.jobs;

import lombok.Getter;
import me.xrexy.humblejobs.HumbleJobs;
import me.xrexy.humblejobs.jobs.events.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Getter
public enum JobEventTypes {
    BLOCK_BREAK(BlockBreak.class),
    BLOCK_PLACE(BlockPlace.class),
    PLAYER_KILL(PlayerKill.class),
    FISH(Fish.class),
    ENCHANT(Enchant.class),
    BREW(Brew.class),
    CRAFT(Craft.class);

    JobEventTypes(Class<?> event) {
        this.event = event;
    }

    private final Class<?> event;

    private String eventName;

    public ConfigurationSection getConfigurationSection() { return JobManager.getConfig().getConfigurationSection(eventName); }

    public void setEventName(String toSet) { this.eventName = toSet; }

    private HashMap<String, Jobs> eventJobs = new HashMap<>();

    private ArrayList<String> validBlocks = new ArrayList<>();

    public void setValidBlocks(ArrayList<String> list) {
        this.validBlocks = list;
    }

    public Set<String> getJobTitles() {
        return eventJobs.keySet();
    }

    public void registerEvent() throws IllegalAccessException, InstantiationException {
        Bukkit.getPluginManager().registerEvents((Listener) event.newInstance(), HumbleJobs.getInstance());
    }

    public void setEventJobs(HashMap<String, Jobs> eventJobs) {
        this.eventJobs = eventJobs;
    }
}
