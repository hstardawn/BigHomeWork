CREATE DATABASE IF NOT EXISTS `hw`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `hw`;

CREATE TABLE `user`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `username`  VARCHAR(20) NOT NULL,
    `name`      VARCHAR(20) NOT NULL,
    `password`  VARCHAR(20) NOT NULL,
    `user_type` INT         NOT NULL
);

CREATE TABLE `post`
(
    `id`         INT AUTO_INCREMENT PRIMARY KEY,
    `content`    TEXT     NOT NULL,
    `user_id`    INT      NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TINYINT           DEFAULT 0
);

CREATE TABLE `report`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY,
    `user_id`     INT          NOT NULL,
    `reporter_id` INT          NOT NULL,
    `status`      TINYINT      NOT NULL,
    `reason`      VARCHAR(255) NOT NULL
);

CREATE TABLE `like`
(
    `id`      INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT     NOT NULL,
    `post_id` INT     NOT NULL,
    `status`  BOOLEAN NOT NULL DEFAULT true,

    unique `like_unique` (`user_id`, `post_id`)
);
