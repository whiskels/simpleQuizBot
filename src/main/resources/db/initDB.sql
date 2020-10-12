DROP TABLE IF EXISTS user_answered_questions;
DROP TABLE IF EXISTS java_quiz;
DROP TABLE IF EXISTS users;
CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    chat_id    INTEGER UNIQUE                NOT NULL,
    name       VARCHAR                       NOT NULL,
    score      INTEGER             DEFAULT 0 NOT NULL,
    high_score INTEGER             DEFAULT 0 NOT NULL,
    bot_state  VARCHAR                       NOT NULL
);
CREATE UNIQUE INDEX users_unique_chatid_idx ON users (chat_id);

CREATE TABLE java_quiz
(
    id             INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    question       VARCHAR NOT NULL,
    answer_correct VARCHAR NOT NULL,
    option1        VARCHAR NOT NULL,
    option2        VARCHAR NOT NULL,
    option3        VARCHAR NOT NULL
);
