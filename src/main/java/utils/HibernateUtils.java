package utils;

import model.Client;
import model.ClientState;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
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
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Collection<Client> result = session.createQuery("FROM CLIENT", Client.class).list();
            transaction.commit();
            return result;
        }
    }

    /**
     * select a client by its id.
     *
     * @param id the id
     * @return the client.
     */
    public Client selectClientById(long id) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client result = session.get(Client.class, id);
            transaction.commit();
            return result;
        }
    }

    /**
     * select a client by its deeplink.
     *
     * @param deepLink the deeplink.
     * @return the client.
     */
    public Client selectClientByDeepLink(String deepLink) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Client.class);
            criteria.add(Restrictions.eq("deepLink", deepLink));
            Client result = (Client) criteria.uniqueResult();
            transaction.commit();
            return result;
        }
    }

    public Client selectClientByUsername(String username) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Client.class);
            criteria.add(Restrictions.eq("username", username));
            Client result = (Client) criteria.uniqueResult();
            transaction.commit();
            return result;
        }
    }

    /**
     * insert a client.
     *
     * @param client the client
     * @return the result as int.
     */
    public int insertClient(Client client) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
            return 0;
        }
    }

    /**
     * updates a client's deeplink.
     *
     * @param id       the client's id.
     * @param deepLink the client's deeplink.
     * @return the result as int.
     */
    public int updateClientDeepLink(long id, String deepLink) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = this.selectClientById(id);
            client.setDeepLink(deepLink);
            session.update(client);
            transaction.commit();
            return 0;
        }
    }

    /**
     * updates a client's admin status.
     *
     * @param id    the client's id.
     * @param admin the client's admin status.
     * @return the result as int.
     */
    public int updateClientAdmin(long id, boolean admin) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = this.selectClientById(id);
            client.setAdmin(admin);
            session.update(client);
            transaction.commit();
            return 0;
        }
    }

    /**
     * updates the client's contact.
     *
     * @param id        the client's id.
     * @param contactId the client's contact id.
     * @return the result as int.
     */
    public int updateClientContact(long id, long contactId) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = this.selectClientById(id);
            client.setContactId(contactId);
            session.update(client);
            transaction.commit();
            return 0;
        }
    }

    /**
     * update the client's state.
     *
     * @param id          the client's id.
     * @param clientState the client's state.
     * @return the result as int.
     */
    public int updateClientState(long id, ClientState clientState) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = this.selectClientById(id);
            client.setClientState(clientState);
            session.update(client);
            transaction.commit();
            return 0;
        }
    }

    public int updateClientContactMessageId(long id, int contactMessageId) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = this.selectClientById(id);
            client.setContactMessageId(contactMessageId);
            session.update(client);
            transaction.commit();
            return 0;
        }
    }

    public int updateClient(Client client) {
        try (Session session = this.factory.openSession()) {
            Transaction tr = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<Client> update = cb.createCriteriaUpdate(Client.class);
            Root<Client> root = update.from(Client.class);
            update.set("firstname", client.getFirstname())
                    .set("lastname", client.getLastname()).set("telegramUser", client.getTelegramUser())
                    .set("username", client.getUsername());
            update.where(cb.equal(root.get("id"), client.getId()));
            session.createQuery(update).executeUpdate();
            tr.commit();
            return 0;
        }
    }
}
