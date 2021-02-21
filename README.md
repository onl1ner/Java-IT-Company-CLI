# IT Company CLI application.

Simple CLI application written in Java, with the defined hierarchy of IT company employees. In the application 4 type of actions can be done, you can view employees, view projects, also you can add an employee and add a new project. Application is connected with PostgreSQL DBMS, so all data is persistent.

## Getting started

### PostgreSQL
Type these DDL statements in PostgreSQL query tool:

```sql
CREATE TABLE employee (
    worker_id SERIAL,
    name VARCHAR(255),
    hourly_salary INT,

    PRIMARY KEY (worker_id)
);

CREATE TABLE project (
    id SERIAL,
    name VARCHAR(255),
    total_hour INT,
    total_cost INT,

    PRIMARY KEY (id)
);

CREATE TABLE emp_to_proj (
    worker_id INT,
    project_id INT,

    PRIMARY KEY (worker_id, project_id)
);
```

### Java
Change the database name, user and password in the constructor of `NetworkService` class in the `NetworkService.java` file. If you are struggling with finding the `NetworkService.java` file please refer to folder structure below.

## Folder Structure

The workspace structure is:

```bash
├── README.md
├── lib                                 # the folder to maintain dependencies
│   └── postgresql-42.2.18.jar          # postgresql driver
└── src
    ├── App.java                        # entry point of an application
    ├── models                          # the folder to maintain all models
    │   ├── Designer.java
    │   ├── Developer.java
    │   ├── Employee.java
    │   └── Project.java
    └── services                        # the folder to maintain services
        └── network                     # the folder for services that providing the network connectivity 
            ├── INetworkService.java
            └── NetworkService.java
```

## Author
Tamerlan Satualdypov. Group: SE-2018