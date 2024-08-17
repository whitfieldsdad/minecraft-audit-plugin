package ca.tylerfisher.minecraft.auditor.tracing;

public class TraceObjectEnvelope {
    public TraceObject data;
    public String type;

    public TraceObjectEnvelope(TraceObject traceObject) {
        this.data = traceObject;
        this.type = traceObject.getType();
    }
}
