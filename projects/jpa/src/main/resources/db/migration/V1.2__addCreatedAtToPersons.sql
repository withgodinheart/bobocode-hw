ALTER TABLE persons
    ADD COLUMN created_at timestamp NOT NULL DEFAULT now();