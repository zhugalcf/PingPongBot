CREATE TABLE games (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    user_id bigint NOT NULL,
    win_game boolean DEFAULT false NOT NULL,
    received_date timestamptz NOT NULL
);