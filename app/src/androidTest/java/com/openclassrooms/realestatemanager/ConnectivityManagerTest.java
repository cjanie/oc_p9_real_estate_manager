package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.NetworkGateway;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.InMemoryNetworkGateway;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

class UseConnectivityManager {

    private NetworkGateway networkGateway;

    public UseConnectivityManager(NetworkGateway networkGateway) {
        this.networkGateway = networkGateway;
    }

    public ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Observable<Boolean> isNetworkAvailable() {
        return this.networkGateway.isNetworkAvailable();
    }
    public Observable<Boolean> isNetworkConnected() {
        return this.networkGateway.isNetworkConnected();
    }

    public Observable<String> getMessage() {
        return this.networkGateway.getMessage();
    }

}

public class ConnectivityManagerTest {

    private Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = this.getContext();
        assertEquals("com.openclassrooms.realestatemanager", appContext.getPackageName());
    }

    @Test
    public void useConnectivityManager() {
        Context appContext = this.getContext();
        InMemoryNetworkGateway inMemoryNetworkGateway = new InMemoryNetworkGateway();
        ConnectivityManager connectivityManager = new UseConnectivityManager(inMemoryNetworkGateway).getConnectivityManager(appContext);
        assertNotNull(connectivityManager);

    }

    @Test
    public void networkAvailable() {
        InMemoryNetworkGateway networkGateway = new InMemoryNetworkGateway();
        networkGateway.setNetworkAvailable(true);

        List<Boolean> results = new ArrayList<>();

        new UseConnectivityManager(networkGateway).isNetworkAvailable().subscribe(results::add);
        Assert.assertTrue(results.get(0));
    }

    @Test
    public void networkNotAvailable() {
        InMemoryNetworkGateway networkGateway = new InMemoryNetworkGateway();
        networkGateway.setNetworkAvailable(false);

        List<Boolean> results = new ArrayList<>();

        new UseConnectivityManager(networkGateway).isNetworkAvailable().subscribe(results::add);
        Assert.assertFalse(results.get(0));
    }

    @Test
    public void networkConnected() {
        InMemoryNetworkGateway networkGateway = new InMemoryNetworkGateway();
        networkGateway.setNetworkConnected(true);

        List<Boolean> results = new ArrayList<>();

        new UseConnectivityManager(networkGateway).isNetworkConnected().subscribe(results::add);
        Assert.assertTrue(results.get(0));
    }

    @Test
    public void networkNotConnected() {
        InMemoryNetworkGateway networkGateway = new InMemoryNetworkGateway();
        networkGateway.setNetworkConnected(false);

        List<Boolean> results = new ArrayList<>();

        new UseConnectivityManager(networkGateway).isNetworkConnected().subscribe(results::add);
        Assert.assertFalse(results.get(0));
    }

}
