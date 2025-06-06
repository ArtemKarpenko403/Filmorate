# 🎬 Filmorate - Социальная сеть для киноманов

## 📖 Описание проекта

**Filmorate** - это социальная сеть для любителей кино, которая позволяет:

- 📝 Создавать персональные профили
- 🎞 Добавлять фильмы в базу данных
- 👍 Оценивать фильмы лайками
- 👥 Находить друзей по интересам
- 🏆 Открывать популярные фильмы

### 🔑 Основные функции

**✨ Управление пользователями**
- Регистрация и настройка профиля
- Система друзей с подтверждением
- Персональные рекомендации

**🎥 Каталог фильмов**
- База фильмов с детальной информацией
- Возрастные рейтинги MPA
- Фильтрация по жанрам
- Поиск по различным критериям

**❤️ Социальные функции**
- Лайки и рейтинги фильмов
- Поиск общих друзей
- Топ-10 популярных фильмов

## 🛠️ Технологии

| Компонент       | Технологии                          |
|----------------|-------------------------------------|
| Бэкенд        | Java 17, Spring Boot                |
| База данных   | H2 (встроенная), PostgreSQL         |
| API           | REST                                |
| Сборка        | Maven                               |
| Тесты         | JUnit 5, Mockito                    |

## 🗃️ Структура базы данных

![Диаграмма базы данных](path/to/diagram.png)

### Основные таблицы

| Таблица        | Описание                             |
|---------------|--------------------------------------|
| `users`       | Профили пользователей                |
| `films`       | Информация о фильмах                 |
| `mpa_ratings` | Возрастные рейтинги (G, PG-13 и др.) |
| `genres`      | Жанры фильмов                        |
| `film_genres` | Связь фильмов и жанров               |
| `likes`       | Лайки пользователей                  |
| `friends`     | Связи дружбы                         |

## 🔍 Примеры SQL-запросов


```sql
1. Получение всех фильмов с деталями
SELECT 
  f.film_id,
  f.title,
  f.description,
  f.release_date,
  f.duration,
  m.name AS mpa_rating,
  STRING_AGG(g.name, ', ') AS genres
FROM films f
JOIN mpa_ratings m ON f.mpa_rating_id = m.mpa_rating_id
LEFT JOIN film_genres fg ON f.film_id = fg.film_id
LEFT JOIN genres g ON fg.genre_id = g.genre_id
GROUP BY f.film_id, m.name;

2. Топ-10 популярных фильмов
sql
SELECT
  f.film_id,
  f.title,
  COUNT(l.user_id) AS likes_count
FROM films f
LEFT JOIN likes l ON f.film_id = l.film_id
GROUP BY f.film_id
ORDER BY likes_count DESC
LIMIT 10;

3. Поиск общих друзей
sql
SELECT 
  u.user_id,
  u.login,
  u.name
FROM friends f1
JOIN friends f2 ON f1.friend_id = f2.friend_id
JOIN users u ON f1.friend_id = u.user_id
WHERE f1.user_id = :userId1 
  AND f2.user_id = :userId2
  AND f1.status = 'confirmed'
  AND f2.status = 'confirmed';

4. Фильмы по жанру
sql
SELECT
  f.film_id,
  f.title
FROM films f
JOIN film_genres fg ON f.film_id = fg.film_id
JOIN genres g ON fg.genre_id = g.genre_id
WHERE g.name = :genreName;

5. Список друзей пользователя
sql
SELECT
  u.user_id,
  u.login,
  u.name,
  f.status
FROM friends f
JOIN users u ON f.friend_id = u.user_id
WHERE f.user_id = :userId;