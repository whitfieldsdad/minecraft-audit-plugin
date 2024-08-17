package ca.tylerfisher.minecraft.auditor.tracing;

import java.time.Instant;
import java.util.Map;

public class TraceEvent {
    public String id;
    public Instant time;
    public TraceObjectEnvelope subject;
    public String verb;
    public TraceObjectEnvelope object;
    public Map<String, TraceObjectEnvelope> prepositionalObjects;

    public TraceEvent(TraceObject subject, String verb, TraceObject object, WorldMetadata world, LocationMetadata location) {
        this.id = java.util.UUID.randomUUID().toString();
        this.time = Instant.now();
        this.subject = new TraceObjectEnvelope(subject);
        this.verb = verb;
        this.object = new TraceObjectEnvelope(object);
        this.prepositionalObjects = Map.of(
            "in", new TraceObjectEnvelope(world),
            "at", new TraceObjectEnvelope(location)
        );
    }
}