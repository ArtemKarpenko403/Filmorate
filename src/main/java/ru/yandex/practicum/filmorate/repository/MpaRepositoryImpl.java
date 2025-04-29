package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class MpaRepositoryImpl {
    private final JdbcTemplate jdbcTemplate;

    public MpaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Mpa> findAll() {
        String sql = "SELECT * FROM mpa_ratings";
        return jdbcTemplate.query(sql, this::mapRowToMpa);
    }

    public Mpa findById(int mpaId) {
        String sql = "SELECT * FROM mpa_ratings WHERE mpa_rating_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToMpa, mpaId);
    }

    private Mpa mapRowToMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(
                rs.getInt("mpa_rating_id"),
                rs.getString("name")
        );
    }
}