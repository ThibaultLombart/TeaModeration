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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        customTournesol.setDisplayName("§fPage " + page);
        tournesol.setItemMeta(customTournesol);

        // On l'ajoute a l'inventaire
        inv.setItem(49, tournesol);

        if (banList.length > 0) {
            // Si la liste des items n'est pas vide
            if (banList.length > 45 + 45 * page) {
                ItemStack prochain = new ItemStack(Material.PAPER, 1);
                ItemMeta customProchain = prochain.getItemMeta();
                customProchain.setDisplayName("§fProchaine Page");
                prochain.setItemMeta(customProchain);

                // On l'ajoute a l'inventaire
                inv.setItem(50, prochain);
            }

            if (page >= 1) {
                ItemStack avant = new ItemStack(Material.PAPER, 1);
                ItemMeta customAvant = avant.getItemMeta();
                customAvant.setDisplayName("§fPrecedente Page");
                avant.setItemMeta(customAvant);

                // On l'ajoute a l'inventaire
                inv.setItem(48, avant);
            }
            int compteur = 0;
            if (page == 0) {
                for (int i = 0; i < banList.length && i < 45; i++) {
                    // On récupere toutes les infos

                    BanEntry bannedPeople = banList[i];
                    String playerBanned = bannedPeople.getTarget();
                    String playerWhoBanned = bannedPeople.getSource();
                    Date dateExpiration = bannedPeople.getExpiration();
                    Date dateBanned = bannedPeople.getCreated();
                    String reason = bannedPeople.getReason();

                    ItemStack skull = new ItemStack(Material.LEGACY_SKULL, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.LEGACY_SKULL);
                    meta.setOwner(playerBanned);
                    skull.setItemMeta(meta);

                    // On rentre les infos
                    ItemMeta itemMeta = skull.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    ;

                    lore.add("§f--------------------");
                    lore.add("§a" + playerBanned);
                    lore.add("§a" + Informations.getInformations("banlist.banned"));
                    lore.add("§9" + Informations.getInformations("banlist.by") + " §f" + playerWhoBanned);
                    lore.add("§9" + Informations.getInformations("banlist.reason") + " §f" + reason);
                    lore.add("§9" + Informations.getInformations("banlist.when") + " §f" + dateBanned);
                    lore.add("§9" + Informations.getInformations("banlist.expiration") + " §f" + dateExpiration);
                    lore.add("§f--------------------");
                    itemMeta.setLore(lore);
                    skull.setItemMeta(itemMeta);

                    inv.setItem(compteur, skull);
                    compteur++;

                }
            } else {
                for (int i = (45 * page); i < banList.length && i < (45 * page) + 45; i++) {
                    // On récupere toutes les infos
                    BanEntry bannedPeople = banList[i];
                    String playerBanned = bannedPeople.getTarget();
                    String playerWhoBanned = bannedPeople.getSource();
                    Date dateExpiration = bannedPeople.getExpiration();
                    Date dateBanned = bannedPeople.getCreated();
                    String reason = bannedPeople.getReason();

                    ItemStack skull = new ItemStack(Material.LEGACY_SKULL, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.LEGACY_SKULL);
                    meta.setOwner(playerBanned);
                    skull.setItemMeta(meta);

                    // On rentre les infos
                    ItemMeta itemMeta = skull.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    ;

                    lore.add("§f--------------------");
                    lore.add("§a" + playerBanned);
                    lore.add("§a" + Informations.getInformations("banlist.banned"));
                    lore.add("§9" + Informations.getInformations("banlist.by") + " §f" + playerWhoBanned);
                    lore.add("§9" + Informations.getInformations("banlist.reason") + " §f" + reason);
                    lore.add("§9" + Informations.getInformations("banlist.when") + " §f" + dateBanned);
                    lore.add("§9" + Informations.getInformations("banlist.expiration") + " §f" + dateExpiration);
                    lore.add("§f--------------------");
                    itemMeta.setLore(lore);
                    skull.setItemMeta(itemMeta);

                    inv.setItem(compteur, skull);
                    compteur++;
                }
            }


        }

    }
}