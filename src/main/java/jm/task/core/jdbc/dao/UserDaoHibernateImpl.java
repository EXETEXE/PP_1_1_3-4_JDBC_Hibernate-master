package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";


        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();

        System.out.println("Таблица user создана или уже существует");

        session.close();
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();

        System.out.println("Таблица user удалена");

        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(new User(name, lastName, age));

        session.getTransaction().commit();

        System.out.println("User с именем – " + name + " добавлен в базу данных");

        session.close();

    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        User user = session.get(User.class, id);

        session.delete(user);

        session.getTransaction().commit();

        System.out.println("Пользователь с id: " + id + " удалён");

        session.close();

    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);

        Root<User> rootEntry = criteriaQuery.from(User.class);

        criteriaQuery.select(rootEntry);

        List<User> result = session.createQuery(criteriaQuery).getResultList();

        session.close();

        return result;

    }

    @Override
    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE user;";

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();

        System.out.println("Таблица user очищенна");

        session.close();

    }
}
