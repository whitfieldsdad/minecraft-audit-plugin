# Yet another audit plugin for Minecraft servers

## Features

- All data is stored in JSON or Parquet files.
- All doubles are floored to integers (i.e. speeds and (x, y, z) coordinates are rounded down).
- Everything has a UUID and timestamp associated with it (i.e. worlds, entities, blocks, etc.).
- All events are representable as quads (i.e. world, subject, predicate, object).

| Object type | Example path                                                              |
|-------------|---------------------------------------------------------------------------|
| World       | `worlds/<world_uuid>.json`                                                |
| Entity      | `worlds/<world_uuid>/entities/<entity_uuid>.json`                         |
| Block       | `worlds/<world_uuid>/blocks/<block_uuid>.json`                            |
| Events      | `worlds/<world_uuid>/events/<subject_uuid>/<predicate>/<event_uuid>.json` |

| Subject | Predicate | Object | Example path                                                        |
|---------|-----------|--------|---------------------------------------------------------------------|
| Player  | placed    | Block  | `worlds/<world_uuid>/events/<entity_uuid>/placed/<block_uuid>.json` |
| Player  | mined     | Block  | `worlds/<world_uuid>/events/<entity_uuid>/mined/<block_uuid>.json`  |

> All dates and timestamps are in ISO 8601 format.
>
> Parquet is recommended for use in production to reduce storage space, improve read/write performance, and improve query performance.

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
