package fr.thibaultlombart.teamoderation;

import fr.thibaultlombart.teamoderation.ban.CommandTbanlist;
import fr.thibaultlombart.teamoderation.database.DatabaseManager;
import fr.thibaultlombart.teamoderation.gui.ListenersTbanlist;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeaModeration extends JavaPlugin {

    private DatabaseManager databaseManager;

    private static TeaModeration instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();
        Informations.init(getConfig());

        getCommand("tbanlist").setExecutor(new CommandTbanlist());
        getServer().getPluginManager().registerEvents(new ListenersTbanlist(), this);

        databaseManager = new DatabaseManager();
        Informations.initMain(this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.databaseManager.close();
    }

    public static TeaModeration getInstance(){
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
