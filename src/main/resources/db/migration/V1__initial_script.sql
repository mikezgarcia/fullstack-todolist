DROP SCHEMA IF EXISTS mikez cascade;
CREATE SCHEMA mikez;

CREATE TABLE mikez.todolist (
    todo_id uuid,
    todo varchar(300),
    created_date TIMESTAMP WITH TIME ZONE,
    modified_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (todo_id)
);
