-- Data initialization script
--SELECT 1;

-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Truncate all tables
TRUNCATE TABLE auth;
TRUNCATE TABLE garbage;
TRUNCATE TABLE garbage_monthly;
TRUNCATE TABLE garbage_weekly;
TRUNCATE TABLE newsletter;
TRUNCATE TABLE ranking;
TRUNCATE TABLE user;
TRUNCATE TABLE user_email_verification;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `user` (`id`, `created_at`, `email`, `onboarding_status`, `user_password`, `updated_at`, `username`) VALUES
(1, '2024-08-09 16:21:58.272364', 'ecopickertest1@gmail.com', 2, '$2a$10$rYykX5oCNsCrOoPCA9KNjOgUKieYjFNy98I35idrXU/Ioje81gIZC', NULL, 'testuser1'),
(2, '2024-08-09 16:22:07.075542', 'ecopickertest2@gmail.com', 2, '$2a$10$Ekpmpsp4APxZVog.SqZ.PescdI.dbqbRD2vf81doRRcnYjvidNrrK', NULL, 'testuser2'),
(3, '2024-08-09 16:22:13.012663', 'ecopickertest3@gmail.com', 2, '$2a$10$Btq3toocv93u5YIrF42dv.KxoMaCO3NCPmKF1TjUlXC4o9q6YuKV.', NULL, 'testuser3'),
(4, '2024-08-09 16:22:18.194022', 'ecopickertest4@gmail.com', 2, '$2a$10$bYuWEFJpK20h704j7DYQ.uYMTIqiBCvgvHn71XUkiwPjDz0Y61Sz.', NULL, 'testuser4'),
(5, '2024-08-09 16:22:23.450457', 'ecopickertest5@gmail.com', 2, '$2a$10$5h8y9mspkEF0z.2RBlScD.qZTY4rpbPjDKQoZ8ObgDTtthAD9HO/2', NULL, 'testuser5'),
(6, '2024-08-09 16:22:28.134366', 'ecopickertest6@gmail.com', 2, '$2a$10$CtYjifitefhTReIQdqoUreq0yVxkobO3gV2N9WYbWa5T/boD8a5GO', NULL, 'testuser6'),
(7, '2024-08-09 16:22:34.785037', 'ecopickertest7@gmail.com', 2, '$2a$10$scjcperHZ9s5zntZA2mxk.V1OeIvVQBHJW9.NlfoVHhQVK/04Rtr6', NULL, 'testuser7'),
(8, '2024-08-09 16:22:41.596740', 'ecopickertest8@gmail.com', 2, '$2a$10$5nAC6iJsunuDhpOcOg5AMOObKTDQChOI/uVb0lGVy2U9584LlFEjG', NULL, 'testuser8'),
(9, '2024-08-09 16:22:48.868552', 'ecopickertest9@gmail.com', 2, '$2a$10$MdOgO4k5.GTS6Nuf9/bNauHM9qoD9bbNR6mcu51eP0ikFxvC8DCBG', NULL, 'testuser9'),
(10, '2024-08-09 16:22:56.222953', 'ecopickertest10@gmail.com', 2, '$2a$10$aNu5JAc9XmBxiBRfK94Fqe7n5uJkXDnNoC9QpdZ3/WXY03u8oGPuq', NULL, 'testuser10'),
(11, '2024-08-09 16:23:02.204235', 'ecopickertest11@gmail.com', 2, '$2a$10$rX/P2V8ZLEvfhmjv33v4Veds5HhP8avERbxZMokocsVQowfB0gbfS', NULL, 'testuser11'),
(12, '2024-08-09 16:23:07.587250', 'ecopickertest12@gmail.com', 2, '$2a$10$nEgJuLrJgnsvMeuPE/ZZwesbv8tmuEutmKqpMmVQyyON9bR9fQmV2', NULL, 'testuser12'),
(13, '2024-08-09 16:23:13.100910', 'ecopickertest13@gmail.com', 2, '$2a$10$F5aTovSpfHRTNbhphb9jDeDaVyA4RGg/tbrzzWcAOgRT73SQcTN5e', NULL, 'testuser13'),
(14, '2024-08-09 16:23:18.337797', 'ecopickertest14@gmail.com', 2, '$2a$10$gO7QHqr6jWcTJW0EmhBt.OYn6DDdNXKhmCN4KS2yrSOCiVyIV0fCG', NULL, 'testuser14'),
(15, '2024-08-09 16:23:23.352336', 'ecopickertest15@gmail.com', 2, '$2a$10$BT.MeNHxEA8pZrvpZo6FvupFWF22B5psXL45My5crjl2j/ksvkHz.', NULL, 'testuser15');



