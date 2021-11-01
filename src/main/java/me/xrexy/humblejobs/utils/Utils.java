package me.xrexy.humblejobs.utils;

import me.xrexy.humblejobs.HumbleJobs;
import me.xrexy.humblejobs.jobs.JobLevel;
import me.xrexy.humblejobs.players.HumblePlayer;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


public class Utils {
    private static class Placeholder {

        private final String placeholder, replacement;

        public Placeholder(String placeholder, String replacement) {
            this.placeholder = placeholder;
            this.replacement = replacement;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public String getReplacement() {
            return replacement;
        }

        public static String processPlaceholders(String message, Placeholder... placeholders) {
            for (Placeholder p : placeholders)
                message = message.replace(p.getPlaceholder(), p.getReplacement());

            return colorize(message);
        }

        public static ArrayList<String> processPlaceholders(List<String> messages, Placeholder... placeholders) {
            ArrayList<String> output = new ArrayList<>();
            for (String line : messages) {
                for (Placeholder p : placeholders)
                    line = line.replace(p.getPlaceholder(), p.getReplacement());

                output.add(colorize(line));
            }
            return output;
        }
    }

    private static final HumbleJobs humbleJobs = HumbleJobs.getInstance();
    private static final FileConfiguration config = humbleJobs.getConfig();
    private static final Economy eco = HumbleJobs.getEcon();

    public static String getString(String path) throws NullPointerException {
        return config.getString(path);
    }

    public static void sendMessage(Player p, String path) {
        p.sendMessage(process(getString(path), p));
    }

    public static void sendText(Player p, String message) {
        p.sendMessage(colorize(message));
    }

    public static void sendMultilineMessage(Player sender, String path) {
        StringBuilder output = new StringBuilder();
        for (String s : Placeholder.processPlaceholders(config.getStringList(path),
                new Placeholder("%prefix%", getString("prefix"))))
            output.append(s).append('\n');
        sender.sendMessage(output.toString());
    }

    public static void sendMultilineMessage(Player sender, Player target, String path) {
        HumblePlayer humblePlayer = new HumblePlayer(target);
        JobLevel jobLevel = humblePlayer.getPlayerJobLevel();
        JobLevel nextJobLevel = humblePlayer.getNextPlayerJobLevel();

        double xp = humblePlayer.getXp();
        double needed_xp = nextJobLevel.getXpNeeded() - xp;

        for (String s : Placeholder.processPlaceholders(config.getStringList(path),
                new Placeholder("%prefix%", getString("prefix")),
                new Placeholder("%player%", target.getName()),
                new Placeholder("%job%", humblePlayer.getJob()),
                new Placeholder("%level%", humblePlayer.getLevel() + ""),
                new Placeholder("%xp%", xp + ""),
                new Placeholder("%level_display%", colorize(jobLevel.getDisplay())),
                new Placeholder("%next_level%", colorize(nextJobLevel.getDisplay())),
                new Placeholder("%needed_xp%", needed_xp < 0 ? getString("messages.xp-max") : needed_xp + "")))
            sender.sendMessage(s);
    }

    public static void sendMultilineMessage(ConsoleCommandSender sender, Player target, String path) {
        HumblePlayer humblePlayer = new HumblePlayer(target);
        JobLevel jobLevel = humblePlayer.getPlayerJobLevel();
        JobLevel nextJobLevel = humblePlayer.getNextPlayerJobLevel();

        double xp = humblePlayer.getXp();
        double needed_xp = nextJobLevel.getXpNeeded() - xp;

        for (String s : Placeholder.processPlaceholders(config.getStringList(path),
                new Placeholder("%prefix%", getString("prefix")),
                new Placeholder("%player%", target.getName()),
                new Placeholder("%job%", humblePlayer.getJob()),
                new Placeholder("%level%", humblePlayer.getLevel() + ""),
                new Placeholder("%xp%", xp + ""),
                new Placeholder("%level_display%", colorize(jobLevel.getDisplay())),
                new Placeholder("%next_level%", colorize(nextJobLevel.getDisplay())),
                new Placeholder("%needed_xp%", needed_xp < 0 ? getString("messages.xp-max") : needed_xp + "")))
            sender.sendMessage(s);
    }

    public static void debug(Exception exception) {
        if (humbleJobs.getConfig().getBoolean("debug"))
            exception.printStackTrace();
    }

    public static String process(String input) {
        return Placeholder.processPlaceholders(input,
                new Placeholder("%prefix%", getString("prefix")));
    }

    private static String process(String input, Player player) {
        HumblePlayer humblePlayer = new HumblePlayer(player);
        JobLevel jobLevel = humblePlayer.getPlayerJobLevel();
        JobLevel nextJobLevel = humblePlayer.getNextPlayerJobLevel();

        double xp = humblePlayer.getXp();
        double needed_xp = nextJobLevel.getXpNeeded() - xp;

        return Placeholder.processPlaceholders(input,
                new Placeholder("%prefix%", getString("prefix")),
                new Placeholder("%player%", player.getName()),
                new Placeholder("%job%", colorize(humblePlayer.getJob())),
                new Placeholder("%level%", humblePlayer.getLevel() + ""),
                new Placeholder("%xp%", xp + ""),
                new Placeholder("%level_display%", colorize(jobLevel.getDisplay())),
                new Placeholder("%next_level%", colorize(nextJobLevel.getDisplay())),
                new Placeholder("%needed_xp%", needed_xp < 0 ? getString("messages.xp-max") : needed_xp + "")
        );
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void pay(Player p, double price) {
        EconomyResponse er = eco.depositPlayer(p, price);
        if (er.transactionSuccess() && config.getBoolean("economy-messages.deposit-toggle"))
            sendText(p, process(getString("economy-messages.deposit-success").replace("%amount%", format((long) price))));
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}