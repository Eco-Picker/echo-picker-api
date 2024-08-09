-- @todo entity 이용해서 자동으로 table 관리되므로 중복 관리 포인트를 없애기 위해 추후 삭제 필요
-- 참고) 미희: 작업한 DDL 은 이미 삭제 함, 모두 작업 완료하시면 해당 파일 삭제 부탁드려요.

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
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    collected_at TIMESTAMP NOT NULL,
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
    collected_year INT,
    collected_week INT,
    plastic INT,
    metal INT,
    glass INT,
    cardboard_paper INT,
    food_scraps INT,
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
