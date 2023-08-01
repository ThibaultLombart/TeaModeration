package fr.thibaultlombart.teamoderation.exceptions;

public class SqlErrorException extends RuntimeException{
    public SqlErrorException(String preparedStatement){
        super("Error during the SQL Query : "+preparedStatement);
    }
}
