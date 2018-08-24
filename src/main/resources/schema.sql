DROP TABLE `restaurant_review` IF EXISTS;

CREATE TABLE `restaurant_review` 
(`review_id` INT NOT NULL,
  `restaurant_id` INT NULL,
  `reviewer_name` VARCHAR(45) NULL,
  `reviewer_rating` INT NULL,
  `restaurant_review` VARCHAR(45) NULL, 
PRIMARY KEY (`review_id`));
