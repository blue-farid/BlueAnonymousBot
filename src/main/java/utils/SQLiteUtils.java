package utils;

import model.Client;
import model.ClientState;
import org.telegram.telegrambots.meta.api.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SQLite utils class.
 * all the database operations are here.
 * NOTE: connects to the database at constructor.
 * @author Farid Masjedi
 */
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

    /**
     * creates the client table if not exist.
     */
    public void creatClientTable() {
        try {
            String q = "CREATE TABLE IF NOT EXISTS \"CLIENT\" (" + "\"ID\"INTEGER UNIQUE NOT NULL," + "\"TelegramUser\"BLOB NOT NULL UNIQUE," + "\"LongDeepLink\"TEXT," + "\"ShortDeepLink\"TEXT," + "\"ChatId\"INTEGER NOT NULL," + "\"ClientState\"TEXT NOT NULL," + "\"IsAdmin\"INTEGER NOT NULL," + "\"ContactId\"INTEGER, " + "\"ContactMessageId\"INTEGER, "+ "PRIMARY KEY(\"ID\")" + ");";
            this.statement.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * select clients.
     * @return A Collection of the clients.
     */
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

    /**
     * select a client by its id.
     * @param id the id
     * @return the client.
     */
    public Client selectClient(long id) {
        try {
            String q = "SELECT * FROM CLIENT WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return resultSetToClient(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * select a client by its deeplink.
     * @param shortDeeplink the deeplink.
     * @return the client.
     */
    public Client selectClient(String shortDeeplink) {
        try {
            String q = "SELECT * FROM CLIENT WHERE ShortDeepLink = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setString(1, shortDeeplink);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return resultSetToClient(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * insert a client.
     * @param client the client
     * @return the result as int.
     */
    public int insertClient(Client client) {
        try {
            String q = "INSERT INTO CLIENT VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setLong(1, client.getId());
            ps.setBytes(2, Common.getInstance().objectToBinaryInputStream(client.getTelegramUser()).readAllBytes());
            ps.setString(3, client.getLongDeepLink());
            ps.setString(4, client.getShortDeepLink());
            ps.setLong(5, client.getChatId());
            ps.setString(6, client.getClientState().toString());
            ps.setInt(7, booleanToInt(client.isAdmin()));
            ps.setLong(8, client.getContactId());
            ps.setInt(9, 0);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * updates a client's deeplink.
     * @param id the client's id.
     * @param longDeepLink the client's long deeplink.
     * @param shortDeepLink the client's short deeplink.
     * @return the result as int.
     */
    public int updateClientDeepLink(long id, String longDeepLink, String shortDeepLink) {
        try {
            String q = "UPDATE CLIENT SET LongDeepLink = ?, ShortDeepLink = ? WHERE ID = ?";
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

    /**
     * updates a client's admin status.
     * @param id the client's id.
     * @param admin the client's admin status.
     * @return the result as int.
     */
    public int updateClientAdmin(long id, boolean admin) {
        try {
            String q = "UPDATE CLIENT SET IsAdmin = ? WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setInt(1, booleanToInt(admin));
            ps.setLong(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * updates the client's contact.
     * @param id the client's id.
     * @param contactId the client's contact id.
     * @return the result as int.
     */
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

    /**
     * update the client's state.
     * @param id the client's id.
     * @param clientState the client's state.
     * @return the result as int.
     */
    public int updateClientState(long id, ClientState clientState) {
        try {
            String q = "UPDATE CLIENT SET ClientState = ? WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setString(1, clientState.toString());
            ps.setLong(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * convert the result set to client.
     * @param rs the result set.
     * @return the client.
     */
    private Client resultSetToClient(ResultSet rs) {
        try {
            long id = rs.getInt(1);
            User telegramUser = (User) Common.getInstance().binaryInputStreamToObject(rs.getBinaryStream(2));
            String longDp = rs.getString(3);
            String shortDp = rs.getString(4);
            long chatId = rs.getInt(5);
            ClientState clientState = ClientState.valueOf(rs.getString(6));
            boolean admin = rs.getInt(7) != 0;
            long contactId = rs.getInt(8);
            int contactMessageId = rs.getInt(9);
            return new Client(id, telegramUser, longDp, shortDp, chatId, clientState, admin, contactId, contactMessageId);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * converts the boolean to int. (sqlite can't store boolean directly).
     * @param target the boolean.
     * @return 1 if target == true else 0.
     */
    private int booleanToInt(boolean target) {
        return target ? 1 : 0;
    }

    /**
     * select a client's chat id
     * @param id the client's id.
     * @return the chat id.
     */
    public Long selectClientChatId(long id) {
        try {
            String q = "SELECT ChatId FROM CLIENT WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setLong(1, id);
            return ps.executeQuery().getLong(1);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * update client's contact message id.
     * @param id the client's id.
     * @param messageId the client's contact message id.
     * @return the result as int.
     */
    public int updateClientContactMessageId(long id, Integer messageId) {
        try {
            String q = "UPDATE CLIENT SET ContactMessageId = ? WHERE ID = ?";
            PreparedStatement ps = this.connection.prepareStatement(q);
            ps.setInt(1, messageId);
            ps.setLong(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
