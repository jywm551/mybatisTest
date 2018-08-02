USE mybatis;

CREATE TABLE country (
id           INT          NOT NULL AUTO_INCREMENT,
country_name VARCHAR(255) NULL,
country_code VARCHAR(255) NULL,
PRIMARY KEY (id)
);

INSERT INTO country (country_name, country_code) VALUES
('中国', 'CN'), ('美国', 'US'), ('俄罗斯', 'RU'), ('英国', 'GB'), ('法国', 'FR');

