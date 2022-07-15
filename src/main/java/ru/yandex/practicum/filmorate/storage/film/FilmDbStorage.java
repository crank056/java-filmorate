package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.WrongIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

@Component
@Repository
@Slf4j
@Primary
public class FilmDbStorage implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Film filmAdd(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("PUBLIC")
                .withTableName("FILMS")
                .usingColumns("NAME", "DESCRIPTION", "RELEASE_DATE", "DURATION", "MPA_RATE")
                .usingGeneratedKeyColumns("FILM_ID");
        log.info("12");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(film);
        log.info("15");
        simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        log.info("18");
        String sql = "select * from films where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs)).get((int) film.getId());
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Long id = rs.getLong("FILM_ID");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        Integer duration = rs.getInt("DURATION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        Integer mpaRate = rs.getInt("MPA_ID");
        return new Film(name,description,releaseDate, duration, id, mpaRate);
    }

    @Override
    public boolean filmDelete(Film film) {
        String sqlQuerry = "delete from films where FILM_ID = ?";
        return jdbcTemplate.update(sqlQuerry, film.getId()) > 0;
    }

    @Override
    public boolean filmRefresh(Film film) {
        String sql = "update films set "
                + "name = ?, description = ?, release_date = ?, duration = ? "
                + "where FILM_ID = ?";
        return jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId()) >0;
    }

    @Override
    public HashMap<Long, Film> getAllFilms() {
        String sql = "select * from films";
        HashMap<Long, Film> films= new HashMap<>();
        for(Film film: jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs))) {
            films.put(film.getId(), film);
        }
        return films;
    }

    @Override
    public Film getFilmFromId(long id) throws WrongIdException {
        log.info("+1");
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select  * from FILMS where FILM_ID = ?", id);
        log.info("+");
        if(sqlRowSet.next()) {
            Film film = new Film(sqlRowSet.getString("name"),
            sqlRowSet.getString("description"),
            sqlRowSet.getDate("release_date").toLocalDate(),
            sqlRowSet.getInt("duration"),
            sqlRowSet.getInt("film_id"),
            sqlRowSet.getInt("mpa_id"));
            log.info("Найден фильм {}", film.getId());
            return film;

        } else return null;
        /*tring sql = "select * from films where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), id).get((int) id);*/
    }
}