-- Inserting into garbage table
INSERT INTO garbage (id, category, collected_at, created_at, latitude, longitude, garbage_name, updated_at, user_id) VALUES
(1, 'PLASTIC', '2021-03-15 11:30:20.000000', '2024-08-09 16:59:10.370277', 43.6532, -79.3832, 'Water Bottle', '2024-08-09 16:59:10.370294', 1),
(2, 'METAL', '2021-11-07 14:45:33.000000', '2024-08-09 16:59:21.478634', 43.7001, -79.4163, 'Soda Can', '2024-08-09 16:59:21.478640', 1),
(3, 'GLASS', '2022-02-21 09:20:45.000000', '2024-08-09 16:59:32.068106', 43.6617, -79.395, 'Glass Bottle', '2024-08-09 16:59:32.068110', 1),
(4, 'CARDBOARD_PAPER', '2022-07-18 18:10:58.000000', '2024-08-09 16:59:41.291319', 43.6761, -79.4105, 'Pizza Box', '2024-08-09 16:59:41.291323', 1),
(5, 'FOOD_SCRAPS', '2022-12-03 12:25:40.000000', '2024-08-09 16:59:51.256185', 43.6434, -79.379, 'Apple Core', '2024-08-09 16:59:51.256187', 1),
(6, 'PLASTIC', '2025-04-22 16:35:22.000000', '2024-08-09 17:00:02.668291', 43.6677, -79.3948, 'Plastic Bag', '2024-08-09 17:00:02.668294', 1),
(7, 'OTHER', '2025-09-14 07:55:11.000000', '2024-08-09 17:00:13.312722', 43.651, -79.347, 'Broken Toy', '2024-08-09 17:00:13.312725', 1),
(8, 'CARDBOARD_PAPER', '2024-03-21 09:15:32.000000', '2024-08-09 17:02:58.164045', 43.6544, -79.3807, 'Coffee Cup', '2024-08-09 17:02:58.164050', 1),
(9, 'FOOD_SCRAPS', '2024-03-27 13:47:19.000000', '2024-08-09 17:03:11.595389', 43.6684, -79.3966, 'Banana Peel', '2024-08-09 17:03:11.595392', 1),
(10, 'GLASS', '2024-02-15 17:02:45.000000', '2024-08-09 17:03:22.863672', 43.6415, -79.3773, 'Glass Jar', '2024-08-09 17:03:22.863674', 1),
(11, 'PLASTIC', '2024-07-21 10:25:47.000000', '2024-08-09 17:06:52.569212', 49.2827, -123.1207, 'Plastic Straw', '2024-08-09 17:06:52.569215', 4),
(12, 'METAL', '2024-07-22 14:55:33.000000', '2024-08-09 17:07:03.978356', 49.2636, -123.1386, 'Tin Can', '2024-08-09 17:07:03.978359', 4),
(13, 'CARDBOARD_PAPER', '2024-07-25 08:43:21.000000', '2024-08-09 17:07:14.396318', 49.2768, -123.1236, 'Newspaper', '2024-08-09 17:07:14.396321', 4),
(14, 'FOOD_SCRAPS', '2023-03-01 16:12:39.000000', '2024-08-09 17:07:23.895247', 49.2875, -123.1148, 'Apple Core', '2024-08-09 17:07:23.895250', 4),
(15, 'PLASTIC', '2023-05-14 15:22:11.000000', '2024-08-09 17:10:12.060391', 43.6515, -79.3832, 'Plastic Fork', '2024-08-09 17:10:12.060410', 9),
(16, 'METAL', '2024-01-23 10:38:56.000000', '2024-08-09 17:10:23.940834', 43.6656, -79.4003, 'Aluminum Foil', '2024-08-09 17:10:23.940837', 9),
(17, 'CARDBOARD_PAPER', '2022-11-09 08:14:37.000000', '2024-08-09 17:10:32.155721', 43.6487, -79.3772, 'Cereal Box', '2024-08-09 17:10:32.155724', 9);

