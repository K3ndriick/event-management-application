package com.fit2081.fit2081_a1.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class EventClass {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;

    @ColumnInfo(name = "eventId")
    @NonNull
    private String eventId;

    @ColumnInfo(name = "eventName")
    @NonNull
    private String eventName;

    @ColumnInfo(name = "eventCategoryID")
    @NonNull
    private String eventCategoryId;

    @ColumnInfo(name = "eventTicketsAvailable")
    private int eventTicketsAvailable;

    @ColumnInfo(name = "eventAvailability")
    private boolean eventAvailability;

    // Constructor
    public EventClass(String eventId, String eventName, String eventCategoryId, int eventTicketsAvailable, boolean eventAvailability) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventCategoryId = eventCategoryId;
        this.eventTicketsAvailable = eventTicketsAvailable;
        this.eventAvailability = eventAvailability;
    }


    // Getter methods
    public int getId() {
        return id;
    }
    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventCategoryId() {
        return eventCategoryId;
    }

    public int getEventTicketsAvailable() {
        return eventTicketsAvailable;
    }

    public boolean getEventAvailability() {
        return eventAvailability;
    }



    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setEventId(@NonNull String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(@NonNull String eventName) {
        this.eventName = eventName;
    }

    public void setEventCategoryId(@NonNull String eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public void setEventTicketsAvailable(int eventTicketsAvailable) {
        this.eventTicketsAvailable = eventTicketsAvailable;
    }

    public void setEventAvailability(boolean eventAvailability) {
        this.eventAvailability = eventAvailability;
    }
}
