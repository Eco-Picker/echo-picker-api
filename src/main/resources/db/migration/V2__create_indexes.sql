/*
Additional indexes will be added as needed 
*/

-- Indexes for User Table
CREATE INDEX idx_user_email ON user(user_email);

-- Indexes for Garbage Table
CREATE INDEX idx_garbage_user_id ON garbage(user_id);
CREATE INDEX idx_garbage_collected_time ON garbage(collected_time);

-- Indexes for GarbageMonthly Table
CREATE INDEX idx_garbage_monthly_user_id ON garbage_monthly(user_id);
CREATE INDEX idx_garbage_monthly_month ON garbage_monthly(collected_month);

-- Indexes for Auth Table
CREATE INDEX idx_auth_access_token ON auth(access_token);
CREATE INDEX idx_auth_refresh_token ON auth(refresh_token);
