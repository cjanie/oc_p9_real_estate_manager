package com.openclassrooms.realestatemanager.ui;

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
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;

public class EstatesFragment extends Fragment {

    private EstatesViewModel viewModel;

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

        this.adapter = new ListEstatesRecyclerViewAdapter();
        this.recyclerView.setAdapter(adapter);

        EstatesViewModelFactory viewModelFactory = ((Launch)this.getActivity().getApplication()).estatesViewModelFactory();
        this.viewModel = new ViewModelProvider(this, viewModelFactory).get(EstatesViewModel.class);

        this.adapter.updateList(viewModel.getEstates());

        return root;
    }
}
