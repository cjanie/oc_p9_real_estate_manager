package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.ui.fragments.EstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.FormAddEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.FormUpdateEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.SearchResultsFragment;
import com.openclassrooms.realestatemanager.ui.viewmodels.NavigationActivity;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

public class SecondActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void showDetails() {
        this.showFragment(new EstateDetailsFragment());

        // TO DO considering screen width
        // R.id.frame_layout or R.id.frame_layout_details
        /*
            FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout_details, new EstateDetailsFragment());
            transaction.commit();
             */
    }

}