-- Inserting into garbage_weekly table
INSERT INTO garbage_weekly (id, cardboard_paper, collected_week, created_at, food_scraps, glass, metal, other, plastic, total_score, updated_at, user_id, collected_year) VALUES
(1, 0, 11, '2024-08-09 16:59:10.386227', 0, 0, 0, 0, 1, 1, '2024-08-09 16:59:10.386965', 1, 2021),
(2, 0, 44, '2024-08-09 16:59:21.489983', 0, 0, 1, 0, 0, 2, '2024-08-09 16:59:21.490016', 1, 2021),
(3, 0, 8, '2024-08-09 16:59:32.077327', 0, 1, 0, 0, 0, 3, '2024-08-09 16:59:32.077350', 1, 2022),
(4, 1, 29, '2024-08-09 16:59:41.301079', 0, 0, 0, 0, 0, 4, '2024-08-09 16:59:41.301099', 1, 2022),
(5, 0, 48, '2024-08-09 16:59:51.265249', 1, 0, 0, 0, 0, 5, '2024-08-09 16:59:51.265269', 1, 2022),
(6, 0, 17, '2024-08-09 17:00:02.677654', 0, 0, 0, 0, 1, 1, '2024-08-09 17:00:02.677666', 1, 2025),
(7, 0, 37, '2024-08-09 17:00:13.322223', 0, 0, 0, 1, 0, 6, '2024-08-09 17:00:13.322243', 1, 2025),
(8, 1, 12, '2024-08-09 17:02:58.175048', 0, 0, 0, 0, 0, 4, '2024-08-09 17:02:58.175061', 1, 2024),
(9, 0, 13, '2024-08-09 17:03:11.605003', 1, 0, 0, 0, 0, 5, '2024-08-09 17:03:11.605015', 1, 2024),
(10, 0, 7, '2024-08-09 17:03:22.871524', 0, 1, 0, 0, 0, 3, '2024-08-09 17:03:22.871536', 1, 2024),
(11, 0, 29, '2024-08-09 17:06:52.577712', 0, 0, 0, 0, 1, 1, '2024-08-09 17:06:52.577726', 4, 2024),
(12, 1, 30, '2024-08-09 17:07:03.985990', 0, 0, 1, 0, 0, 6, '2024-08-09 17:07:14.404938', 4, 2024),
(13, 0, 9, '2024-08-09 17:07:23.904468', 1, 0, 0, 0, 0, 5, '2024-08-09 17:07:23.904480', 4, 2023),
(14, 0, 19, '2024-08-09 17:10:12.071074', 0, 0, 0, 0, 1, 1, '2024-08-09 17:10:12.071092', 9, 2023),
(15, 0, 4, '2024-08-09 17:10:23.947889', 0, 0, 1, 0, 0, 2, '2024-08-09 17:10:23.947901', 9, 2024),
(16, 1, 45, '2024-08-09 17:10:32.164097', 0, 0, 0, 0, 0, 4, '2024-08-09 17:10:32.164109', 9, 2022);

-- Inserting into garbage_monthly table
INSERT INTO garbage_monthly (id, cardboard_paper, collected_month, created_at, food_scraps, glass, metal, other, plastic, total_score, updated_at, user_id) VALUES
(1, 0, '2021-03', '2024-08-09 16:59:10.399061', 0, 0, 0, 0, 1, 1, '2024-08-09 16:59:10.399099', 1),
(2, 0, '2021-11', '2024-08-09 16:59:21.501742', 0, 0, 1, 0, 0, 2, '2024-08-09 16:59:21.501772', 1),
(3, 0, '2022-02', '2024-08-09 16:59:32.088357', 0, 1, 0, 0, 0, 3, '2024-08-09 16:59:32.088378', 1),
(4, 1, '2022-07', '2024-08-09 16:59:41.312373', 0, 0, 0, 0, 0, 4, '2024-08-09 16:59:41.312393', 1),
(5, 0, '2022-12', '2024-08-09 16:59:51.276035', 1, 0, 0, 0, 0, 5, '2024-08-09 16:59:51.276054', 1),
(6, 0, '2025-04', '2024-08-09 17:00:02.686625', 0, 0, 0, 0, 1, 1, '2024-08-09 17:00:02.686636', 1),
(7, 0, '2025-09', '2024-08-09 17:00:13.331214', 0, 0, 0, 1, 0, 6, '2024-08-09 17:00:13.331231', 1),
(8, 1, '2024-03', '2024-08-09 17:02:58.187217', 1, 0, 0, 0, 0, 9, '2024-08-09 17:03:11.614000', 1),
(9, 0, '2024-02', '2024-08-09 17:03:22.880344', 0, 1, 0, 0, 0, 3, '2024-08-09 17:03:22.880355', 1),
(10, 1, '2024-07', '2024-08-09 17:06:52.587039', 0, 0, 1, 0, 1, 7, '2024-08-09 17:07:14.408551', 4),
(11, 0, '2023-03', '2024-08-09 17:07:23.913354', 1, 0, 0, 0, 0, 5, '2024-08-09 17:07:23.913367', 4),
(12, 0, '2023-05', '2024-08-09 17:10:12.080721', 0, 0, 0, 0, 1, 1, '2024-08-09 17:10:12.080731', 9),
(13, 0, '2024-01', '2024-08-09 17:10:23.957469', 0, 0, 1, 0, 0, 2, '2024-08-09 17:10:23.957481', 9),
(14, 1, '2022-11', '2024-08-09 17:10:32.173348', 0, 0, 0, 0, 0, 4, '2024-08-09 17:10:32.173359', 9);


