FROM itzg/minecraft-server:latest

ENV TYPE=PAPER

COPY target/auditor-0.0.1-SNAPSHOT.jar /plugins/
