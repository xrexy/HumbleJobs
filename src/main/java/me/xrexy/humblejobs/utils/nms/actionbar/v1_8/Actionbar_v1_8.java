package me.xrexy.humblejobs.utils.nms.actionbar.v1_8;

import me.xrexy.humblejobs.utils.Utils;
import me.xrexy.humblejobs.utils.nms.actionbar.Actionbar;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Actionbar_v1_8 implements Actionbar {
    @Override
    public void sendActionbar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(Utils.colorize(message)), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }



    //use code spitzy in the fortnite item shop #ad
}
