package com.openclassrooms.realestatemanager.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SearchEstatesUseCase;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private SearchEstatesUseCase searchEstatesUseCase;

    private MutableLiveData<List<Estate>> searchResults;

    public SearchViewModel(SearchEstatesUseCase searchEstatesUseCase) {
        this.searchEstatesUseCase = searchEstatesUseCase;
        this.searchResults = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Estate>> getSearchResults() {
        return this.searchResults;
    }

    public void fetchSearchResultsToUpdateLiveData(EstateType type) {
        this.searchResults.postValue(this.searchEstatesUseCase.handle(type));
    }
}
