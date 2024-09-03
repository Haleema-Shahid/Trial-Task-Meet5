package com.haleema.trial_task_application.repository.JDBC;

import com.haleema.trial_task_application.entity.User;

import java.util.List;
import java.util.Optional;

public interface DataRepositoryJDBC<T, ID> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<T> findById(ID id);

    void save(T entity);

    void update(T entity);

}
