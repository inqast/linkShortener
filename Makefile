build:
	cd link && mvn clean package

build-cli:
	cd cli && go build -o bin/cli main.go

migrate:
	cd link &&	mvn -Dflyway.configFiles=flyway.conf flyway:migrate

test:
	cd link && mvn clean test

up:
	docker compose up -d --build

down:
	docker compose down