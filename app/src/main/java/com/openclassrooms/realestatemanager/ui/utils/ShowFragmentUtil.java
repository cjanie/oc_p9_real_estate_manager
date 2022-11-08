package com.openclassrooms.realestatemanager.ui.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ShowFragmentUtil {

    public void showFragment(FragmentManager fragmentManager, int frameLayoutId, Fragment fragment) {
        fragmentManager.beginTransaction().replace(frameLayoutId, fragment).commit();
    }
}
