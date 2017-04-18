package com.willowtreeapps.eventbusexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.willowtreeapps.eventbusexample.models.ProductDetailEvent;
import com.willowtreeapps.eventbusexample.models.RetrieveProductEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class PaginatedActivity extends AppCompatActivity {

    public static final String TAG = "PaginatedActivity";
    private int productId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paginated_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.detail_fragment, DetailFragment.newIstance())
                .commit();

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new RetrieveProductEvent(++productId));
            }
        });

        Button previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productId > 0) {
                    EventBus.getDefault().post(new RetrieveProductEvent(--productId));
                }
            }
        });

        // Delay a post to allow the fragment to inflate
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new RetrieveProductEvent(productId));
            }
        }, 200);
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

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onRetrieveProductEvent(RetrieveProductEvent event) {
        Log.i(TAG, String.format("Retrieving the product %s on %s",
                event.getIdentifier(),
                Thread.currentThread().getName()));

        ProductDetailEvent pde = new ProductDetailEvent(productId,
                "Shiny",
                String.format("Awesome new hotness #%s", productId),
                19.99f);
        EventBus.getDefault().post(pde);
    }
}
