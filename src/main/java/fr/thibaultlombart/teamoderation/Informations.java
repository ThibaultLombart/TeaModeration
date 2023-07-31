package fr.thibaultlombart.teamoderation;

import org.bukkit.configuration.file.FileConfiguration;

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


}
