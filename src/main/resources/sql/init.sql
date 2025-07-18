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


CREATE TABLE `ht_pay_0` (
                           `id` BIGINT PRIMARY KEY,
                           `order_id` VARCHAR(50) NOT NULL,
                           `amount` int NOT NULL,
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件信息';

CREATE TABLE `ht_pay_1` LIKE `ht_pay_0`;

-- hint 分片
CREATE TABLE ht_hint_0 (
                           id BIGINT PRIMARY KEY,
                           name VARCHAR(100)
);
CREATE TABLE ht_hint_1 LIKE ht_hint_0;
CREATE TABLE ht_hint_2 LIKE ht_hint_0;

-- 不分表
CREATE TABLE ht_log (
                        id BIGINT PRIMARY KEY,
                        content TEXT
);


CREATE TABLE `ht_prov_trans_2024` (
                             `id` BIGINT PRIMARY KEY,
                             `year` VARCHAR(4) NOT NULL,
                             `prov` VARCHAR(6) NOT NULL,
                             `amount` DOUBLE NOT NULL,
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省交易信息';

CREATE TABLE ht_prov_trans_2025 LIKE ht_prov_trans_2024;
CREATE TABLE ht_prov_trans_2026 LIKE ht_prov_trans_2024;

CREATE TABLE `user_order_0` (
                                      `order_id` BIGINT PRIMARY KEY,
                                      `user_name` VARCHAR(100) NULL,
                                      `amount` DOUBLE NOT NULL,
                                      `create_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省交易信息';

CREATE TABLE user_order_1 LIKE user_order_0;
CREATE TABLE user_order_2 LIKE user_order_0;
CREATE TABLE user_order_3 LIKE user_order_0;
CREATE TABLE user_order_4 LIKE user_order_0;
CREATE TABLE user_order_5 LIKE user_order_0;
