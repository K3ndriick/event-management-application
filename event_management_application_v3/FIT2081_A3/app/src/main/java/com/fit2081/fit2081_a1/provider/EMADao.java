package com.fit2081.fit2081_a1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EMADao {

    // Category query methods
    @Query("select * from categories")
    LiveData<List<CategoryClass>> getAllCategories();

    @Query("select * from categories where categoryId=:categoryId")
    LiveData<List<CategoryClass>> getCategoryId(String categoryId);

    @Query("select * from categories where categoryName=:categoryName")
    List<CategoryClass> getCategoryName(String categoryName);

    @Query("select * from categories where eventLocation=:eventLocation")
    List<CategoryClass> getEventLocation(String eventLocation);

    @Insert
    void addCategory(CategoryClass categoryObject);

    @Query("update categories set categoryEventCount = categoryEventCount + 1 where categoryId=:categoryId")
    void increaseCategoryEventCount(String categoryId);

    @Query("update categories set categoryEventCount = categoryEventCount - 1 where categoryId=:categoryId")
    void decreaseCategoryEventCount(String categoryId);

    @Query("delete from categories")
    void deleteAllCategories();

    @Query("delete from categories where categoryId=:categoryId")
    void deleteCategory(String categoryId);




    // Event query methods
    @Query("select * from events")
    LiveData<List<EventClass>> getAllEvents();

    @Query("select * from events where eventName=:eventName")
    List<EventClass> getEventName(String eventName);

    @Query("select * from events where eventId=:eventId")
    LiveData<List<EventClass>> getEventId (String eventId);

    @Query("select * from events where eventCategoryID=:eventCategoryId")
    List<EventClass> getEventCategoryId(String eventCategoryId);

    @Insert
    void addEvent(EventClass eventObject);

    @Query("delete from events")
    void deleteAllEvents();

    @Query("delete from events where eventId=:eventId")
    void deleteEvent(String eventId);
}
