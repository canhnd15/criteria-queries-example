-- Insert dummy data into department table
INSERT INTO department (name)
VALUES
    ('Human Resources'),
    ('IT'),
    ('Finance'),
    ('Marketing'),
    ('Sales');

-- Insert dummy data into employee table
INSERT INTO employee (first_name, last_name, position, department_id)
VALUES
    ('John', 'Doe', 'Software Engineer', 2),  -- IT Department
    ('Jane', 'Smith', 'HR Manager', 1),  -- Human Resources Department
    ('Robert', 'Brown', 'Accountant', 3),  -- Finance Department
    ('Alice', 'Johnson', 'Marketing Specialist', 4),  -- Marketing Department
    ('Michael', 'Clark', 'Sales Manager', 5);  -- Sales Department