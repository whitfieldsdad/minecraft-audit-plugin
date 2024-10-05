default: clean install

help:
	@echo make install - Install the project
	@echo make clean - Remove any build artifacts
	@echo make container - Build the container
	@echo make up - Start the development environment
	@echo make down - Shutdown the development environment
	@echo make nuke - Destroy the development environment (e.g., to start from scratch with a new Minecraft world)
	@echo make restart - Restart the development environment
	@echo make login - Login to the container

clean:
	mvn clean

install:
	mvn install

container: install
	docker-compose build --no-cache

up: container
	docker-compose up --force-recreate

down:
	docker-compose down --remove-orphans

nuke:
	docker-compose down --remove-orphans --rmi all --volumes

restart: down up

login:
	docker exec -it minecraft-dev bash
