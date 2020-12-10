package ru.job4j.cinema.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Place;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Optional<Hall> getLastHall() {
        Hall hall = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM hall ORDER BY id DESC FETCH FIRST ROW ONLY");
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    String places = it.getString("places");
                    List<List<Place>> parsedPlaces = objectMapper.convertValue(objectMapper.readValue(places, List.class), new TypeReference<List<List<Place>>>() {
                    });

                    hall = new Hall(it.getInt("id"), it.getString("name"), parsedPlaces);
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching default hall: ", e);
        }
        return Optional.ofNullable(hall);
    }

    @Override
    public void updateHall(Hall hall) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE hall SET name = ?, places = ? WHERE id = ?");
        ) {
            ps.setString(1, hall.getName());
            ps.setString(2, objectMapper.writeValueAsString(hall.getPlaces()));
            ps.setInt(3, hall.getId());
            ps.execute();
        } catch (Exception e) {
            log.error("Error while updating hall: ", e);
        }
    }

    @Override
    public Account createAccount(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO account(name,phone) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS)
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
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM account WHERE phone = ?", PreparedStatement.RETURN_GENERATED_KEYS)
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

}
