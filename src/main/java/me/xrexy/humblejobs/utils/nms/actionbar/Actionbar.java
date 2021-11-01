package me.xrexy.humblejobs.utils.nms.actionbar;

import org.bukkit.entity.Player;

public interface Actionbar {
    public void sendActionbar(Player player, String actionbar);
//        (p.getHandle()).playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR,
//                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + actionbar + "\"}")));

}
