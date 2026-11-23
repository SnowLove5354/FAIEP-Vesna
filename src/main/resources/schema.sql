CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50),
  phone VARCHAR(20),
  avatar VARCHAR(255),
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  points INT DEFAULT 0,
  status TINYINT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS garbage_category (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  code VARCHAR(20) NOT NULL UNIQUE,
  icon_path VARCHAR(255),
  color VARCHAR(20),
  disposal_method VARCHAR(500)
);
CREATE TABLE IF NOT EXISTS garbage_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_id INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  alias VARCHAR(500),
  hazard_level VARCHAR(20),
  decomposition_time VARCHAR(50),
  common_mistake VARCHAR(500),
  recycle_info VARCHAR(500),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY idx_category (category_id),
  KEY idx_name (name)
);
CREATE TABLE IF NOT EXISTS recycle_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(32) NOT NULL,
  user_id BIGINT NOT NULL,
  category_id INT NOT NULL,
  `weight` DECIMAL(10,2) DEFAULT 0.00,
  quantity INT DEFAULT 1,
  images VARCHAR(1000),
  status TINYINT NOT NULL DEFAULT 0,
  reject_reason VARCHAR(200),
  pre_points INT DEFAULT 0,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_user (user_id),
  KEY idx_status (status)
);
CREATE TABLE IF NOT EXISTS point_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  change_points INT NOT NULL,
  type VARCHAR(20) NOT NULL,
  record_id BIGINT DEFAULT NULL,
  remark VARCHAR(200),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY idx_user (user_id)
);
CREATE TABLE IF NOT EXISTS article (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  content TEXT,
  cover VARCHAR(255),
  type VARCHAR(20) NOT NULL DEFAULT 'ARTICLE',
  view_count INT DEFAULT 0,
  top_flag TINYINT DEFAULT 0,
  status TINYINT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS quiz_question (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  question_text VARCHAR(500) NOT NULL,
  options_json VARCHAR(1000) NOT NULL,
  correct_answer VARCHAR(10) NOT NULL,
  category_id INT,
  type VARCHAR(10) DEFAULT 'SINGLE'
);
CREATE TABLE IF NOT EXISTS quiz_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  score INT DEFAULT 0,
  correct_count INT DEFAULT 0,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
