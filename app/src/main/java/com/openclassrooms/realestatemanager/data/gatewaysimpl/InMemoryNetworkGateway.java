package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.NetworkGateway;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class InMemoryNetworkGateway implements NetworkGateway {

    private BehaviorSubject<Boolean> isNetworkAvailable = BehaviorSubject.create();
    private BehaviorSubject<Boolean> isNetworkConnected = BehaviorSubject.create();

    @Override
    public Observable<Boolean> isNetworkAvailable() {
        return this.isNetworkAvailable;
    }

    @Override
    public Observable<Boolean> isNetworkConnected() {
        return this.isNetworkConnected;
    }

    @Override
    public Observable<String> getMessage() {
        return Observable.empty();
    }

    public void setNetworkAvailable(boolean isNetworkAvailable) {
        this.isNetworkAvailable.onNext(isNetworkAvailable);
    }

    public void setNetworkConnected(boolean isNetworkConnected) {
        this.isNetworkConnected.onNext(isNetworkConnected);
    }
}
