package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
@Slf4j
@Component
@Primary
public class UserDbStorage implements UserStorage{
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public User userAdd(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("PUBLIC")
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        log.info("12");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        log.info("15");
        simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        log.info("18");
        String sql = "select * from users where user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs)).get((int) user.getId());
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Long id = rs.getLong("USER_ID");
        String name = rs.getString("NAME");
        String login = rs.getString("LOGIN");
        String email = rs.getString("EMAIL");
        LocalDate birthday = rs.getDate("BIRHDAY").toLocalDate();
        return new User(email, login, name,birthday);
    }

    @Override
    public boolean userDelete(User user) {
        String sqlQuerry = "delete from users where USER_ID = ?";
        return jdbcTemplate.update(sqlQuerry, user.getId()) > 0;
    }

    @Override
    public boolean userRefresh(User user) {
        String sql = "update USERS set "
                + "name = ?, LOGIN = ?, BIRHDAY = ?, EMAIL = ? "
                + "where USER_ID = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getLogin(), user.getBirthday(), user.getEmail()) >0;
    }

    @Override
    public HashMap<Long, User> getAllUsers() {
        String sql = "select * from USERS";
        HashMap<Long, User> users= new HashMap<>();
        for(User user: jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs))) {
            users.put(user.getId(), user);
        }
        return users;
    }

    @Override
    public User getUserFromId(long id) throws WrongIdException {
        log.info("+1");
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select  * from USERS where USER_ID = ?", id);
        log.info("+");
        if(sqlRowSet.next()) {
            User user = new User(sqlRowSet.getInt("user_id"),
                    sqlRowSet.getString("email"),
                    sqlRowSet.getString("login"),
                    sqlRowSet.getString("name"),
                    sqlRowSet.getDate("birhday").toLocalDate());
            log.info("Найден фильм {}", user.getId());
            return user;
        } else return null;
    }
}
