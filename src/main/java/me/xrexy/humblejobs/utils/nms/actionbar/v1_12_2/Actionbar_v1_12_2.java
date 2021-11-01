package me.xrexy.humblejobs.utils.nms.actionbar.v1_12_2;

import me.xrexy.humblejobs.utils.Utils;
import me.xrexy.humblejobs.utils.nms.actionbar.Actionbar;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Actionbar_v1_12_2 implements Actionbar {
    @Override
    public void sendActionbar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(Utils.colorize(message)), ChatMessageType.GAME_INFO);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


    //use code spitzy in the fortnite item shop #ad
}
