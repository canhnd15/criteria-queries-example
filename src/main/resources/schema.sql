-- Create the Department table
CREATE TABLE IF NOT EXISTS department (
                            id SERIAL PRIMARY KEY,  -- Primary key for Department
                            name VARCHAR(255) NOT NULL  -- Name of the department
);

COMMENT ON TABLE department IS 'Stores department details';
COMMENT ON COLUMN department.id IS 'Primary key, auto-incremented';
COMMENT ON COLUMN department.name IS 'Name of the department';

-- Create the Employee table
CREATE TABLE IF NOT EXISTS employee (
                          id SERIAL PRIMARY KEY,  -- Primary key for Employee
                          first_name VARCHAR(255) NOT NULL,  -- First name of the employee
                          last_name VARCHAR(255) NOT NULL,  -- Last name of the employee
                          position VARCHAR(255) NOT NULL,  -- Position of the employee
                          department_id INT,  -- Foreign key to reference the Department table
                          CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department(id)  -- Foreign key constraint
);

COMMENT ON TABLE employee IS 'Stores employee details';
COMMENT ON COLUMN employee.id IS 'Primary key, auto-incremented';
COMMENT ON COLUMN employee.first_name IS 'First name of the employee';
COMMENT ON COLUMN employee.last_name IS 'Last name of the employee';
COMMENT ON COLUMN employee.position IS 'Position of the employee';
COMMENT ON COLUMN employee.department_id IS 'Foreign key referencing department.id';