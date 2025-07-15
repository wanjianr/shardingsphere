CREATE TABLE `ht_ticket` (
                             `id` BIGINT PRIMARY KEY,
                             `year` VARCHAR(4) NOT NULL,
                             `ticket_no` VARCHAR(50) NOT NULL,
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='票据信息';

CREATE TABLE `ht_ticket_2024` LIKE `ht_ticket`;
CREATE TABLE `ht_ticket_2025` LIKE `ht_ticket`;
CREATE TABLE `ht_ticket_2026` LIKE `ht_ticket`;
