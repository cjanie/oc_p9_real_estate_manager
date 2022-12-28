package com.openclassrooms.realestatemanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.ui.enums.Action;
import com.openclassrooms.realestatemanager.ui.enums.ActionVisitor;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends NavigationActivity {

    protected SharedViewModel sharedViewModel;

    private Map<Integer, Action> menuItemIdToActionMap;

    public MenuActivity() {
        this.menuItemIdToActionMap = new HashMap<>();
        menuItemIdToActionMap.put(R.id.menu_item_home, Action.HOME);
        menuItemIdToActionMap.put(R.id.menu_item_add, Action.ADD);
        menuItemIdToActionMap.put(R.id.menu_item_edit, Action.EDIT);
        menuItemIdToActionMap.put(R.id.menu_item_sell, Action.SELL);
        menuItemIdToActionMap.put(R.id.menu_item_search, Action.SEARCH);
        menuItemIdToActionMap.put(R.id.menu_item_map, Action.MAP);
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

        this.setMenuItemVisible(menu, R.id.menu_item_edit, false);
        this.setMenuItemVisible(menu, R.id.menu_item_sell, false);

        return action.accept(new ActionVisitor<Integer>() {

            @Override
            public Integer visitHome() {
                setMenuItemVisible(menu, R.id.menu_item_home,false);
                return R.id.menu_item_home;
            }

            @Override
            public Integer visitAdd() {
                setMenuItemVisible(menu, R.id.menu_item_add, false);
                return R.id.menu_item_add;
            }

            @Override
            public Integer visitEdit() {
                setMenuItemVisible(menu, R.id.menu_item_edit,false);
                return R.id.menu_item_edit;
            }

            @Override
            public Integer visitSell() {
                setMenuItemVisible(menu, R.id.menu_item_sell, false);
                return R.id.menu_item_sell;
            }

            @Override
            public Integer visitSearch() {
                setMenuItemVisible(menu, R.id.menu_item_search, false);
                return R.id.menu_item_search;
            }

            @Override
            public Integer visitDetails() {
                setMenuItemVisible(menu, R.id.menu_item_edit, true); // So
                sharedViewModel.getEstateSelectionStatus().observe(MenuActivity.this, estateStatus -> {
                    if(estateStatus.equals(EstateStatus.SALE)) {
                        setMenuItemVisible(menu, R.id.menu_item_sell, true);
                    }
                });
                return -1;
            }

            @Override
            public Integer visitMap() {
                setMenuItemVisible(menu, R.id.menu_item_map,false);
                return R.id.menu_item_map;
            }
        });
    }

    private void setMenuItemVisible(Menu menu, int menuItemId, boolean visible) {
        menu.findItem(menuItemId).setVisible(visible);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu_item_settings) {
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
            public Boolean visitSell() {
                onSellCalled();
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

    private void onSellCalled() {
        this.sharedViewModel.updateAction(Action.SELL);
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
    public void setEstateSelectionStatus(EstateStatus estateStatus) {
        this.sharedViewModel.updateEstateSelection(estateStatus);
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