INSERT INTO newsletter (title, content, source, category, published_at, created_at, updated_at)
VALUES ('Canada Announces New Recycling Targets',
        'The Canadian government has announced ambitious new recycling targets, aiming to recycle 90% of all packaging by 2030. The plan includes investments in infrastructure and technology, as well as measures to encourage businesses and consumers to adopt more sustainable practices.',
        'https://www.cbc.ca/news/canada/canada-announces-new-recycling-targets-1.6645313', 'NEWS',
        '2023-10-27 00:00:00', NOW(), NULL),
       ('South Korea Launches National Recycling Campaign',
        'South Korea has launched a nationwide recycling campaign aimed at increasing awareness and participation in waste reduction and recycling efforts. The campaign features public service announcements, educational materials, and community events.',
        'https://www.koreatimes.co.kr/www/news/nation/2023/10/123_348260.html', 'NEWS', '2023-10-26 00:00:00', NOW(),
        NULL),
       ('New Study Highlights Environmental Impact of Plastic Waste',
        'A recent study published in the journal Nature has revealed the significant environmental impact of plastic waste, particularly in the ocean. The study found that plastic pollution is harming marine ecosystems and contributing to climate change.',
        'https://www.nature.com/articles/s41586-023-06724-w', 'NEWS', '2023-10-25 00:00:00', NOW(), NULL),
       ('Recycle Right!',
        'Learn how to properly recycle different materials like paper, plastic, and glass. This guide explains the different recycling bins, what items belong in each, and how to prepare your recyclables for collection.',
        'https://www.recyclingcouncil.org/recycle-right/', 'EDUCATION', '2023-10-27 00:00:00', NOW(), NULL),
       ('The Journey of a Recycled Bottle',
        'Follow the fascinating journey of a plastic bottle from your recycling bin to its transformation into new products. Learn about the different stages of recycling and how recycled materials are used to create new items.',
        'https://www.terracycle.com/en-US/learn/the-journey-of-a-recycled-bottle', 'EDUCATION', '2023-10-26 00:00:00',
        NOW(), NULL),
       ('Fun Ways to Reduce Waste with Kids',
        'Engage children in environmental stewardship with fun activities like crafting with recycled materials, composting food scraps, and learning about the importance of conserving water and energy.',
        'https://www.epa.gov/recycle/kids-waste-free-living', 'EDUCATION', '2023-10-25 00:00:00', NOW(), NULL),
       ('Family Eco-Challenge',
        'Join a fun, family-friendly challenge to reduce your environmental impact. Participants will be given a list of eco-friendly tasks to complete throughout the month, from using reusable bags to planting a tree. Prizes will be awarded to the most environmentally conscious families!',
        'https://www.eventbrite.ca/e/family-eco-challenge-tickets-70708920865', 'EVENT', '2023-10-27 00:00:00', NOW(),
        NULL),
       ('Online Workshop: Composting 101',
        'Learn how to start your own compost bin at home! This interactive online workshop will cover everything from the basics of composting to choosing the right materials and composting methods. Register now for a free session!',
        'https://www.greenliving.ca/events/online-workshop-composting-101', 'EVENT', '2023-10-26 00:00:00', NOW(),
        NULL),
       ('Online Workshop: Composting 101',
        'Learn how to start your own compost bin at home! This interactive online workshop will cover everything from the basics of composting to choosing the right materials and composting methods. Register now for a free session!',
        'https://www.greenliving.ca/events/online-workshop-composting-101', 'EVENT', '2023-10-26 00:00:00', NOW(),
        NULL),
       ('Online Workshop: Composting 101',
        'Learn how to start your own compost bin at home! This interactive online workshop will cover everything from the basics of composting to choosing the right materials and composting methods. Register now for a free session!',
        'https://www.greenliving.ca/events/online-workshop-composting-101', 'EVENT', '2023-10-26 00:00:00', NOW(),
        NULL),
       ('Online Workshop: Composting 101',
        'Learn how to start your own compost bin at home! This interactive online workshop will cover everything from the basics of composting to choosing the right materials and composting methods. Register now for a free session!',
        'https://www.greenliving.ca/events/online-workshop-composting-101', 'EVENT', '2023-10-26 00:00:00', NOW(),
        NULL),
       ('Volunteer at a Local Recycling Center',
        'Make a difference in your community by volunteering at your local recycling center. Volunteers help sort materials, organize recycling bins, and educate others about recycling best practices. Sign up for a shift today!',
        'https://www.volunteermatch.org/search/opp?q=recycling+center+volunteer&l=Canada', 'EVENT',
        '2023-10-25 00:00:00', NOW(), NULL);
