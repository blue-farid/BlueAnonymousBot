package utils;

import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SQLiteUtils {
    private static SQLiteUtils instance;
    private Connection connection;
    private Statement statement;

    private SQLiteUtils() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:files/db/sqlite/blue-anonymous-bot.db");
            this.statement = this.connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLiteUtils getInstance() {
        if (instance == null) {
            instance = new SQLiteUtils();
        }
        return instance;
    }

    public void creatClientTable() {
        try {
            String q = "CREATE TABLE \"CLIENT\" (\n" +
                    "\t\"ID\"\tINTEGER UNIQUE NOT NULL,\n" +
                    "\t\"TelegramUser\"\tBLOB NOT NULL UNIQUE,\n" +
                    "\t\"LongDeepLink\"\tTEXT,\n" +
                    "\t\"ShortDeepLink\"\tTEXT,\n" +
                    "\t\"ChatId\"\tINTEGER NOT NULL,\n" +
                    "\t\"ClientState\"\tTEXT NOT NULL,\n" +
                    "\t\"IsAdmin\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"ID\")\n" +
                    ");";
            this.statement.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Client> selectClients() {
        try {
            String q = "SELECT * FROM CLIENT";
            ResultSet rs = this.statement.executeQuery(q);
            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                clients.add(resultSetToClient(rs));
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client selectClient(long id) {
        try {
            String q = "SELECT * FROM CLIENT WHERE ID = ?";
            PreparedStatement ps = (PreparedStatement) this.connection.prepareStatement(q);
            ps.setLong(1, id);
            return resultSetToClient(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client resultSetToClient(ResultSet rs) {
        try {
            long id = rs.getInt(1);
            User telegramUser = (User) rs.getBlob(2);
            String longDp = rs.getString(3);
            String shortDp = rs.getString(4);
            long chatId = rs.getInt(5);
            ClientState clientState = ClientState.valueOf(rs.getString(6));
            boolean admin = rs.getInt(7) != 0;
            return new Client(id, telegramUser, longDp, shortDp, chatId
                    , clientState, admin);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
