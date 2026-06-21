-- Очистка таблиц перед вставкой данных
SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE filmorate.users_films_likes RESTART IDENTITY;
TRUNCATE TABLE filmorate.film_mpa_rating RESTART IDENTITY;
TRUNCATE TABLE filmorate.film_genre RESTART IDENTITY;
TRUNCATE TABLE filmorate.friendships RESTART IDENTITY;
TRUNCATE TABLE filmorate.films RESTART IDENTITY;
TRUNCATE TABLE filmorate.mpa_rating RESTART IDENTITY;
TRUNCATE TABLE filmorate.genre RESTART IDENTITY;
TRUNCATE TABLE filmorate.users RESTART IDENTITY;
SET REFERENTIAL_INTEGRITY TRUE;

-- 1. Пользователи
INSERT INTO filmorate.users (email, login, user_name, birthday) VALUES
    ('ivanov@mail.com', 'ivanov', 'Иван Иванов', '1990-05-15'),
    ('petrov@mail.com', 'petrov', 'Пётр Петров', '1985-12-01'),
    ('sidorova@mail.com', 'sidorova', 'Анна Сидорова', '2000-03-10'),
    ('kuznetsov@mail.com', 'kuznetsov', 'Олег Кузнецов', '1995-07-22');

-- 2. Жанры
INSERT INTO filmorate.genre (name) VALUES
    ('Комедия'),
    ('Драма'),
    ('Мультфильм'),
    ('Триллер'),
    ('Документальный'),
    ('Боевик');

-- 3. Рейтинги MPA
INSERT INTO filmorate.mpa_rating(rating_name)
VALUES ('G'),
    ('PG'),
    ('PG-13'),
    ('R'),
    ('NC-17');

-- 4. Фильмы
INSERT INTO filmorate.films (film_name, description, release_date, duration) VALUES
    ('Назад в будущее', 'Приключения подростка и учёного во времени.', '1985-07-03', 116),
    ('Интерстеллар', 'Путешествие через червоточину в поисках нового дома.', '2014-11-07', 169),
    ('Зелёная миля', 'История о тюремном надзирателе, столкнувшемся с чудом.', '1999-12-10', 189),
    ('Остров проклятых', 'Федеральный маршал расследует исчезновение пациентки.', '2010-02-18', 138);

-- 5. Связи фильмов с жанрами (film_genre)
INSERT INTO filmorate.film_genre (film_id, genre_id) VALUES
    (1, 1), -- Назад в будущее -> Комедия
    (1, 3), -- Назад в будущее -> Фантастика
    (2, 3), -- Интерстеллар -> Фантастика
    (2, 2), -- Интерстеллар -> Драма
    (3, 2), -- Зелёная миля -> Драма
    (4, 4), -- Остров проклятых -> Триллер
    (4, 2); -- Остров проклятых -> Драма

-- 6. Связи фильмов с рейтингами MPA (film_mpa_rating)
INSERT INTO filmorate.film_mpa_rating (film_id, film_mparating) VALUES
    (1, 3), -- Назад в будущее -> PG_13
    (2, 3), -- Интерстеллар -> PG_13
    (3, 4), -- Зелёная миля -> R
    (4, 4); -- Остров проклятых -> R

-- 7. Лайки пользователей (users_films_likes)
-- Пользователь 1 (ivanov) лайкнул фильмы 1 и 2
-- Пользователь 2 (petrov) лайкнул фильм 2
-- Пользователь 3 (sidorova) лайкнула фильм 3
INSERT INTO filmorate.users_films_likes (film_id, user_id) VALUES
   (1, 1),
   (2, 1),
   (2, 2),
   (3, 3);

-- 8. Дружба между пользователями (friendships)
-- Пользователь 1 отправил заявку пользователю 2 (подтверждено)
-- Пользователь 2 отправил заявку пользователю 3 (не подтверждено)
-- Пользователь 1 отправил заявку пользователю 3 (подтверждено)
INSERT INTO filmorate.friendships (user_id, friend_id, status, request_date, accepted_date)
VALUES
    (1, 2, 'CONFIRMED', CURRENT_DATE - 5, CURRENT_DATE - 4),
    (2, 3, 'NOT_CONFIRMED', CURRENT_DATE - 2, NULL),
    (1, 3, 'CONFIRMED', CURRENT_DATE - 3, CURRENT_DATE-2);