package com.haleema.trial_task_application.repository.JDBC;


import com.haleema.trial_task_application.entity.User;
import com.haleema.trial_task_application.repository.JDBC.DataRepositoryJDBC;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserRepositoryJDBC implements DataRepositoryJDBC<User, Long> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM user_profile WHERE email = ? AND password = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{email, password}, new UserRowMapper());
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM user_profile WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{id}, new UserRowMapper());
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user_profile (name, email, password, date_of_birth, created_at, last_login, is_deactivated, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getDateOfBirth(), user.getCreatedAt(), user.getLastLogin(), user.getIsDeactivated(), user.getIsDeleted());
    }
    @Override
    public void update(User user) {
        String sql = "UPDATE user_profile SET name = ?, email = ?, password = ?, date_of_birth = ?, created_at = ?, last_login = ?, is_deactivated = ?, is_deleted = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getDateOfBirth(), user.getCreatedAt(), user.getLastLogin(), user.getIsDeactivated(), user.getIsDeleted(), user.getId());
    }

    private static class UserRowMapper implements RowMapper<User> {
        
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setDateOfBirth(rs.getDate("date_of_birth"));
            user.setCreatedAt(rs.getDate("created_at"));
            user.setLastLogin(rs.getDate("last_login"));
            user.setIsDeactivated(rs.getBoolean("is_deactivated"));
            user.setIsDeleted(rs.getBoolean("is_deleted"));
            return user;
        }
    }
}

