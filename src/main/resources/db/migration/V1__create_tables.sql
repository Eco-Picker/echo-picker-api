-- User Table
CREATE TABLE user (
    user_id VARCHAR(50) PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL,
    user_onboarding_status INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Garbage Table
CREATE TABLE garbage (
    garbage_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    garbage_type VARCHAR(50),
    garbage_detail TEXT,
    collected_latitude DOUBLE,
    collected_longitude DOUBLE,
    collected_time TIMESTAMP,
    garbage_image_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Auth Table
CREATE TABLE auth (
    user_id VARCHAR(50) PRIMARY KEY,
    access_token TEXT,
    refresh_token TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- GarbageMonthly Table
CREATE TABLE garbage_monthly (
    garbage_monthly_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    collected_month VARCHAR(6),
    plastic INT,
    metal INT,
    glass INT,
    cardboard_paper INT,
    food_scraps INT,
    organic_yard_waste INT,
    other INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);