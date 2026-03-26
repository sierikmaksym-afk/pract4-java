CREATE TABLE employees (
   id INTEGER PRIMARY KEY,
   type VARCHAR(50) NOT NULL,
   name VARCHAR(255) NOT NULL,
   salary DOUBLE PRECISION NOT NULL,
   contract_months INTEGER,
   bonus DOUBLE PRECISION,
   work_country VARCHAR(255),
   team_size INTEGER
);