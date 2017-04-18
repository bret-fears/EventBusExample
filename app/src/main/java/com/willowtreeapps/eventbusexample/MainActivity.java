package com.willowtreeapps.eventbusexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.willowtreeapps.eventbusexample.models.MobileNetConnectedEvent;
import com.willowtreeapps.eventbusexample.models.MobileNetDisconnectedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    public static final String NETWORK_INFO_EVENT = "NetworkInfoEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launch = (Button) findViewById(R.id.launch);
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PaginatedActivity.class));
            }
        });
    }

    @Subscribe
    public void onMobileDisconnectedEvent(MobileNetDisconnectedEvent event) {
        String message = "Mobile connection is not available \n";
        updateUI(message);
    }

    @Subscribe
    public void onMobileConnectedEvent(MobileNetConnectedEvent event) {
        String message = String.format("Mobile connection is available - %s\n",
                event.getDetailedState());
        updateUI(message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void updateUI(String message) {
        TextView networkInfoText = (TextView) findViewById(R.id.network_info);
        networkInfoText.setText(message);
        Log.i(NETWORK_INFO_EVENT, message);
    }
}
