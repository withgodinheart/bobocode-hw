CREATE TABLE IF NOT EXISTS urls
(
    id         VARCHAR(5) PRIMARY KEY,
--     short_url  TEXT UNIQUE NOT NULL,
    url        TEXT        NOT NULL,
    title      TEXT        NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT now()
)