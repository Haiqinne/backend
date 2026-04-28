-- Tắt FK
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE image;
TRUNCATE TABLE author_detail;
TRUNCATE TABLE product;
TRUNCATE TABLE author;
TRUNCATE TABLE publisher;
TRUNCATE TABLE genre;
TRUNCATE TABLE category;

SET FOREIGN_KEY_CHECKS = 1;

-- ========================
-- 1. CATEGORY
-- ========================
INSERT INTO category (category_id, category_name) VALUES
                                                      (1, 'Văn học'),
                                                      (2, 'Kinh tế'),
                                                      (3, 'Kỹ năng sống'),
                                                      (4, 'Thiếu nhi'),
                                                      (5, 'Công nghệ');

-- ========================
-- 2. GENRE
-- ========================
INSERT INTO genre (genre_id, genre_name, category_id) VALUES
                                                          (1, 'Tiểu thuyết', 1),
                                                          (2, 'Truyện ngắn', 1),
                                                          (3, 'Kinh doanh', 2),
                                                          (4, 'Marketing', 2),
                                                          (5, 'Phát triển bản thân', 3),
                                                          (6, 'Truyện tranh', 4),
                                                          (7, 'Lập trình', 5),
                                                          (8, 'AI', 5);

-- ========================
-- 3. PUBLISHER
-- ========================
INSERT INTO publisher (publisher_id, publisher_name) VALUES
                                                         (1, 'NXB Trẻ'),
                                                         (2, 'NXB Văn học'),
                                                         (3, 'NXB CNTT'),
                                                         (4, 'NXB Kinh tế'),
                                                         (5, 'NXB Thiếu nhi');

-- ========================
-- 4. AUTHOR
-- ========================
INSERT INTO author (author_id, author_name) VALUES
                                                (1, 'Dale Carnegie'),
                                                (2, 'Paulo Coelho'),
                                                (3, 'Robert C. Martin'),
                                                (4, 'Napoleon Hill'),
                                                (5, 'J.K. Rowling'),
                                                (6, 'Sun Tzu'),
                                                (7, 'Yuval Noah Harari'),
                                                (8, 'Brian Tracy');

-- ========================
-- 5. PRODUCT
-- ========================
INSERT INTO product (
    product_id,
    product_name,
    price,
    product_quantity,
    description,
    page_number,
    publication_year,
    stock,
    genre_id,
    publisher_id
) VALUES
      (1, 'Đắc Nhân Tâm', 120000, 50, 'Sách kỹ năng nổi tiếng', 320, 2020, 50, 5, 1),
      (2, 'Nhà Giả Kim', 90000, 30, 'Tiểu thuyết nổi tiếng', 210, 2019, 30, 1, 2),
      (3, 'Clean Code', 200000, 20, 'Sách lập trình hay', 450, 2021, 20, 7, 3),
      (4, 'Think and Grow Rich', 150000, 40, 'Sách kinh doanh kinh điển', 350, 2018, 40, 3, 4),
      (5, 'Harry Potter', 180000, 25, 'Truyện thiếu nhi nổi tiếng', 500, 2000, 25, 6, 5),
      (6, 'Nghệ Thuật Chiến Tranh', 100000, 35, 'Chiến lược quân sự cổ điển', 220, 2017, 35, 2, 2),
      (7, 'Sapiens', 150000, 30, 'Lịch sử nhân loại', 480, 2015, 30, 1, 2),
      (8, 'Goals!', 120000, 45, 'Sách phát triển bản thân', 300, 2022, 45, 5, 1),

      (9, 'Atomic Habits', 140000, 40, 'Thói quen nguyên tử', 320, 2020, 40, 5, 1),
      (10, 'The Lean Startup', 170000, 35, 'Khởi nghiệp tinh gọn', 300, 2018, 35, 3, 4),
      (11, 'Deep Work', 160000, 25, 'Làm việc sâu', 280, 2019, 25, 5, 1),
      (12, 'Rich Dad Poor Dad', 130000, 50, 'Cha giàu cha nghèo', 250, 2017, 50, 3, 4),
      (13, 'The Alchemist 2', 95000, 20, 'Phiên bản mở rộng', 230, 2021, 20, 1, 2),
      (14, 'AI Basics', 210000, 15, 'Nhập môn AI', 400, 2022, 15, 8, 3),
      (15, 'Python Crash Course', 220000, 18, 'Học Python nhanh', 500, 2021, 18, 7, 3),
      (16, 'Marketing 101', 150000, 30, 'Cơ bản marketing', 270, 2020, 30, 4, 4),
      (17, 'Fairy Tales', 2000, 60, 'Truyện cổ tích', 200, 2015, 60, 6, 5),
      (18, 'Startup Nation', 180000, 22, 'Quốc gia khởi nghiệp', 350, 2019, 22, 3, 4),
      (19, 'Machine Learning', 250000, 12, 'Học máy nâng cao', 600, 2023, 12, 8, 3),
      (20, 'Life Skills', 110000, 45, 'Kỹ năng sống', 260, 2020, 45, 5, 1);

-- ========================
-- 6. AUTHOR_DETAIL
-- ========================
INSERT INTO author_detail (product_id, author_id) VALUES
                                                      (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),
                                                      (9,1),(10,4),(11,8),(12,4),(13,2),(14,3),(15,3),(16,8),
                                                      (17,5),(18,7),(19,3),(20,8);

-- ========================
-- 7. IMAGE
-- ========================
INSERT INTO image (image_url, product_id, description) VALUES
                                                           ('/images/1.png',1,'Bìa 1'),
                                                           ('/images/2.png',1,'Bìa 2'),
                                                           ('/images/3.png',2,'Bìa 3'),
                                                           ('/images/4.png',3,'Bìa 4'),
                                                           ('/images/5.png',3,'Bìa 5'),
                                                           ('/images/6.png',4,'Bìa 6'),
                                                           ('/images/7.png',5,'Bìa 7'),
                                                           ('/images/8.png',6,'Bìa 8'),
                                                           ('/images/9.png',7,'Bìa 9'),
                                                           ('/images/10.png',8,'Bìa 10'),

                                                           ('/images/11.png',9,'Bìa 11'),
                                                           ('/images/12.png',10,'Bìa 12'),
                                                           ('/images/13.png',11,'Bìa 13'),
                                                           ('/images/14.png',12,'Bìa 14'),
                                                           ('/images/15.png',13,'Bìa 15'),
                                                           ('/images/16.png',14,'Bìa 16'),
                                                           ('/images/17.png',15,'Bìa 17'),
                                                           ('/images/18.png',16,'Bìa 18'),
                                                           ('/images/19.png',17,'Bìa 19'),
                                                           ('/images/20.png',18,'Bìa 20'),
                                                           ('/images/21.png',19,'Bìa 21'),
                                                           ('/images/22.png',20,'Bìa 22');