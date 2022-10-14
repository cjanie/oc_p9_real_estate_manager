package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.ListEstatesRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.viewmodels.EstatesViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;

public class EstatesFragment extends BaseFragment {

    private EstatesViewModel estatesViewModel;

    private SharedViewModel sharedViewModel;

    private RecyclerView recyclerView;

    private ListEstatesRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        Context context = root.getContext();
        this.recyclerView = (RecyclerView) root;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);
        this.adapter = new ListEstatesRecyclerViewAdapter(this.sharedViewModel);
        this.recyclerView.setAdapter(adapter);

        EstatesViewModelFactory viewModelFactory = ((Launch)this.getActivity().getApplication()).estatesViewModelFactory();
        this.estatesViewModel = new ViewModelProvider(this, viewModelFactory).get(EstatesViewModel.class);



        this.estatesViewModel.getEstates().observe(this.getViewLifecycleOwner(),
                estates -> this.adapter.updateList(estates)
                );
        this.estatesViewModel.fetchEstatesToUpdateLiveData();
        return root;
    }
}
