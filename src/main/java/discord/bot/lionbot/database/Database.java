package discord.bot.lionbot.database;

import com.sun.jdi.Field;
import discord.bot.lionbot.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String connectionUrl;
    private Connection connection = null;

    public Database() {
        prepareToConnect();
    }

    public Connection getConnection() {
        return connection;
    }

    private void prepareToConnect()
    {
        String abspath = new File("").getAbsolutePath();
        String databaseFileName = "lionpdfdb.sqlite";
        connectionUrl = "jdbc:sqlite:" + abspath + File.separator + databaseFileName;
    }
    public void connect()
    {
        Main.getLogger().finest("Connecting to database");
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            if(conn == null) {
                throw new SQLException("Database is not connected");
            }
            Main.getLogger().finest("CONNECTED TO DATABASE");
            createMetadadosTable(conn);
            connection = conn;
        } catch (SQLException exception) {
            Main.getLogger().warning(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static void createMetadadosTable(Connection conn) throws SQLException {
        Main.getLogger().finest("CREATING DATABASE TO SAVE INFO");
        String createMetadadosTable = "CREATE TABLE IF NOT EXISTS metadados (" +
                "fullpath TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL" +
                ")";
        Statement stmt = conn.createStatement();
        stmt.execute(createMetadadosTable);
    }
}
