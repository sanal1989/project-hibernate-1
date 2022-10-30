package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {

    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/rpg");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.USER, "bestuser");
        properties.put(Environment.PASS, "bestuser");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        sessionFactory = new Configuration()
                .addAnnotatedClass(Player.class)
                .setProperties(properties)
                .buildSessionFactory();
    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        Session session = sessionFactory.openSession();
        NativeQuery<Player> nativeQuery = session.createNativeQuery("SELECT * FROM player", Player.class);
        nativeQuery.setFirstResult(pageNumber * pageSize);
        nativeQuery.setMaxResults(pageSize);
        List<Player> players = nativeQuery.list();
        session.close();
        return players;
    }


    @Override
    public int getAllCount() {
        Session session = sessionFactory.openSession();
        Query<Long> query = session.createNamedQuery("AllCount", Long.class);
        long count = query.uniqueResult();
        session.close();
        return (int)count;
    }

    @Override
    public Player save(Player player) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(player);
        transaction.commit();
        session.close();
        return player;
    }

    @Override
    public Player update(Player player) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Player playerUpdate = (Player) session.merge(player);
        transaction.commit();
        session.close();
        return playerUpdate;
    }

    @Override
    public Optional<Player> findById(long id) {
        Session session = sessionFactory.openSession();
        Query<Player> query = session.createQuery("from Player where id = " + id, Player.class);
        Optional<Player> optionalPlayer = query.uniqueResultOptional();
        session.close();
        return optionalPlayer;
    }

    @Override
    public void delete(Player player) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(player);
        session.flush();
        transaction.commit();
        session.close();
    }

    @PreDestroy
    public void beforeStop() {
        sessionFactory.close();
    }
}