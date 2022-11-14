package com.openclassrooms.realestatemanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.ListEstatesRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.enums.Action;
import com.openclassrooms.realestatemanager.ui.enums.ActionVisitor;
import com.openclassrooms.realestatemanager.ui.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.ui.fragments.EstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.MapEstatesFragment;
import com.openclassrooms.realestatemanager.ui.fragments.SearchFragment;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormAddEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormUpdateEstateFragment;

public abstract class MobileAndTabletActivity extends BaseActivity implements ListEstatesRecyclerViewAdapter.HandleEstateDetails {

    private final int LAYOUT_ID = R.layout.activity_navigation;

    private boolean isTablet;

    private FragmentManager fragmentManager;

    private final int SECOND_FRAMELAYOUT_FOR_TABLET = R.id.frame_layout_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.LAYOUT_ID);

        this.isTablet = this.getResources().getBoolean(R.bool.is_tablet);
        this.fragmentManager = this.getSupportFragmentManager();
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

    protected boolean showFragmentForAction(Action action) {
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

    private Fragment getFragmentForAction(Action action) {
        return action.accept(new ActionVisitor<Fragment>() {
            @Override
            public Fragment visitHome() {
                return new EstatesFragment(MobileAndTabletActivity.this);
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
                return new SearchFragment(MobileAndTabletActivity.this);
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


}
