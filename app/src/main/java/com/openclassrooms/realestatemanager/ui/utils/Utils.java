package com.openclassrooms.realestatemanager.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.openclassrooms.realestatemanager.dateprovider.DateProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euros) {
        return (int) Math.round(euros/0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */

    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getToday(DateProvider dateProvider) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(dateProvider.today());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE

     * @return
     */
/*
    public static Boolean isInternetAvailable(Context context){
        // dans test d'integration, accès au service
        // mettre le wifi à false et tester la connection
        // DNS 8888: internet est présent
        // GMS service

        // Wifi service + // Connection service
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }
*/
    // TODO
    public static boolean isWifiEnabled(WifiManager wifiManager) {
        return wifiManager.isWifiEnabled();
    }

    public static void setWifiEnabled(WifiManager wifiManager, boolean enable, Activity activity) {
        if(enable != wifiManager.isWifiEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                goToSettingsInternetConnectivity(activity);
            } else {
                if(enable) {
                    enableWifi(wifiManager);
                } else {
                    disableWifi(wifiManager);
                }
            }
        }
    }

    public static void checkWifi(WifiManager wifiManager, Activity activity) {
        if(!wifiManager.isWifiEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                goToSettingsInternetConnectivity(activity);
            } else {
                enableWifi(wifiManager);
            }
        }
    }

    private static void enableWifi(WifiManager wifiManager) {
        wifiManager.setWifiEnabled(true);
    }

    private static void disableWifi(WifiManager wifiManager) {
        wifiManager.setWifiEnabled(false);
    }

    private static void goToSettingsInternetConnectivity(Activity activity) {
        activity.startActivity(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
    }

}
