# Yet another audit plugin for Minecraft servers

> This plugin is a work in progress and is not ready for public use.

## Features

- Detect when a player breaks or places a block

## Examples

### Events

- `player.broke.block`: a player broke a block
- `player.placed.block`: a player placed a block

Example `player.broke.block` event:

```json
{
    "id": "f2ee70a3-c0ca-43cb-ac3d-e0a5c34ecccb",
    "time": "2024-08-17T06:25:11.416021567Z",
    "subject": {
        "data": {
            "id": "0250f7e1-68c2-41f9-8b7b-154759b769e1",
            "name": "whitfieldsdad"
        },
        "type": "player"
    },
    "verb": "broke",
    "object": {
        "data": {
            "x": -2,
            "y": 110,
            "z": -81,
            "materialType": "DIRT"
        },
        "type": "block"
    },
    "prepositionalObjects": {
        "at": {
            "data": {
                "x": -1,
                "y": 110,
                "z": -82
            },
            "type": "location"
        },
        "in": {
            "data": {
                "id": "ba4aa330-f661-454a-9813-9fbc224f4ea6",
                "name": "world"
            },
            "type": "world"
        }
    }
}
```

Example `player.placed.block` event:

```json
{
    "id": "7136f5aa-6013-4127-aaaa-447859465940",
    "time": "2024-08-17T06:25:10.071691835Z",
    "subject": {
        "data": {
            "id": "0250f7e1-68c2-41f9-8b7b-154759b769e1",
            "name": "whitfieldsdad"
        },
        "type": "player"
    },
    "verb": "placed",
    "object": {
        "data": {
            "x": -2,
            "y": 110,
            "z": -81,
            "materialType": "DIRT"
        },
        "type": "block"
    },
    "prepositionalObjects": {
        "at": {
            "data": {
                "x": -1,
                "y": 110,
                "z": -81
            },
            "type": "location"
        },
        "in": {
            "data": {
                "id": "ba4aa330-f661-454a-9813-9fbc224f4ea6",
                "name": "world"
            },
            "type": "world"
        }
    }
}
```

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
