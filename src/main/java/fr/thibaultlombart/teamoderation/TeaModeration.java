package fr.thibaultlombart.teamoderation;

import fr.thibaultlombart.teamoderation.ban.CommandTbanlist;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeaModeration extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Informations.init(getConfig());

        getCommand("tbanlist").setExecutor(new CommandTbanlist());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
