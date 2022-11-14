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

public class NavigationActivity extends BaseActivity implements ListEstatesRecyclerViewAdapter.HandleEstateDetails {

    private final int LAYOUT_ID = R.layout.activity_navigation;

    protected SharedViewModel sharedViewModel;

    private FragmentManager fragmentManager;

    private boolean isTablet;

    private final int SECOND_FRAMELAYOUT_FOR_TABLET = R.id.frame_layout_details;

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
        this.setContentView(this.LAYOUT_ID);

        this.sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        this.fragmentManager = this.getSupportFragmentManager();
        this.isTablet = this.getResources().getBoolean(R.bool.is_tablet);

        // Observe menu action state
        this.sharedViewModel.getAction().observe(this, action -> {
            this.showFragmentForAction(action);
            this.invalidateMenu(); // Launches creation of the menu

        });

    }

    private Fragment getFragmentForAction(Action action) {
        return action.accept(new ActionVisitor<Fragment>() {
            @Override
            public Fragment visitHome() {
                return new EstatesFragment(NavigationActivity.this);
            }

            @Override
            public Fragment visitAdd() {
                return new FormAddEstateFragment();
            }

            @Override
            public Fragment visitEdit() {
                return new FormUpdateEstateFragment();
            }

            @Override
            public Fragment visitSearch() {
                return new SearchFragment(NavigationActivity.this);
            }

            @Override
            public Fragment visitDetails() {
                return new EstateDetailsFragment();
            }

            @Override
            public Fragment visitMap() {
                return new MapEstatesFragment();
            }
        });
    }

    private boolean showFragmentForAction(Action action) {
        Fragment fragment = this.getFragmentForAction(action);
        return action.accept(new ActionVisitor<Boolean>() {

            @Override
            public Boolean visitHome() {
                fullScreenOnMobileAndTablet(fragment);
                return true;
            }

            @Override
            public Boolean visitAdd() {
                fullScreenOnMobileAndTablet(fragment);
                return true;
            }

            @Override
            public Boolean visitEdit() {
                fullScreenOnMobileAndTablet(fragment);
                return true;
            }

            @Override
            public Boolean visitSearch() {
                fullScreenOnMobileAndTablet(fragment);
                return true;
            }

            @Override
            public Boolean visitDetails() {
                hasSpecialScreenOnTablet(fragment);
                return true;
            }

            @Override
            public Boolean visitMap() {
                fullScreenOnMobileAndTablet(fragment);
                return true;
            }
        });
    }

    private void fullScreenOnMobileAndTablet(Fragment fragment) {
        showFragment(fragment);
        if(this.isTablet) {
            this.hideFrameLayout(SECOND_FRAMELAYOUT_FOR_TABLET);
        }
    }

    private void hasSpecialScreenOnTablet(Fragment fragment) {
        if(!isTablet) {
            this.showFragment(fragment);
        } else {
            this.showFragmentForTablet(fragment);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        // if transaction.replace is used: onBackPressed considers the activity;
        // if transaction.add is uses: onBackPressed considers the fragments
        // First fragment should not be added to back stack
        if(this.fragmentManager.getFragments().isEmpty()) {
            transaction.add(R.id.frame_layout, fragment);
        } else {
            transaction.add(R.id.frame_layout, fragment).addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }

    private void showFragmentForTablet(Fragment fragment) {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        // replace() for blank when backPressed
        // add to backstack to stay on the same activity and remove the fragment
        if(this.fragmentManager.getFragments().isEmpty()) {
            transaction.replace(this.SECOND_FRAMELAYOUT_FOR_TABLET, fragment).addToBackStack(fragment.getClass().getName());
        } else {
            transaction.add(this.SECOND_FRAMELAYOUT_FOR_TABLET, fragment).addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();

        // Make the view visible
        FrameLayout frameLayout = this.findViewById(this.SECOND_FRAMELAYOUT_FOR_TABLET);
        frameLayout.setVisibility(View.VISIBLE);
    }

    private void hideFrameLayout(int frameLayoutId) {
        FrameLayout frameLayout = this.findViewById(frameLayoutId);
        frameLayout.setVisibility(View.GONE);
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
        MenuItem menuItem = menu.findItem(menuItemId);
        menuItem.setVisible(visible);
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

    private void onMapCalled() {
        this.sharedViewModel.updateAction(Action.MAP);
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
