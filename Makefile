build:
	cd link && mvn package

migrate:
	cd link &&	mvn -Dflyway.configFiles=flyway.conf flyway:migrate

test:
	cd link && mvn clean test

up:
	docker compose up -d --build

down:
	docker compose down