package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a class that logs Events
// As specified in Phase 4: Task 2, this class was taken from the AlarmSystem project:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // MODIFIES: this
    // EFFECTS: creates an event log
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: returns the instance of this EventLog, or creates it if it doesn't exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns an iterator of the collection of events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
