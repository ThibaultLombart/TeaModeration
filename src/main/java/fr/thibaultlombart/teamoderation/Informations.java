package fr.thibaultlombart.teamoderation;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Date;

public class Informations {

    private static String prefix;

    private static TeaModeration main;

    private static FileConfiguration fileConfig;



    private Informations() {
        throw new IllegalStateException("Utility class");
    }

    // Initialisation des infos
    public static void init(FileConfiguration file){
        fileConfig = file;
    }


    public static void initMain(TeaModeration mainRequest){
        main = mainRequest;
    }

    public static void reload(){
        fileConfig = main.getConfig();
    }



    // Errors :
    public static String getError(String message){
        return fileConfig.getString(message);
    }

    public static String getInformations(String message){
        return fileConfig.getString(message);
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
                time += years + " " + Informations.getInformations("time.years");
            } else {
                time += years + " " + Informations.getInformations("time.year");
            }
        }

        if(months > 0){
            if(months > 1){
                time += months + " " + Informations.getInformations("time.months");
            } else {
                time += months + " " + Informations.getInformations("time.month");
            }
        }

        if(days > 0){
            if(days > 1){
                time += days + " " + Informations.getInformations("time.days");
            } else {
                time += days + " " + Informations.getInformations("time.day");
            }
        }

        if(hours > 0){
            if(hours > 1){
                time += hours + " " + Informations.getInformations("time.hours");
            } else {
                time += hours + " " + Informations.getInformations("time.hour");
            }
        }

        if(minutes > 0){
            if(minutes > 1){
                time += minutes + " " + Informations.getInformations("time.minutes");
            } else {
                time += minutes + " " + Informations.getInformations("time.minute");
            }
        }

        if(seconds > 0){
            if(seconds > 1){
                time += seconds + " " + Informations.getInformations("time.seconds");
            } else {
                time += seconds + " " + Informations.getInformations("time.second");
            }
        }

        return time;
    }


}
