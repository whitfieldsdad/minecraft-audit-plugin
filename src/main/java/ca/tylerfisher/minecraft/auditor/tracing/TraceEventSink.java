package ca.tylerfisher.minecraft.auditor.tracing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.tylerfisher.minecraft.auditor.io.InstantTypeAdapter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;

public class TraceEventSink {

    private final String path;

    public TraceEventSink(String path) {
        this.path = Paths.get(path).toAbsolutePath().toString();
    }

    public void writeEvents(Object[] objects) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            for (Object object : objects) {
                writer.write(gson.toJson(object));
                writer.newLine();
            }
        }
    }
}