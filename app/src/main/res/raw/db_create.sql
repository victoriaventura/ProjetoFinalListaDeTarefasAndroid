CREATE TABLE login (
 id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
 username varchar(255) NOT NULL,
 password varchar(255) NOT NULL
);
CREATE TABLE category (
 id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
 name varchar(255) NOT NULL
);
CREATE TABLE task (
 id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
 category_id integer NOT NULL,
 name varchar(255) NOT NULL,
 description varchar(500),
 dueDate Date,
 FOREIGN KEY (category_id) REFERENCES category (id)
);