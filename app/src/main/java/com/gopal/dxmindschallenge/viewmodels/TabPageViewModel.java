package com.gopal.dxmindschallenge.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class TabPageViewModel extends ViewModel {

    private MutableLiveData<String> sourceId = new MutableLiveData<>();

    private LiveData<String> sourceData = Transformations.map(sourceId, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return "Source: " + input;
        }
    });

    public void setId(String id) {
        sourceId.setValue(id);
    }

    public LiveData<String> getId() {
        return sourceData;
    }

}