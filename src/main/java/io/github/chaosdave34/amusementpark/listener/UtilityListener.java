package io.github.chaosdave34.amusementpark.listener;

import io.github.chaosdave34.amusementpark.AmusementPark;
import io.github.chaosdave34.ghutils.utils.JsonUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.github.chaosdave34.amusementpark.ExtendedPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.File;

public class UtilityListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Component message = Component.text(p.getName() + " is visiting the amusement park!", NamedTextColor.DARK_GRAY);
        e.joinMessage(message);

        AmusementPark.INSTANCE.createExtendedPlayer(p);

        // player list header
        p.sendPlayerListHeader(Component.text("Amusement Park", NamedTextColor.YELLOW, TextDecoration.BOLD));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        Component message = Component.text(p.getName() + " has left the amusement park!", NamedTextColor.DARK_GRAY);
        e.quitMessage(message);

        AmusementPark.INSTANCE.removeExtendedPlayer(p);
    }

    @EventHandler
    public void onChatMessage(AsyncChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();

        Component message = p.displayName()
                .append(Component.text(" >> "))
                .append(e.message());

        Bukkit.broadcast(message);
    }

    @EventHandler
    public void onGameSave(WorldSaveEvent e) {
        if (e.getWorld().getName().equals("world")) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                ExtendedPlayer extendedPlayer = ExtendedPlayer.from(p);
                JsonUtils.writeObjectToFile(new File(AmusementPark.INSTANCE.getDataFolder(), "player_data/" + p.getUniqueId() + ".json"), extendedPlayer);
            }
        }
    }
}
