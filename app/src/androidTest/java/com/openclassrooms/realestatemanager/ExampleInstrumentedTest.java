package com.openclassrooms.realestatemanager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.ui.utils.Utils;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.openclassrooms.realestatemanager", appContext.getPackageName());
    }

    @Test
    public void connectionOK() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        Boolean isInternetAvailable = Utils.isWifiEnabled(wifiManager);
        assertEquals(true, isInternetAvailable);
        //Looper.prepare(); // Null pointer exception
        //Utils.setWifiEnabled(wifiManager, false, new Activity());
        // Runtime exception: can't create handler in main thread
        // that has not called Looper.prepare() // error points to Activity
    }

    @Test
    public void connectionNotAvailable() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager != null) wifiManager.setWifiEnabled(false);


        Boolean isInternetAvailable = Utils.isWifiEnabled(wifiManager);
        assertEquals(false, isInternetAvailable);
    }


}
