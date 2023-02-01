package com.openclassrooms.realestatemanager.ui.viewmodels;

import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.CONNECT_TIME_OUT;
import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.PING_URL;
import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.READ_TIME_OUT;
import static com.openclassrooms.realestatemanager.ui.utils.HttpConnectionConfig.REQUEST_METHOD;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class ConnectivityViewModel extends ViewModel {

    private MutableLiveData<Boolean> isNetworkConnected;

    public ConnectivityViewModel() {

        this.isNetworkConnected = new MutableLiveData<>(false);
        this.isNetworkConnectedSubject.subscribe(
                isNetworkConnectedResult -> {
                    //
                    this.isNetworkConnected.postValue(isNetworkConnectedResult);
                }
        );
    }

    public LiveData<Boolean> isNetworkConnected() {
        return this.isNetworkConnected;
    }

    public void updateIsNetworkConnected() {
        this.updateIsNetworkConnectedSubject();
    }

    private BehaviorSubject<Boolean> isNetworkConnectedSubject = BehaviorSubject.create();

    private void updateIsNetworkConnectedSubject() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(PING_URL).openConnection();
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setRequestMethod(REQUEST_METHOD);
            httpURLConnection.connect();
            this.isNetworkConnectedSubject.onNext(httpURLConnection.getResponseCode() == 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
