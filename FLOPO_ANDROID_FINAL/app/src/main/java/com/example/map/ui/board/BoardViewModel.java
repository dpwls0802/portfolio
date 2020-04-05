package com.example.map.ui.board;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BoardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BoardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is board fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}