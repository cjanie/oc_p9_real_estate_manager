package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.ui.adapters.ListEstatesRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.utils.ShowFragmentUtil;
import com.openclassrooms.realestatemanager.ui.viewmodels.SearchViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.SearchViewModelFactory;

public class SearchFragment extends BaseFragment implements SearchParametersFragment.HandleSearchParameters, SearchParametersFragment.HandleSearchRequest {

    private SearchViewModel searchViewModel;

    private SharedViewModel sharedViewModel;

    private EstateType typeParam;

    private String locationParam;

    ShowFragmentUtil fragmentUtil;

    private ListEstatesRecyclerViewAdapter adapter;

    public SearchFragment() {
        this.fragmentUtil = new ShowFragmentUtil();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SearchViewModelFactory viewModelFactory = ((Launch)this.getActivity().getApplication()).searchViewModelFactory();
        this.searchViewModel = new ViewModelProvider(this, viewModelFactory).get(SearchViewModel.class);

        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        FragmentManager fragmentManager = this.getChildFragmentManager();

        this.fragmentUtil.showFragment(fragmentManager, R.id.frame_layout_search_parameters,
                new SearchParametersFragment(this, this));

        this.adapter = new ListEstatesRecyclerViewAdapter(sharedViewModel);
        this.fragmentUtil.showFragment(fragmentManager, R.id.frame_layout_search_results,
                new SearchResultsFragment(this.adapter));

        this.searchViewModel.getSearchResults().observe(this.getViewLifecycleOwner(), estates -> {
            this.adapter.updateList(estates);
            Log.d(this.getClass().getName(), "found estate list size : " + estates.size());
        });

        return root;
    }

    @Override
    public void setParamType(EstateType type) {
        this.typeParam = type;
    }

    @Override
    public void setParamLocation(String location) {
        this.locationParam = location;
    }


    @Override
    public void search() {
        this.searchViewModel.fetchSearchResultsToUpdateLiveData(typeParam);
        // TODO location param
    }
}
