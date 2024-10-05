package ca.tylerfisher.minecraft.auditor.tracing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TraceEvent {
    public String id;
    public Instant time;
    public TraceObjectEnvelope subject;
    public String verb;
    public TraceObjectEnvelope object;
    public List<TraceObjectEnvelope> relatedObjects;

    public TraceEvent(TraceObject subject, String verb, TraceObject object) {
        this.id = java.util.UUID.randomUUID().toString();
        this.time = Instant.now();
        this.subject = new TraceObjectEnvelope(subject);
        this.verb = verb;
        this.object = new TraceObjectEnvelope(object);
        this.relatedObjects = new ArrayList<>();
    }
}
