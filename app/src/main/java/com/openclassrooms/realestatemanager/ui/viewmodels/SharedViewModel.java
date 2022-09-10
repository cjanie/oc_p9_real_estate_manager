package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.MenuAction;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<MenuAction> menuAction;

    private MutableLiveData<Estate> estateSelection;

    public SharedViewModel() {
        this.menuAction = new MutableLiveData<>();
        this.menuAction.setValue(MenuAction.HOME);

        this.estateSelection = new MutableLiveData<>();
    }

    public LiveData<MenuAction> getMenuAction() {
        return this.menuAction;
    }

    public void updateMenuAction(MenuAction menuAction) {
        this.menuAction.setValue(menuAction);
    }

    public LiveData<Estate> getEstateSelection() {
        return this.estateSelection;
    }

    public void updateEstateSelection(Estate estate) {
        this.estateSelection.setValue(estate);
    }
}
