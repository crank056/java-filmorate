# java-filmorate
Template repository for Filmorate project.
link to diagramm of database
https://github.com/crank056/java-filmorate/blob/add_db_scheme/scheme.png
examples of request to DB:
________________
- get user name:
SELECT name
FROM user
WHERE user_id = '1';
- get friends
SELECT friend_id
FROM friends
WHERE user_id = '1';
- isConfirmed
SELECT is_confirmed
FROM friends
WHERE friend_id = '2' AND user_id = '1';
- get film genre
SELECT f.name AS film,
g.name AS genre
FROM film AS f
LEFT JOIN film_genre AS fg ON f.film_id = fg.film_id
LEFT JOIN genre AS g ON fg.genre_id = g.genre_id
WHERE film = 'matrix';

