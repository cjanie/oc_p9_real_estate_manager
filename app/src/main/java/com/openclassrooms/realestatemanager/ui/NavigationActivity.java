package com.openclassrooms.realestatemanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.ListEstatesRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.enums.Action;
import com.openclassrooms.realestatemanager.ui.enums.ActionVisitor;
import com.openclassrooms.realestatemanager.ui.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.ui.fragments.EstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.MapEstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormAddEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormUpdateEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.SearchFragment;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends MobileAndTabletActivity {

    protected SharedViewModel sharedViewModel;

    private Map<Integer, Action> menuItemIdToActionMap;

    public NavigationActivity() {
        this.menuItemIdToActionMap = new HashMap<>();
        menuItemIdToActionMap.put(R.id.action_home, Action.HOME);
        menuItemIdToActionMap.put(R.id.action_add, Action.ADD);
        menuItemIdToActionMap.put(R.id.action_edit, Action.EDIT);
        menuItemIdToActionMap.put(R.id.action_search, Action.SEARCH);
        menuItemIdToActionMap.put(R.id.action_map, Action.MAP);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Observe menu action state
        this.sharedViewModel.getAction().observe(this, action -> {
            this.showFragmentForAction(action);
            this.invalidateMenu(); // Launches creation of the menu
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        this.createDynamicMenu(menu);
        return true;
    }

    private void createDynamicMenu(Menu menu) {
        Action action = this.sharedViewModel.getAction().getValue();
        this.handleMenuItemVisibilityWhenAction(action, menu);
    }

    private Integer handleMenuItemVisibilityWhenAction(Action action, Menu menu) {

        this.setMenuItemVisible(menu, R.id.action_edit, false);
        return action.accept(new ActionVisitor<Integer>() {

            @Override
            public Integer visitHome() {
                setMenuItemVisible(menu, R.id.action_home,false);
                return R.id.action_home;
            }

            @Override
            public Integer visitAdd() {
                setMenuItemVisible(menu, R.id.action_add, false);
                return R.id.action_add;
            }

            @Override
            public Integer visitEdit() {
                setMenuItemVisible(menu, R.id.action_edit,false);
                return R.id.action_edit;
            }

            @Override
            public Integer visitSearch() {
                setMenuItemVisible(menu, R.id.action_search, false);
                return R.id.action_search;
            }

            @Override
            public Integer visitDetails() {
                setMenuItemVisible(menu, R.id.action_edit, true); // So
                return -1;
            }

            @Override
            public Integer visitMap() {
                setMenuItemVisible(menu, R.id.action_map,false);
                return R.id.action_map;
            }
        });
    }

    private void setMenuItemVisible(Menu menu, int menuItemId, boolean visible) {
        menu.findItem(menuItemId).setVisible(visible);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);

        } else {
            Action action = this.menuItemIdToActionMap.get(item.getItemId());
            this.onActionCalled(action);
        }
        return true;
    }

    private boolean onActionCalled(Action action) {
        return action.accept(new ActionVisitor<Boolean>() {

            @Override
            public Boolean visitHome() {
                onHomeCalled();
                return true;
            }

            @Override
            public Boolean visitAdd() {
                onAddCalled();
                return true;
            }

            @Override
            public Boolean visitEdit() {
                onEditCalled();
                return true;
            }

            @Override
            public Boolean visitSearch() {
                onSearchCalled();
                return true;
            }

            @Override
            public Boolean visitDetails() {
                onDetailsCalled();
                return false;
            }

            @Override
            public Boolean visitMap() {
                onMapCalled();
                return true;
            }
        });
    }

    private void onHomeCalled() {
        this.sharedViewModel.updateAction(Action.HOME);
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

    private void onMapCalled() {
        this.sharedViewModel.updateAction(Action.MAP);
    }

    @Override
    public void setEstateSelectionId(int estateId) {
        this.sharedViewModel.updateEstateSelection(estateId);
    }

    @Override
    public void onDetailsCalled() {
        this.sharedViewModel.updateAction(Action.DETAILS);
    }

    @Override
    public LiveData<Integer> getEstateSelectionId() {
        return this.sharedViewModel.getEstateSelectionId();
    }

}
