package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        Transaction transaction = null;

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";

        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();

            System.out.println("Таблица user создана или уже существует");

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        Transaction transaction = null;

        String sql = "DROP TABLE IF EXISTS user";

        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();

            System.out.println("Таблица user удалена");
        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            User user = session.get(User.class, id);

            session.delete(user);

            transaction.commit();

            System.out.println("Пользователь с id: " + id + " удалён");

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> result = null;

        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);

            Root<User> rootEntry = criteriaQuery.from(User.class);

            criteriaQuery.select(rootEntry);

            result = session.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }

        return result;

    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;

        String sql = "TRUNCATE TABLE user;";

        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();

            System.out.println("Таблица user очищенна");

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }

    }
}
