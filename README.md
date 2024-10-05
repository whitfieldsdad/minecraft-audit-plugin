# Yet another audit plugin for Minecraft servers

An audit plugin for Minecraft servers that attempts to log as much about player and entity behaviours as possible.

## Design

- The plugin runs on a Minecraft server<sub>1</sub> and writes events to a file<sub>2</sub>.

<sub>1. The plugin is tested against a [PaperMC](https://github.com/PaperMC/Paper) server during development.</sub>
<sub>2. The file is in JSON Lines format (i.e. each line of the file is a JSON object) and is rotated daily.</sub>

## Features

| Subject | Verb | Object | Status |
| --- | --- | --- | --- |
| player | broke | block | ✅ |
| player | placed | block | ✅ |
