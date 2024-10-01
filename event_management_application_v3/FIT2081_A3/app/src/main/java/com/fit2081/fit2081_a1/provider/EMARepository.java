package com.fit2081.fit2081_a1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EMARepository {

    private EMADao mEmaDao;
    private LiveData<List<CategoryClass>> mAllCategories;
    private LiveData<List<EventClass>> mAllEvents;

    EMARepository(Application application) {
        EMADatabase emaDB = EMADatabase.getDatabase(application);
        mEmaDao = emaDB.emaDao();
        mAllCategories = mEmaDao.getAllCategories();
        mAllEvents = mEmaDao.getAllEvents();
    }


    // Category methods
    LiveData<List<CategoryClass>> getAllCategories() {
        return mAllCategories;
    }

    LiveData<List<CategoryClass>> getCategoryId(String categoryId) {
        return mEmaDao.getCategoryId(categoryId);
    }

    List<CategoryClass> getCategoryName(String categoryName) {
        return mEmaDao.getCategoryName(categoryName);
    }

    List<CategoryClass> getEventLocation(String eventLocation) {
        return mEmaDao.getEventLocation(eventLocation);
    }

    void insertCategory(CategoryClass categoryObject) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.addCategory(categoryObject));
    }

    void increaseCategoryEventCount(String categoryId) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.increaseCategoryEventCount(categoryId));
    }

    void decreaseCategoryEventCount(String categoryId) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.decreaseCategoryEventCount(categoryId));
    }

    void deleteAllCategories() {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.deleteAllCategories());
    }



    // Event methods
    LiveData<List<EventClass>> getAllEvents() {
        return mAllEvents;
    }

    List<EventClass> getEventName(String eventName) {
        return mEmaDao.getEventName(eventName);
    }

    LiveData<List<EventClass>> getEventId (String eventId) {
        return mEmaDao.getEventId(eventId);
    }
    List<EventClass> getEventCategoryId(String eventCategoryId) {
        return mEmaDao.getEventCategoryId(eventCategoryId);
    }

    void insertEvent(EventClass eventObject) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.addEvent(eventObject));
    }

    void deleteAllEvents() {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.deleteAllEvents());
    }

    void deleteEvent(String eventId) {
        EMADatabase.databaseWriteExecutor.execute(() -> mEmaDao.deleteEvent(eventId));
    }
}
