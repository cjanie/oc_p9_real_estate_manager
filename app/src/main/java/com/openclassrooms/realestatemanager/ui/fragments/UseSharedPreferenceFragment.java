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

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

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
    }

    @Override
    public String getDeviseAsString() {
        return this.isDeviseEuro ? this.getString(R.string.euro) : this.getString(R.string.dollar);
    }

    @Override
    public Devise getDevise() {
        return this.isDeviseEuro ? Devise.EURO : Devise.DOLLAR;
    }

    @Override
    public Float getPriceInCurrentDevise(Estate estate) {
        Float priceInCurrentDevise;
        if(this.isDeviseEuro) {
            Integer priceInEuro;
            if(estate.getDevise().equals(Devise.DOLLAR)) {
                priceInEuro = Utils.convertDollarToEuro(Math.round(estate.getPrice()));
            } else {
                priceInEuro = Math.round(estate.getPrice());
            }
            priceInCurrentDevise = priceInEuro.floatValue();
        } else {
            Integer priceInDollar;
            if(estate.getDevise().equals(Devise.DOLLAR)) {
                priceInDollar = Math.round(estate.getPrice());
            } else {
                priceInDollar = Utils.convertEuroToDollar(Math.round(estate.getPrice()));
            }
            priceInCurrentDevise = priceInDollar.floatValue();
        }
        return priceInCurrentDevise;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initDevise();
    }



}
