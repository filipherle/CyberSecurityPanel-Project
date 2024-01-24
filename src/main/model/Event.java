package model;

import java.util.Calendar;
import java.util.Date;

// Represents an Event object with a date and description of what happened
// As specified in Phase 4: Task 2, this class was taken from the AlarmSystem project:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // MODIFIES: this
    // EFFECTS: Creates an event with the given description
    //          and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: returns the date of this event
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of this event
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns true if the object compared to this event is equal based on their
    //          class type, dateLogged, and description. returns false otherwise.
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: returns the integer of the hashcode of this event
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns a String of the date logged and description of this event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

