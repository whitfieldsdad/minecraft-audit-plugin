# Yet another audit plugin for Minecraft servers

> This plugin is a work in progress and is not ready for public use.

## Usage

### Docker Compose

A [docker-compose.yml](docker-compose.yml) is provided for convenience. It will start a Minecraft server with the plugin installed.

To start the server, run:

```bash
make up
```

To stop the server, hit CTRL + C in the same terminal or run the following command to teardown the Docker Compose stack:

```bash
make down
```

To view information about the running container:

```bash
docker ps
```

```text
CONTAINER ID   IMAGE                   COMMAND    CREATED              STATUS                        PORTS                      NAMES
bedc43fc81c6   itzg/minecraft-server   "/start"   About a minute ago   Up About a minute (healthy)   0.0.0.0:25565->25565/tcp   minecraft-dev
```

To login to the container:

```bash
make login
```

Or:

```bash
docker exec -it minecraft-dev bash
```
