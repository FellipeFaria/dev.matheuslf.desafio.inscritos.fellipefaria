CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    CONSTRAINT ck_date CHECK (start_date < end_date)
);

CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    status VARCHAR(5) NOT NULL DEFAULT 'TODO',
    priority VARCHAR(6) NOT NULL,
    due_date TIMESTAMP NOT NULL,
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    CONSTRAINT ck_status CHECK (status IN ('TODO', 'DOING', 'DONE')),
    CONSTRAINT ck_priority CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH'))
);