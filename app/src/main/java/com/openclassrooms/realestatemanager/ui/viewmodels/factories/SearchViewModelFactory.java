package com.openclassrooms.realestatemanager.ui.viewmodels.factories;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.businesslogic.usecases.SearchEstatesUseCase;
import com.openclassrooms.realestatemanager.ui.viewmodels.SearchViewModel;

import org.jetbrains.annotations.NotNull;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    private SearchEstatesUseCase searchEstatesUseCase;

    public SearchViewModelFactory(SearchEstatesUseCase searchEstatesUseCase) {
        this.searchEstatesUseCase = searchEstatesUseCase;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(this.searchEstatesUseCase);
        }
        throw new IllegalArgumentException("Search view model factory");
    }
}
