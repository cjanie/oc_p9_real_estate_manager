package com.openclassrooms.realestatemanager.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.EstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.SearchResultsFragment;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

public class SecondActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEditor;

    private EstatesFragment estatesFragment;
    private SearchResultsFragment searchResultsFragment;
    private FormFragment formFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferenceEditor = this.sharedPreferences.edit();

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
                if(this.formFragment == null) {
                    this.formFragment = new FormFragment();
                }
                this.showFragment(formFragment);
            }
            if(action.equals(MenuAction.EDIT)) {
                if(this.formFragment == null) {
                    this.formFragment = new FormFragment();
                }
                this.showFragment(formFragment);
            }
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
        this.sharedPreferenceEditor.putString(SharedPreferencesConfig.MODE, MenuAction.ADD.toString());
        this.sharedPreferenceEditor.commit();

    }

    private void onEditCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.EDIT);
        this.sharedPreferenceEditor.putString(SharedPreferencesConfig.MODE, MenuAction.EDIT.toString());
        this.sharedPreferenceEditor.commit();

    }

    private void onSearchCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.SEARCH);
    }

    private void onHomeCalled() {
        this.sharedViewModel.updateMenuAction(MenuAction.HOME);
    }



}
