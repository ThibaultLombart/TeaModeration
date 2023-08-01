package fr.thibaultlombart.teamoderation.gui;

import fr.thibaultlombart.teamoderation.Informations;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.sound.sampled.Line;
import java.util.*;
import java.util.stream.Collectors;

public class GuiManager {

    public enum TypeGui {
        BANLIST
    }


    public static void createBanList(Player player, int page, BanEntry[] banList) {

        // On crée ici l'interface pour la banList
        Inventory inv = Bukkit.createInventory(null, 54, "§fBanlist");

        // Ici on met donc le tournesol avec la page
        ItemStack tournesol = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta customTournesol = tournesol.getItemMeta();
        customTournesol.setDisplayName("§f"+Informations.getInformations("page.page")+" " + page);
        tournesol.setItemMeta(customTournesol);

        // On l'ajoute a l'inventaire
        inv.setItem(49, tournesol);

        if (banList.length > 0) {
            // Si la liste des items n'est pas vide
            if (banList.length > 45 + 45 * page) {
                ItemStack prochain = new ItemStack(Material.PAPER, 1);
                ItemMeta customProchain = prochain.getItemMeta();
                customProchain.setDisplayName("§f"+ Informations.getInformations("page.next"));
                prochain.setItemMeta(customProchain);

                // On l'ajoute a l'inventaire
                inv.setItem(50, prochain);
            }

            if (page >= 1) {
                ItemStack avant = new ItemStack(Material.PAPER, 1);
                ItemMeta customAvant = avant.getItemMeta();
                customAvant.setDisplayName("§f"+ Informations.getInformations("page.previous"));
                avant.setItemMeta(customAvant);

                // On l'ajoute a l'inventaire
                inv.setItem(48, avant);
            }
            int compteur = 0;
            // Ici on met le début a 0
            int debutLen = 0;

            // Si la page est > a 0, on change pour mettre 45*page
            if (page > 0) {
                debutLen = (45 * page);
            }

            for (int i = debutLen; i < banList.length && i < 45; i++) {
                // On récupere toutes les infos

                BanEntry bannedPeople = banList[i];
                String playerBanned = bannedPeople.getTarget();
                String playerWhoBanned = bannedPeople.getSource();
                Date dateExpiration = bannedPeople.getExpiration();
                Date dateBanned = bannedPeople.getCreated();
                String reason = bannedPeople.getReason();

                String time = Informations.getInformations("time.permanently");

                if(dateExpiration != null){
                    time = Informations.getTime(dateBanned,dateExpiration);

                }


                boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");

                Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");

                ItemStack skull = new ItemStack(type, 1);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                meta.setOwner(playerBanned);
                skull.setItemMeta(meta);

                // On rentre les infos
                ItemMeta itemMeta = skull.getItemMeta();
                List<String> lore = new ArrayList<>();


                lore.add("§f--------------------");
                lore.add("§a" + playerBanned);
                lore.add("§a" + Informations.getInformations("banlist.banned"));
                lore.add("§9" + Informations.getInformations("banlist.by") + " §f" + playerWhoBanned);
                lore.add("§9" + Informations.getInformations("banlist.reason") + " §f" + reason);
                lore.add("§9" + Informations.getInformations("banlist.time") + " §f" + time);
                lore.add("§9" + Informations.getInformations("banlist.when") + " §f" + dateBanned);
                lore.add("§9" + Informations.getInformations("banlist.expiration") + " §f" + dateExpiration);
                lore.add("§f--------------------");
                itemMeta.setLore(lore);
                skull.setItemMeta(itemMeta);

                inv.setItem(compteur, skull);
                compteur++;

            }



        }

        player.openInventory(inv);

    }

    public static void createPardon(Player player, ItemStack skull) {
        // On crée ici l'interface pour la banList
        Inventory inv = Bukkit.createInventory(null, 9, "§fPardon");

        inv.setItem(4,skull);

        ItemStack pardon = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta customPardon = pardon.getItemMeta();
        customPardon.setDisplayName("§f"+Informations.getInformations("pardon.unban"));
        pardon.setItemMeta(customPardon);

        inv.setItem(6,pardon);

        ItemStack notPardon = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta customNotPardon = notPardon.getItemMeta();
        customNotPardon.setDisplayName("§f"+Informations.getInformations("pardon.cancel"));
        notPardon.setItemMeta(customNotPardon);

        inv.setItem(2,notPardon);

        player.openInventory(inv);
    }
}