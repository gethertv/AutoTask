package dev.gether.autotask.data;

import java.time.LocalTime;
import java.util.List;

public class EventData {

    private String id;
    private LocalTime eventTimes;
    private List<String> commands;

    public EventData(String id, LocalTime eventTimes, List<String> commands) {
        this.id =id;
        this.eventTimes = eventTimes;
        this.commands = commands;
    }

    public String getId() {
        return id;
    }

    public LocalTime getEventTimes() {
        return eventTimes;
    }

    public void setEventTimes(LocalTime eventTimes) {
        this.eventTimes = eventTimes;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
