CREATE TABLE links (
    hash int,
    link VARCHAR(256),
    owner UUID,
    usages_count INT,
    usages_limit INT,
    deadline DATE
);

CREATE INDEX links_hashes ON links (hash); 
CREATE INDEX links_owners ON links (owner); 
