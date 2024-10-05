# Development guide

> ["Developers, developers, developers, developers, developers, developers, developers, developers, developers, developers, developers"](https://www.youtube.com/watch?v=hMLcKtVwF-A) - Steve Ballmer

## Usage

The development environment is managed using `docker compose` and a Makefile is provided to simplify the process of managing the development environment (i.e., there's no need to be familiar with `docker`, `docker compose`, etc.).

```sh
make help
```

```
"make install - Install the project"
"make clean - Remove any build artifacts"
"make container - Build the container"
"make up - Start the development environment"
"make down - Shutdown the development environment"
"make nuke - Destroy the development environment (e.g., to start over with a new Minecraft world during development)"
"make restart - Restart the development environment"
"make login - Login to the container"
```