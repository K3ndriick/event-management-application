package com.fit2081.fit2081_a1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EMAViewModel extends AndroidViewModel {
    private EMARepository mRepository;
    private LiveData<List<CategoryClass>> mAllCategories;
    private LiveData<List<EventClass>> mAllEvents;

    public EMAViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EMARepository(application);
        mAllCategories = mRepository.getAllCategories();
        mAllEvents = mRepository.getAllEvents();
    }


    // Category methods
    public LiveData<List<CategoryClass>> getAllCategories() {
        return mAllCategories;
    }

    public LiveData<List<CategoryClass>> getCategoryId(String categoryId) {
        return mRepository.getCategoryId(categoryId);
    }

    public List<CategoryClass> getCategoryName(String categoryName) {
        return mRepository.getCategoryName(categoryName);
    }

    public List<CategoryClass> getEventLocation(String eventLocation) {
        return mRepository.getEventLocation(eventLocation);
    }

    public void insertCategory(CategoryClass categoryObject) {
        mRepository.insertCategory(categoryObject);
    }

    public void increaseCategoryEventCount(String categoryId) {
        mRepository.increaseCategoryEventCount(categoryId);
    }

    public void decreaseCategoryEventCount(String categoryId) {
        mRepository.decreaseCategoryEventCount(categoryId);
    }

    public void deleteAllCategories() {
        mRepository.deleteAllCategories();
    }



    // Event methods
    public LiveData<List<EventClass>> getAllEvents() {
        return mAllEvents;
    }

    public List<EventClass> getEventName(String eventName) {
        return mRepository.getEventName(eventName);
    }

    public LiveData<List<EventClass>> getEventId(String eventId) {
        return mRepository.getEventId(eventId);
    }

    public List<EventClass> getEventCategoryId(String eventCategoryId) {
        return mRepository.getEventCategoryId(eventCategoryId);
    }

    public void insertEvent(EventClass eventObject) {
        mRepository.insertEvent(eventObject);
    }

    public void deleteAllEvents() {
        mRepository.deleteAllEvents();
    }

    public void deleteEvent(String eventId) {
        mRepository.deleteEvent(eventId);
    }
}
