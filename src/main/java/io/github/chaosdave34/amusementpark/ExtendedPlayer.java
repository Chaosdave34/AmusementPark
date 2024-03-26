package io.github.chaosdave34.amusementpark;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ExtendedPlayer {
    private final UUID uuid;

    public ExtendedPlayer(Player p) {
        uuid = p.getUniqueId();
    }

    public static ExtendedPlayer from(UUID uuid) {
        return from(Bukkit.getPlayer(uuid));
    }

    public static ExtendedPlayer from(Player player) {
        return AmusementPark.INSTANCE.getExtendedPlayer(player);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
