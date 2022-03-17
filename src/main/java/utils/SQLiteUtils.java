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
                    "\t\"ContactId\"\tINTEGER" +
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
            return null;
        }
    }

    public Client selectClient(long id) {
        try {
            String q = "SELECT * FROM CLIENT WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setLong(1, id);
            return resultSetToClient(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Client selectClient(String shortDeeplink) {
        try {
            String q = "SELECT * FROM CLIENT WHERE ShortDeepLink = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setString(1, shortDeeplink);
            return resultSetToClient(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insertClient(Client client) {
        try {
            String q = "INSERT INTO CLIENT VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setLong(1, client.getId());
            ps.setBlob(2, (Blob) client.getTelegramUser());
            ps.setString(3, client.getLongDeepLink());
            ps.setString(4, client.getShortDeepLink());
            ps.setLong(5, client.getChatId());
            ps.setString(6, client.getClientState().toString());
            ps.setInt(7, booleanToInt(client.isAdmin()));
            ps.executeUpdate();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateClientDeepLink(long id, String longDeepLink, String shortDeepLink) {
        try {
            String q = "UPDATE CLIENT SET LongDeepLink = ?, SortDeepLink = ? WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setString(1, longDeepLink);
            ps.setString(2, shortDeepLink);
            ps.setLong(3, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateClientAdmin(long id, boolean admin) {
        try {
            String q = "UPDATE CLIENT SET Admin = ? WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setInt(1, booleanToInt(admin));
            ps.setLong(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateClientContact(long id, long contactId) {
        try {
            String q = "UPDATE CLIENT SET ContactId = ? WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setLong(1, contactId);
            ps.setLong(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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

    private int booleanToInt(boolean target){
        return target ? 1 : 0;
    }
}
