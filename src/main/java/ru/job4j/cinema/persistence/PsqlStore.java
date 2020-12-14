package ru.job4j.cinema.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.cinema.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Log4j2
public class PsqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return PsqlStore.Lazy.INST;
    }

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    @Override
    public Optional<Hall> getHall(int id) {
        Hall hall = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM halls WHERE id = ?");
        ) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet it = ps.getResultSet()) {
                if (it.next()) {
                    hall = new Hall(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching hall: ", e);
        }
        return Optional.ofNullable(hall);
    }

    @Override
    public Account createAccount(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO accounts(name,phone) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());

            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error("Error during account creation: ", e);
        }
        return account;
    }


    @Override
    public Optional<Account> getAccountByPhone(String phone) {
        Account account = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM accounts WHERE phone = ?", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, phone);

            ps.execute();
            try (ResultSet it = ps.getResultSet()) {
                if (it.next()) {
                    account = new Account(it.getInt("id"), it.getString("name"), it.getString("phone"));
                }
            }
        } catch (Exception e) {
            log.error("Error during account fetching: ", e);
        }
        return Optional.ofNullable(account);
    }

    @Override
    public Optional<Session> getSession(int id) {
        Session session = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions WHERE id = ?", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet it = ps.getResultSet()) {
                if (it.next()) {
                    session = new Session(it.getInt("id"), it.getString("name"), it.getInt("hall_id"));
                }
            }
        } catch (Exception e) {
            log.error("Error during session fetching: ", e);
        }
        return Optional.ofNullable(session);
    }

    @Override
    public List<Seat> getSeatsByHall(int hallId) {
        List<Seat> seats = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM seats WHERE  hall_id = ?", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, hallId);
            ps.execute();
            try (ResultSet it = ps.getResultSet()) {
                while (it.next()) {
                    seats.add(new Seat(it.getInt("id"), it.getInt("row"), it.getInt("place"), it.getInt("hall_id")));
                }
            }
        } catch (Exception e) {
            log.error("Error during seats fetching: ", e);
        }
        return seats;
    }

    @Override
    public Order create(Order order) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO orders(session_id,account_id,hall_id,seat_id,price) values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, order.getSessionId());
            ps.setInt(2, order.getAccountId());
            ps.setInt(3, order.getHallId());
            ps.setInt(4, order.getSeatId());
            ps.setInt(5, order.getPrice());
            ps.execute();

            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    order.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.error("Error during order creation: ", e);
        }
        return order;
    }

    @Override
    public List<Order> getSessionOrders(int sessionId) {
        List<Order> orders = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from orders where session_id = ?;", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, sessionId);
            ps.execute();
            try (ResultSet it = ps.getResultSet()) {
                while (it.next()) {
                    orders.add(new Order(it.getInt("id"), it.getInt("session_id"), it.getInt("account_id"), it.getInt("hall_id"), it.getInt("seat_id"), it.getInt("price")));
                }
            }
        } catch (Exception e) {
            log.error("Error during orders fetching: ", e);
        }
        return orders;
    }
}
