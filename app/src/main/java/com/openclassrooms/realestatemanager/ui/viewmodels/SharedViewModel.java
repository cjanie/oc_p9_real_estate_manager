package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.Action;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Action> menuAction;

    private MutableLiveData<Estate> estateSelection;

    public SharedViewModel() {
        this.menuAction = new MutableLiveData<>();
        this.menuAction.setValue(Action.HOME);

        this.estateSelection = new MutableLiveData<>();
    }

    public LiveData<Action> getAction() {
        return this.menuAction;
    }

    public void updateAction(Action action) {
        this.menuAction.setValue(action);
    }

    public LiveData<Estate> getEstateSelection() {
        return this.estateSelection;
    }

    public void updateEstateSelection(Estate estate) {
        this.estateSelection.setValue(estate);
    }

    public void initEstateSelection() {
        this.estateSelection = new MutableLiveData<>();
    }
}
