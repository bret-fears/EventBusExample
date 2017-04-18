package com.willowtreeapps.eventbusexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.willowtreeapps.eventbusexample.models.ProductDetailEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;


public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";

    private TextView brandTv;
    private TextView nameTv;
    private TextView priceTv;

    public static DetailFragment newIstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brandTv = (TextView) view.findViewById(R.id.brand_text);
        nameTv = (TextView) view.findViewById(R.id.name_text);
        priceTv = (TextView) view.findViewById(R.id.price_text);
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onProductDetailEvent(ProductDetailEvent event) {
        Log.i(TAG, String.format("Product details received for identifier %s on %s",
                event.getIdentifier(),
                Thread.currentThread().getName()));

        brandTv.setText(event.getBrand());
        nameTv.setText(event.getName());
        priceTv.setText(String.format(Locale.US, "$%.2f", event.getPrice()));
    }

}
