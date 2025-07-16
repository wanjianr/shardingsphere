CREATE TABLE `ht_ticket` (
                             `id` BIGINT PRIMARY KEY,
                             `year` VARCHAR(4) NOT NULL,
                             `ticket_no` VARCHAR(50) NOT NULL,
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='票据信息';

CREATE TABLE `ht_ticket_2024` LIKE `ht_ticket`;
CREATE TABLE `ht_ticket_2025` LIKE `ht_ticket`;
CREATE TABLE `ht_ticket_2026` LIKE `ht_ticket`;


CREATE TABLE `ht_file` (
                             `id` BIGINT PRIMARY KEY,
                             `year` VARCHAR(4) NOT NULL,
                             `file_id` VARCHAR(50) NOT NULL,
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件信息';

CREATE TABLE `ht_file_2024` LIKE `ht_file`;
CREATE TABLE `ht_file_2025` LIKE `ht_file`;
CREATE TABLE `ht_file_2026` LIKE `ht_file`;


CREATE TABLE `ht_pay` (
                           `id` BIGINT PRIMARY KEY,
                           `order_id` VARCHAR(50) NOT NULL,
                           `amount` DOUBLE NOT NULL,
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件信息';

CREATE TABLE `ht_pay_0` LIKE `ht_pay`;
CREATE TABLE `ht_pay_1` LIKE `ht_pay`;
