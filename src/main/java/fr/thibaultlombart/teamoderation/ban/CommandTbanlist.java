package fr.thibaultlombart.teamoderation.ban;

import fr.thibaultlombart.teamoderation.gui.GuiManager;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTbanlist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player player) {
            BanEntry[] banList = Bukkit.getBanList(BanList.Type.NAME).getBanEntries().toArray(new BanEntry[0]);

            GuiManager.createBanList(player,0,banList);

        }



        return false;
    }
}
