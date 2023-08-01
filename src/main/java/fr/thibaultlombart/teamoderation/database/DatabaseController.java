package fr.thibaultlombart.teamoderation.database;

import fr.thibaultlombart.teamoderation.TeaModeration;
import fr.thibaultlombart.teamoderation.exceptions.SqlErrorException;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class DatabaseController {

    private static TeaModeration main;
    private DatabaseController(){
        throw new IllegalStateException("Utility class");
    }

    public static void init(){
        main = TeaModeration.getInstance();
    }

    // Addline in MySQL when we have unban date
    public static void addLine(UUID uuidBanned, String namedBan, String typeOfSanction, String time, Player playerWhoBanned, Date when, Date whenUnban){
        final DbConnection databaseManager = main.getDatabaseManager().getDbConnection();

        if(whenUnban == null){

            try (Connection connection = databaseManager.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logsTeaModeration(UUID_BANNED,NICKNAME_BANNED,TYPE_OF_SANCTION,TIME,UUID_WHO_BANNED,NICKNAME_WHO_BANNED,WHEN_SANCTION) VALUES (?,?,?,?,?,?,?)");){

                java.sql.Date sqlDate = new java.sql.Date(when.getTime());

                preparedStatement.setString(1,uuidBanned.toString());
                preparedStatement.setString(2,namedBan);
                preparedStatement.setString(3,typeOfSanction);
                preparedStatement.setString(4,time);
                preparedStatement.setString(5,playerWhoBanned.getUniqueId().toString());
                preparedStatement.setString(6,playerWhoBanned.getDisplayName());
                preparedStatement.setDate(7, sqlDate);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new SqlErrorException(e + " ----- Add Line Error");
            }

        } else {
            try (Connection connection = databaseManager.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logsTeaModeration(UUID_BANNED,NICKNAME_BANNED,TYPE_OF_SANCTION,TIME,UUID_WHO_BANNED,NICKNAME_WHO_BANNED,WHEN_SANCTION,WHEN_UNSANCTION) VALUES (?,?,?,?,?,?,?,?)");){

                java.sql.Date sqlDate = new java.sql.Date(when.getTime());
                java.sql.Date sqlDate2 = new java.sql.Date(whenUnban.getTime());

                preparedStatement.setString(1,uuidBanned.toString());
                preparedStatement.setString(2,namedBan);
                preparedStatement.setString(3,typeOfSanction);
                preparedStatement.setString(4,time);
                preparedStatement.setString(5,playerWhoBanned.getUniqueId().toString());
                preparedStatement.setString(6,playerWhoBanned.getDisplayName());
                preparedStatement.setDate(7, sqlDate);
                preparedStatement.setDate(8, sqlDate2);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new SqlErrorException(e + " ----- Add Line Error");
            }
        }
    }

    // Addline in MySQL when we don't have unban date
    public static void addLine(UUID uuidBanned,String namedBan, String typeOfSanction, String time, Player playerWhoBanned, Date when){
        addLine(uuidBanned,namedBan,typeOfSanction,time,playerWhoBanned,when,null);
    }

}
