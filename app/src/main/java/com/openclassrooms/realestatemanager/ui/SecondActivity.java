package com.openclassrooms.realestatemanager.ui;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.ui.fragments.EstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.FormAddEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.FormUpdateEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.SearchResultsFragment;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

public class SecondActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;

    private EstatesFragment estatesFragment;
    private SearchResultsFragment searchResultsFragment;
    private FormAddEstateFragment formAddEstateFragment;
    private FormUpdateEstateFragment formUpdateEstateFragment;
    private EstateDetailsFragment estateDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        this.sharedViewModel.getMenuAction().observe(this, action -> {
            if(action.equals(MenuAction.SEARCH)) {
                if(this.searchResultsFragment == null) {
                    this.searchResultsFragment = new SearchResultsFragment();
                }
                this.showFragment(this.searchResultsFragment);
            }
            if(action.equals(MenuAction.HOME)) {
                if(this.estatesFragment == null) {
                    this.estatesFragment = new EstatesFragment();
                }
                this.showFragment(estatesFragment);
            }
            if(action.equals(MenuAction.ADD)) {
                if(this.formAddEstateFragment == null) {
                    this.formAddEstateFragment = new FormAddEstateFragment();
                }
                this.showFragment(formAddEstateFragment);
            }
            if(action.equals(MenuAction.EDIT)) {
                if(this.formUpdateEstateFragment == null) {
                    this.formUpdateEstateFragment = new FormUpdateEstateFragment();
                }
                this.showFragment(formUpdateEstateFragment);
            }
        });

        this.sharedViewModel.getEstateSelection().observe(this, estateSelection -> {
            FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
            if(this.estateDetailsFragment == null) {
                this.estateDetailsFragment = new EstateDetailsFragment();
            }
            transaction.replace(R.id.frame_layout_details, this.estateDetailsFragment);
            transaction.commit();
        });
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                this.onAddCalled();
                return true;
            case R.id.action_edit:
                this.onEditCalled();
                return true;
            case R.id.action_search:
                this.onSearchCalled();
                return true;
            case R.id.action_home:
                this.onHomeCalled();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void onAddCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.ADD);
        this.sharedViewModel.initEstateSelection();

    }

    private void onEditCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.EDIT);

    }

    private void onSearchCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.SEARCH);
    }

    private void onHomeCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.HOME);
    }



}
