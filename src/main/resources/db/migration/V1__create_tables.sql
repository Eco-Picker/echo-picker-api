-- User Table
CREATE TABLE user (
    user_id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL,
    user_onboarding_status VARCHAR(20) NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Garbage Table
CREATE TABLE garbage (
    garbage_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    name VARCHAR(255) NOT NULL, -- name from Gemini
    category ENUM('PLASTIC', 'METAL', 'GLASS', 'CARDBOARD_PAPER', 'FOOD_SCRAPS', 'ORGANIC_YARD_WASTE', 'OTHER') NOT NULL,
    memo TEXT,
    collected_latitude DOUBLE NOT NULL,
    collected_longitude DOUBLE NOT NULL,
    picked_up_at TIMESTAMP NOT NULL, -- picked up datetime
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

-- Ranking Table
CREATE TABLE ranking (
    ranking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    period ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    rank INT NOT NULL,
    score INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Newsletter_Event Table
CREATE TABLE newsletter_event (
    event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    source VARCHAR(255),
    event_date TIMESTAMP,
    expiration_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Newsletter_Education Table
CREATE TABLE newsletter_education (
    education_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    source VARCHAR(255),
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Newsletter_News Table
CREATE TABLE newsletter_news (
    news_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    source VARCHAR(255),
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
