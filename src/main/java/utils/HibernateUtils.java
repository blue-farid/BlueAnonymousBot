package utils;

import model.Client;
import model.ClientState;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Collection;

/**
 * SQLite utils class.
 * all the database operations are here.
 * NOTE: connects to the database at constructor.
 *
 * @author Farid Masjedi
 */
public class HibernateUtils {
    private static HibernateUtils instance;
    private final SessionFactory factory;

    private HibernateUtils() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static HibernateUtils getInstance() {
        if (instance == null) {
            instance = new HibernateUtils();
        }
        return instance;
    }

    /**
     * select clients.
     *
     * @return A Collection of the clients.
     */
    public Collection<Client> selectClients() {
        return this.factory.openSession().createQuery("FROM CLIENT", Client.class).list();
    }

    /**
     * select a client by its id.
     *
     * @param id the id
     * @return the client.
     */
    public Client selectClient(long id) {
        return this.factory.openSession().byId(Client.class).getReference(id);
    }

    /**
     * select a client by its deeplink.
     *
     * @param shortDeeplink the deeplink.
     * @return the client.
     */
    public Client selectClient(String shortDeeplink) {
        return this.factory.openSession().get(Client.class, shortDeeplink);
    }

    /**
     * insert a client.
     *
     * @param client the client
     * @return the result as int.
     */
    public int insertClient(Client client) {
        this.factory.openSession().saveOrUpdate(client);
        return 0;
    }

    /**
     * updates a client's deeplink.
     *
     * @param id       the client's id.
     * @param deepLink the client's deeplink.
     * @return the result as int.
     */
    public int updateClientDeepLink(long id, String deepLink) {
        Client client = this.selectClient(id);
        client.setDeepLink(deepLink);
        this.factory.openSession().update(client);
        return 0;
    }

    /**
     * updates a client's admin status.
     *
     * @param id    the client's id.
     * @param admin the client's admin status.
     * @return the result as int.
     */
    public int updateClientAdmin(long id, boolean admin) {
        Client client = this.selectClient(id);
        client.setAdmin(admin);
        this.factory.openSession().update(client);
        return 0;
    }

    /**
     * updates the client's contact.
     *
     * @param id        the client's id.
     * @param contactId the client's contact id.
     * @return the result as int.
     */
    public int updateClientContact(long id, long contactId) {
        Client client = this.selectClient(id);
        client.setContactId(contactId);
        this.factory.openSession().update(client);
        return 0;
    }

    /**
     * update the client's state.
     *
     * @param id          the client's id.
     * @param clientState the client's state.
     * @return the result as int.
     */
    public int updateClientState(long id, ClientState clientState) {
        Client client = this.selectClient(id);
        client.setClientState(clientState);
        this.factory.openSession().update(client);
        return 0;
    }
}
