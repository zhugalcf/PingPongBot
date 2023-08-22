CREATE TABLE user_messages (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    user_id bigint NOT NULL,
    message varchar(4096),
    received_date timestamptz NOT NULL
);