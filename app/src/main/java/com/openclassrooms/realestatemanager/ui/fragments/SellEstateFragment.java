package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.enums.Action;
import com.openclassrooms.realestatemanager.ui.fragments.BaseFragment;
import com.openclassrooms.realestatemanager.ui.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.ui.viewmodels.SellViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.SellViewModelFactory;

public class SellEstateFragment extends EstateDetailsFragment {

    private SellViewModel sellViewModel;

    private LinearLayout layout;
    private Button sell;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SellViewModelFactory sellViewModelFactory = ((Launch) this.getActivity().getApplication()).sellViewModelFactory();
        this.sellViewModel = new ViewModelProvider(this, sellViewModelFactory).get(SellViewModel.class);

        View root = super.onCreateView(inflater, container, savedInstanceState);
        this.layout = root.findViewById(R.id.details_root_layout);
        this.sell = new Button(this.getContext());
        this.sell.setText(R.string.sell);
        this.layout.addView(this.sell);

        this.sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estate estate = detailsViewModel.getEstate().getValue();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SellEstateFragment.this.getContext());
                String agentName = sharedPreferences.getString(SellEstateFragment.this.getString(R.string.user_name_preference_key), "");
                sellViewModel.sell(estate, agentName);
                sharedViewModel.updateAction(Action.HOME);
            }
        });
        return root;
    }
}
