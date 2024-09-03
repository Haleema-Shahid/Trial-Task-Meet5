CREATE TABLE IF NOT EXISTS user_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    date_of_birth DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_deactivated BIT DEFAULT FALSE,
    is_deleted BIT DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS profile_visit (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             visitor_id BIGINT NOT NULL,
                                             visited_id BIGINT NOT NULL,
                                             visited_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                             FOREIGN KEY (visitor_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (visited_id) REFERENCES user_profile(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS profile_like (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            liker_id BIGINT NOT NULL,
                                            liked_id BIGINT NOT NULL,
                                            liked_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (liker_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (liked_id) REFERENCES user_profile(id) ON DELETE CASCADE
    );
