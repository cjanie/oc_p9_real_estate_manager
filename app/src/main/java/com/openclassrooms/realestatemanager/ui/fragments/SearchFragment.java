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
import com.openclassrooms.realestatemanager.businesslogic.MapSearchEstatesParamsConfig;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.enums.SearchParameter;
import com.openclassrooms.realestatemanager.ui.adapters.ListEstatesRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.utils.ShowFragmentUtil;
import com.openclassrooms.realestatemanager.ui.viewmodels.SearchViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.SearchViewModelFactory;

import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends BaseFragment implements
        SearchParametersFragment.HandleSearchParameters,
        SearchParametersFragment.HandleResetParameters,
        SearchParametersFragment.HandleSearchRequest {

    private SearchViewModel searchViewModel;

    private SharedViewModel sharedViewModel;

    private EstateType typeParam;

    private String locationParam;

    private Float maxPriceParam;

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
                new SearchParametersFragment(this, this, this));

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
    public void setParamMaxPrice(Float maxPrice) {
        this.maxPriceParam = maxPrice;
    }


    @Override
    public void search() {
        Map<SearchParameter, Object> params = new HashMap<>();

        if(this.typeParam != null) {
            params.put(SearchParameter.TYPE, this.typeParam);
        }
        if(this.locationParam != null) {
            params.put(SearchParameter.LOCATION, this.locationParam);
        }
        if(this.maxPriceParam != null) {
            params.put(SearchParameter.MAX_PRICE_IN_DOLLARS, this.maxPriceParam);
        }

        this.searchViewModel.fetchSearchResultsToUpdateLiveData(params);
    }

    @Override
    public void reset() {
        this.typeParam = null;
        this.locationParam = null;
        this.maxPriceParam = null;
    }
}
