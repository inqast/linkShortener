build:
	cd link && mvn package

test:
	cd link && mvn clean test

up:
	docker compose up -d