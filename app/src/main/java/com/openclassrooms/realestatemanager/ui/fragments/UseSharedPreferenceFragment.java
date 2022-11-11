package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

public class UseSharedPreferenceFragment extends BaseFragment implements HandleDevise {

    protected SharedPreferences sharedPreferences;

    protected boolean isDeviseEuro;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initDevise();


    }

    private void initDevise() {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        this.isDeviseEuro = this.sharedPreferences.getBoolean("devise_preference", false);
        Log.d(this.getClass().getName(), "------is devise euro --------- " + this.isDeviseEuro);
    }

    @Override
    public boolean isDeviseEuro() {
        return this.isDeviseEuro;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initDevise();
    }

}
