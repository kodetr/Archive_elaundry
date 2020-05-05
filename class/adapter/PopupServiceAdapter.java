package com.kodetr.elaundry.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kodetr.elaundry.R;
import com.kodetr.elaundry.imp.ServicesCartImp;
import com.kodetr.elaundry.model.Services;
import com.kodetr.elaundry.model.ServicesCart;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PopupServiceAdapter extends RecyclerView.Adapter {

    private static String TAG = "PopupServiceAdapter";
    private ServicesCart servicesCart;
    private Context mContext;
    private int itemCount;
    private ArrayList<Services> servicesList;
    private ServicesCartImp servicesCartImp;

    public PopupServiceAdapter(Context mContext, ServicesCart servicesCart) {
        this.servicesCart = servicesCart;
        this.mContext = mContext;
        this.itemCount = servicesCart.getServiceAccount();
        this.servicesList = new ArrayList<>();
        servicesList.addAll(servicesCart.getServicesSingleMap().keySet());
        Log.e(TAG, "PopupServiceAdapter: " + this.itemCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {
        ServiceViewHolder serviceholder = (ServiceViewHolder) holder;
        final Services services = getServiceByPosition(position);
        if (services != null) {

            Picasso.with(mContext).load(services.getServiceImage()).into(serviceholder.right_service_image_iv);
            serviceholder.right_service_name_tv.setText(services.getServiceName());
            serviceholder.right_service_price_tv.setText(services.getServicePrice() + " " + services.getServiceType());
            int num = servicesCart.getServicesSingleMap().get(services);
            serviceholder.right_service_account_tv.setText(num + "");

            serviceholder.right_service_add_iv.setOnClickListener(view -> {
                if (servicesCart.addServicesSingle(services)) {
                    notifyItemChanged(position);
                    if (servicesCartImp != null)
                        servicesCartImp.add(view, position);
                }
            });

            serviceholder.right_service_remove_iv.setOnClickListener(view -> {
                if (servicesCart.subServicesSingle(services)) {
                    servicesList.clear();
                    servicesList.addAll(servicesCart.getServicesSingleMap().keySet());
                    itemCount = servicesCart.getServiceAccount();
                    notifyDataSetChanged();
                    if (servicesCartImp != null)
                        servicesCartImp.remove(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    private Services getServiceByPosition(int position) {
        return servicesList.get(position);
    }

    public ServicesCartImp getServicesCartImp() {
        return servicesCartImp;
    }

    public void setServicesCartImp(ServicesCartImp servicesCartImp) {
        this.servicesCartImp = servicesCartImp;
    }

    private class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView right_service_image_iv;
        private TextView right_service_name_tv;
        private TextView right_service_price_tv;
        private ImageView right_service_remove_iv;
        private ImageView right_service_add_iv;
        private TextView right_service_account_tv;

        ServiceViewHolder(View itemView) {
            super(itemView);
            right_service_image_iv = itemView.findViewById(R.id.right_service_image);
            right_service_name_tv = itemView.findViewById(R.id.right_service_name);
            right_service_price_tv = itemView.findViewById(R.id.right_service_price);
            right_service_remove_iv = itemView.findViewById(R.id.right_service_remove);
            right_service_add_iv = itemView.findViewById(R.id.right_service_add);
            right_service_account_tv = itemView.findViewById(R.id.right_service_account);
        }

    }
}
