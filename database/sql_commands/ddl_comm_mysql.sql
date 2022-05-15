CREATE TABLE users (
	username varchar(100) NOT NULL,
	password varchar(100) NOT NULL,
	PRIMARY KEY (username)
);

CREATE TABLE search_history (
	content varchar(200) NOT NULL,
	username varchar(100) NOT NULL,
	content_date DATE NOT NULL
);

ALTER TABLE search_history ADD CONSTRAINT search_history_fk0 FOREIGN KEY (username) REFERENCES users(username);
