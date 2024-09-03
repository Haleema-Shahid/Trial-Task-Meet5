package com.haleema.trial_task_application.service;

import com.haleema.trial_task_application.entity.User;
import com.haleema.trial_task_application.repository.UserRepository;
import com.haleema.trial_task_application.util.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static com.haleema.trial_task_application.util.Constants.*;

@Service
@RequiredArgsConstructor
public class BulkInsertionService {

    private static final Logger logger = LoggerFactory.getLogger(BulkInsertionService.class);
    private final UserRepository userRepository;

    public void bulkInsertUsers() {
        List<User> users = readUsersFromCSV(CSV_FILE_PATH);
        Long startTime = System.currentTimeMillis();
        logger.info("Bulk Insert Started");
        String insertSQL = "INSERT INTO user_profile (name, email, password, date_of_birth, last_login) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            connection.setAutoCommit(false);

            for (User user : users) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
                preparedStatement.setDate(5, null);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            connection.commit();
            Long endTime = System.currentTimeMillis();
            Long executionTime = endTime - startTime;
            logger.info("Bulk Insertion complete. Execution time: " + executionTime + " milliseconds");
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    /**
     * this method has only been created for performance comparison.
     */
    public void bulkInsertUsersWithJpa() {
        Long startTime = System.currentTimeMillis();
        logger.info("Bulk Insert With Jpa started");
        List<User> users = readUsersFromCSV(CSV_FILE_PATH);
        userRepository.saveAll(users);
        Long endTime = System.currentTimeMillis();
        Long executionTime = endTime - startTime;
        logger.info("Bulk Insertion with Jpa complete. Execution time: " + executionTime + " milliseconds");

    }

    private List<User> readUsersFromCSV(String filePath) {
        List<User> users = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust to match your date format

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",\\s*");
                if (values.length == 4) {
                    User user = new User();
                    user.setName(values[0]);
                    user.setEmail(values[1]);
                    user.setPassword(values[2]);
                    user.setLastLogin(null);
                    try {
                        Date dateOfBirth = dateFormat.parse(values[3]);
                        user.setDateOfBirth(dateOfBirth);
                    } catch (ParseException e) {
                        logger.error("Unable to parse date string. Exception occurred: " + e.getLocalizedMessage());
                    }
                    users.add(user);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return users;
    }
}
