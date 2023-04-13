package repository.db;

import repository.IRepository;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.SQLiteDataSource;
import utils.Logger;

import static java.lang.System.exit;

public abstract class DatabaseRepository<ID, T> implements IRepository<ID, T> {
    protected String url;
    protected Connection connection;

    public DatabaseRepository(String url) {
        this.url = "jdbc:sqlite:" + url;
        createTable();
    }

    protected void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(this.url);
            if (connection == null || connection.isClosed())
                connection = ds.getConnection();
            Logger.log(this.getClass().getSimpleName(), "Connection opened to " + this.url);
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    protected void closeConnection() {
        try {
            connection.close();
            Logger.log(this.getClass().getSimpleName(), "Connection closed with " + this.url);
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    abstract protected void createTable();
}
