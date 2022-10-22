package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.Action;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Action> menuAction;

    private MutableLiveData<Integer> estateSelectionId;

    public SharedViewModel() {
        this.menuAction = new MutableLiveData<>();
        this.menuAction.setValue(Action.HOME);

        this.estateSelectionId = new MutableLiveData<>();
    }

    public LiveData<Action> getAction() {
        return this.menuAction;
    }

    public void updateAction(Action action) {
        this.menuAction.setValue(action);
    }

    public LiveData<Integer> getEstateSelectionId() {
        return this.estateSelectionId;
    }

    public void updateEstateSelection(Integer estateId) {
        this.estateSelectionId.setValue(estateId);
    }

    public void initEstateSelection() {
        this.estateSelectionId = new MutableLiveData<>();
    }
}
