package com.willowtreeapps.eventbusexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.willowtreeapps.eventbusexample.models.MobileNetConnectedEvent;
import com.willowtreeapps.eventbusexample.models.MobileNetDisconnectedEvent;

import org.greenrobot.eventbus.EventBus;


public class MobileNetworkListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo info = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
        if (isMobileNetwork(context, info) && !info.isConnected()) {
            EventBus.getDefault().post(
                    new MobileNetDisconnectedEvent());
        } else if (isMobileNetwork(context, info) && info.isConnected()) {
            EventBus.getDefault().post(
                    new MobileNetConnectedEvent(info.getState().toString()));
        }

    }

    public boolean isMobileNetwork(Context context, NetworkInfo info) {
        return info != null &&
                (info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}
