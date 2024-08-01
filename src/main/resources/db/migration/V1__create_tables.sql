-- User Table
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL, -- using prefix to avoid potential compatibility issues
    onboarding_status INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL
);

-- Garbage Table
CREATE TABLE garbage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    garbage_name VARCHAR(255) NOT NULL, -- using prefix to avoid potential compatibility issues
    category VARCHAR(50) NOT NULL,
    memo VARCHAR(1000),
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    collected_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Auth Table
CREATE TABLE auth (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    access_token VARCHAR(1000),
    expires_at TIMESTAMP NOT NULL,
    refresh_token VARCHAR(1000),
    refresh_expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- GarbageMonthly Table
CREATE TABLE garbage_monthly (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    collected_month VARCHAR(6), -- "YYYYMM"
    plastic INT,
    metal INT,
    glass INT,
    cardboard_paper INT,
    food_scraps INT,
    organic_yard_waste INT,
    other INT,
    total_score INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- GarbageWeekly Table
CREATE TABLE garbage_weekly (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    collected_week INT,
    plastic INT,
    metal INT,
    glass INT,
    cardboard_paper INT,
    food_scraps INT,
    organic_yard_waste INT,
    other INT,
    total_score INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Ranking Table
CREATE TABLE ranking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    ranking_period VARCHAR(20) NOT NULL, -- using prefix to avoid potential compatibility issues (values: 'daily', 'weekly', 'monthly')
    ranking INT NOT NULL, -- using prefix to avoid potential compatibility issues
    score INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Newsletter Event Table
CREATE TABLE newsletter_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    source VARCHAR(255) NOT NULL,
    start_at TIMESTAMP,
    end_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL
);

-- Newsletter Education Table
CREATE TABLE newsletter_education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    source VARCHAR(255) NOT NULL,
    published_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL
);

-- Newsletter News Table
CREATE TABLE newsletter_news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    source VARCHAR(255) NOT NULL,
    published_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL
);
