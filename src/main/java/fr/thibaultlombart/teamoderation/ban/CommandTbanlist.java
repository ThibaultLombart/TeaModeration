package fr.thibaultlombart.teamoderation.ban;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTbanlist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg0, String[] args) {

        BanEntry[] banList = Bukkit.getBanList(BanList.Type.NAME).getBanEntries().toArray(new BanEntry[0]);

        return false;
    }
}
