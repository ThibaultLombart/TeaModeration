package fr.thibaultlombart.teamoderation.gui;

import fr.thibaultlombart.teamoderation.Informations;
import fr.thibaultlombart.teamoderation.database.DatabaseController;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.MessageFormat;
import java.util.Date;

public class ListenersTbanlist implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase("§fBanlist")){
            event.setCancelled(true);
            if(event.getCurrentItem() != null){
                ItemStack itemClicked = event.getCurrentItem();
                Player player = (Player) event.getWhoClicked();
                Inventory inv = event.getClickedInventory();
                BanEntry[] banList = Bukkit.getBanList(BanList.Type.NAME).getBanEntries().toArray(new BanEntry[0]);
                if(itemClicked.getItemMeta().getDisplayName().equals("§f"+ Informations.getInformations("page.next"))){
                    String page = inv.getItem(49).getItemMeta().getDisplayName();
                    String[] str = page.split(" ");
                    int nbPage = Integer.parseInt(str[1]);
                    GuiManager.createBanList(player,nbPage+1,banList);
                } else if(itemClicked.getItemMeta().getDisplayName().equals("§f"+ Informations.getInformations("page.previous"))){
                    String page = inv.getItem(49).getItemMeta().getDisplayName();
                    String[] str = page.split(" ");
                    int nbPage = Integer.parseInt(str[1]);
                    GuiManager.createBanList(player,nbPage-1,banList);
                } else {
                    if(event.getSlot() >= 0 && event.getSlot() <= 45){
                        player.closeInventory();
                        GuiManager.createPardon(player,itemClicked);
                    }
                }
            }


        } else if(event.getView().getTitle().equalsIgnoreCase("§fPardon")){
            event.setCancelled(true);
            if(event.getCurrentItem() != null){
                ItemStack itemClicked = event.getCurrentItem();
                Player player = (Player) event.getWhoClicked();
                Inventory inv = event.getClickedInventory();
                if(itemClicked.getItemMeta().getDisplayName().equals("§f"+Informations.getInformations("pardon.unban"))){
                    if(player.hasPermission("minecraft.command.pardon")){
                        player.sendMessage(String.valueOf(player.hasPermission("minecraft.command.pardon")));
                        SkullMeta skullMeta = (SkullMeta) inv.getItem(4).getItemMeta();
                        String namedBan = skullMeta.getOwner();




                        OfflinePlayer playerBanned = Bukkit.getOfflinePlayer(namedBan);



                        Bukkit.getBanList(BanList.Type.NAME).pardon(namedBan);
                        player.sendMessage("§f"+ MessageFormat.format(Informations.getInformations("pardon.message"),namedBan));

                        Date date = new Date();

                        DatabaseController.addLine(playerBanned.getUniqueId(),namedBan,"Unban","0",player,new Date());

                        player.closeInventory();
                    } else {
                        Informations.noPermission(itemClicked,inv,6,"minecraft.command.pardon");
                    }
                } else if(itemClicked.getItemMeta().getDisplayName().equals("§f"+Informations.getInformations("pardon.cancel"))){
                    player.closeInventory();
                }
            }
        }
    }
}
