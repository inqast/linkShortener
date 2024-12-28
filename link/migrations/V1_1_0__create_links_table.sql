CREATE TABLE links (
    hash CHAR(6) PRIMARY KEY,
    link VARCHAR(256),
    owner UUID,
    usages_count INT,
    usages_limit INT,
    deadline DATE
);

CREATE INDEX links_owners ON links (owner); 
