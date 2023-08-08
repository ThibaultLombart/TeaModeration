package fr.thibaultlombart.teamoderation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Date;

import static org.bukkit.Bukkit.getServer;

public class Informations {

    private static String prefix;

    private static TeaModeration main;

    private static FileConfiguration fileConfig;

    private static FileConfiguration fileLang;



    private Informations() {
        throw new IllegalStateException("Utility class");
    }

    // Initialisation des infos
    public static void init(FileConfiguration file, TeaModeration mainRequest){
        // Init Configs
        fileConfig = file;
        main = mainRequest;

        // Init Lang
        createAllLangs();

        String lang = fileConfig.getString("lang");
        File defaultLanguageFile = new File(main.getDataFolder(), "languages/"+lang+".yml");

        if(defaultLanguageFile.exists()){
            fileLang = YamlConfiguration.loadConfiguration(defaultLanguageFile);
        } else {
            System.out.println("[TeaModeration] Error, no lang detected");
            Bukkit.shutdown();
        }

    }

    public static void reload(){
        fileConfig = main.getConfig();
        String lang = fileConfig.getString("lang");
        File defaultLanguageFile = new File(main.getDataFolder(), "languages/"+lang+".yml");

        if(defaultLanguageFile.exists()){
            fileLang = YamlConfiguration.loadConfiguration(defaultLanguageFile);
        } else {
            System.out.println("[TeaModeration] Error, no lang detected");
            Bukkit.shutdown();
        }
    }



    // Errors :
    public static String getErrors(String message){

        return fileLang.getString(message);
    }

    public static String getLangInfos(String message){
        return fileLang.getString(message);
    }

    public static String getParameters(String message){
        return fileConfig.getString(message);
    }

    public static int getParametersInt(String message){
        return fileConfig.getInt(message);
    }


    public static String getTime(Date dateBanned,Date dateExpiration){
        String time = "";
        long tempsRestantMillis = dateExpiration.getTime() - dateBanned.getTime();

        long seconds = tempsRestantMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;

        months %= 12;
        days %= 30;
        hours %= 24;
        minutes %= 60;
        seconds %= 60;

        if(years > 0){
            if(years > 1){
                time += " " + years + " " + Informations.getLangInfos("time.years");
            } else {
                time += " " + years + " " + Informations.getLangInfos("time.year");
            }
        }

        if(months > 0){
            if(months > 1){
                time += " " + months + " " + Informations.getLangInfos("time.months");
            } else {
                time += " " + months + " " + Informations.getLangInfos("time.month");
            }
        }

        if(days > 0){
            if(days > 1){
                time += " " + days + " " + Informations.getLangInfos("time.days");
            } else {
                time += " " + days + " " + Informations.getLangInfos("time.day");
            }
        }

        if(hours > 0){
            if(hours > 1){
                time += " " + hours + " " + Informations.getLangInfos("time.hours");
            } else {
                time += " " + hours + " " + Informations.getLangInfos("time.hour");
            }
        }

        if(minutes > 0){
            if(minutes > 1){
                time += " " + minutes + " " + Informations.getLangInfos("time.minutes");
            } else {
                time += " " + minutes + " " + Informations.getLangInfos("time.minute");
            }
        }

        if(seconds > 0){
            if(seconds > 1){
                time += " " + seconds + " " + Informations.getLangInfos("time.seconds");
            } else {
                time += " " + seconds + " " + Informations.getLangInfos("time.second");
            }
        }

        return time;
    }

    public static void noPermission(ItemStack itemStack, Inventory inv, int i, String permission){
        ItemStack noPerm = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta customNoPerm = noPerm.getItemMeta();
        customNoPerm.setDisplayName("§c"+ MessageFormat.format(Informations.getLangInfos("errors.permission").replaceAll("'", "''"),permission));
        noPerm.setItemMeta(customNoPerm);

        inv.setItem(i,noPerm);
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                inv.setItem(i,itemStack);
            }
        }, 60L);

    }

    private static void createAllLangs(){
        File languageDirectory = new File(main.getDataFolder(), "languages/");
        File languageEN = new File(main.getDataFolder(), "languages/en_US.yml");
        File languageFR = new File(main.getDataFolder(), "languages/fr_FR.yml");

        if (!languageDirectory.isDirectory()){
            languageDirectory.mkdir();
        }

        if(!languageEN.exists()){
            InputStream link = main.getResource("langs/en_US.yml");
            try{
                Files.copy(link, languageEN.getAbsoluteFile().toPath());
            } catch (Exception e){
                System.out.println("Error Creation Langs File " + e);
            }

        }
        if(!languageFR.exists()){
            InputStream link = main.getResource("langs/fr_FR.yml");
            try{
                Files.copy(link, languageFR.getAbsoluteFile().toPath());
            } catch (Exception e){
                System.out.println("Error Creation Langs File " + e);
            }

        }
    }


}
