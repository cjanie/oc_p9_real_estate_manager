package com.openclassrooms.realestatemanager.ui.viewmodels;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.Action;
import com.openclassrooms.realestatemanager.ui.fragments.EstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.FormAddEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.FormUpdateEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.SearchResultsFragment;

public abstract class NavigationActivity extends BaseActivity {

    private final int LAYOUT_ID = R.layout.activity_second;

    protected SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.LAYOUT_ID);

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Observe menu action state
        this.sharedViewModel.getAction().observe(this, action -> {

            if(action.equals(Action.SEARCH)) {
                this.showFragment(new SearchResultsFragment());
            }
            if(action.equals(Action.HOME)) {
                this.showFragment(new EstatesFragment());
            }
            if(action.equals(Action.ADD)) {
                this.showFragment(new FormAddEstateFragment());
            }
            if(action.equals(Action.EDIT)) {
                this.showFragment(new FormUpdateEstateFragment());
            }
            if(action.equals(Action.DETAILS)) {
                this.showDetails();
            }

            this.invalidateMenu(); // Launches creation of the menu
        });

    }

    protected void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        // if transaction.replace is used: onBackPressed considers the activity;
        // if transaction.add is uses: onBackPressed considers the fragments
        // First fragment should not be added to back stack
        if(this.getSupportFragmentManager().getFragments().isEmpty()) {
            transaction.add(R.id.frame_layout, fragment);
        } else {
            transaction.add(R.id.frame_layout, fragment).addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }

    protected abstract void showDetails();

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        this.createDynamicMenu(menu);
        return true;
    }

    private void createDynamicMenu(Menu menu) {
        if(!this.sharedViewModel.getAction().getValue().equals(Action.DETAILS)) {
            MenuItem item = menu.findItem(R.id.action_edit);
            item.setVisible(false);
        }
        if(this.sharedViewModel.getAction().getValue().equals(Action.EDIT)) {
            MenuItem item = menu.findItem(R.id.action_edit);
            item.setVisible(false);
        }
        if(this.sharedViewModel.getAction().getValue().equals(Action.ADD)) {
            MenuItem item = menu.findItem(R.id.action_add);
            item.setVisible(false);
        }
        if(this.sharedViewModel.getAction().getValue().equals(Action.SEARCH)) {
            MenuItem item = menu.findItem(R.id.action_search);
            item.setVisible(false);
        }
        if(this.sharedViewModel.getAction().getValue().equals(Action.HOME)) {
            MenuItem item = menu.findItem(R.id.action_home);
            item.setVisible(false);
        }
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
        this.sharedViewModel.updateAction(Action.ADD);
        this.sharedViewModel.initEstateSelection();
    }

    private void onEditCalled() {
        this.sharedViewModel.updateAction(Action.EDIT);
    }

    private void onSearchCalled() {
        this.sharedViewModel.updateAction(Action.SEARCH);
    }

    private void onHomeCalled() {
        this.sharedViewModel.updateAction(Action.HOME);
    }

}
