package io.github.chaosdave34.amusementpark;

import io.github.chaosdave34.amusementpark.listener.UtilityListener;
import io.github.chaosdave34.ghutils.GHUtils;
import io.github.chaosdave34.ghutils.utils.JsonUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
public final class AmusementPark extends JavaPlugin {
    public static AmusementPark INSTANCE;

    private final Map<UUID, ExtendedPlayer> extendedPlayers = new HashMap<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        INSTANCE = this;
        GHUtils.setPlugin(INSTANCE);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new UtilityListener(), this);

        // Create data folder
        getDataFolder().mkdir();
        new File(getDataFolder(), "player_data").mkdir();
    }

    public ExtendedPlayer getExtendedPlayer(Player p) {
        return extendedPlayers.get(p.getUniqueId());
    }

    public void createExtendedPlayer(Player p) {
        File playerData = new File(getDataFolder(), "player_data/" + p.getUniqueId() + ".json");

        ExtendedPlayer extendedPlayer = null;
        if (playerData.exists())
            extendedPlayer = JsonUtils.readObjectFromFile(playerData, ExtendedPlayer.class);
        if (extendedPlayer == null)
            extendedPlayer = new ExtendedPlayer(p);

        extendedPlayers.put(p.getUniqueId(), extendedPlayer);
    }

    public void removeExtendedPlayer(Player p) {
        ExtendedPlayer extendedPlayer = getExtendedPlayer(p);

        JsonUtils.writeObjectToFile(new File(getDataFolder(), "player_data/" + p.getUniqueId() + ".json"), extendedPlayer);

        extendedPlayers.remove(p.getUniqueId());
    }

}
