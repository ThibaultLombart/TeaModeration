package fr.thibaultlombart.teamoderation.database;

import fr.thibaultlombart.teamoderation.Informations;
import fr.thibaultlombart.teamoderation.exceptions.SqlErrorException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final DbConnection dbConnection;

    public DatabaseManager(){
        this.dbConnection = new DbConnection(new DbCredentials(Informations.getInformations("database.host"), Informations.getInformations("database.user"), Informations.getInformations("database.password"), Informations.getInformations("database.dbName"), Informations.getInformationsInt("database.port")));
        checkExistingTables();
        DatabaseController.init();
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void checkExistingTables(){
        try(Connection connection = dbConnection.getConnection();) {
            Statement statement = connection.createStatement();
            String logs = "CREATE TABLE IF NOT EXISTS logsTeaModeration"+
                    "(id serial," +
                    "UUID_BANNED VARCHAR(128) not null," +
                    "NICKNAME_BANNED VARCHAR(16) not null," +
                    "TYPE_OF_SANCTION VARCHAR(20) not null," +
                    "TIME VARCHAR(50) not null," +
                    "UUID_WHO_BANNED VARCHAR(128) not null," +
                    "NICKNAME_WHO_BANNED VARCHAR(16) not null," +
                    "WHEN_SANCTION date not null," +
                    "WHEN_UNSANCTION date," +
                    "Primary Key (id))";
            statement.execute(logs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            this.dbConnection.close();
        } catch (SQLException e) {
            throw new SqlErrorException(e.toString());
        }
    }
}