package com.fit2081.fit2081_a1.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryClass {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;

    @ColumnInfo(name = "categoryId")
    @NonNull
    private String categoryId;

    @ColumnInfo(name = "categoryName")
    @NonNull
    private String categoryName;

    @ColumnInfo(name = "categoryEventCount")
    private int categoryEventCount;

    @ColumnInfo(name = "categoryAvailability")
    private boolean categoryAvailability;

    @ColumnInfo(name = "eventLocation")
    private String eventLocation;

    // Constructor
    public CategoryClass(String categoryId, String categoryName, int categoryEventCount, boolean categoryAvailability, String eventLocation) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryEventCount = categoryEventCount;
        this.categoryAvailability = categoryAvailability;
        this.eventLocation = eventLocation;
    }


    // Getter methods
    public int getId() {
        return id;
    }
    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryEventCount() {
        return categoryEventCount;
    }

    public boolean getCategoryAvailability() {
        return categoryAvailability;
    }

    public String getEventLocation() {
        return eventLocation;
    }


    // Setter methods

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryEventCount(int categoryEventCount) {
        this.categoryEventCount = categoryEventCount;
    }

    public void setCategoryAvailability(boolean categoryAvailability) {
        this.categoryAvailability = categoryAvailability;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
