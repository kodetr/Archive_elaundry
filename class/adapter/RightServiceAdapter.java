package com.kodetr.elaundry.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kodetr.elaundry.R;
import com.kodetr.elaundry.imp.ServicesCartImp;
import com.kodetr.elaundry.model.Services;
import com.kodetr.elaundry.model.ServiceMenu;
import com.kodetr.elaundry.model.ServicesCart;
import com.kodetr.elaundry.utils.FormatMoney;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RightServiceAdapter extends RecyclerView.Adapter {
    private final int MENU_TYPE = 0;
    private final int SERVICE_TYPE = 1;

    private Context mContext;
    private ArrayList<ServiceMenu> mMenuList;
    private int mItemCount;
    private ServicesCart servicesCart;
    private ServicesCartImp servicesCartImp;

    public RightServiceAdapter(Context mContext, ArrayList<ServiceMenu> mMenuList, ServicesCart servicesCart) {
        this.mContext = mContext;
        this.mMenuList = mMenuList;
        this.mItemCount = mMenuList.size();
        this.servicesCart = servicesCart;
        for (ServiceMenu menu : mMenuList) {
            mItemCount += menu.getServicesList().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int sum = 0;
        for (ServiceMenu menu : mMenuList) {
            if (position == sum) {
                return MENU_TYPE;
            }
            sum += menu.getServicesList().size() + 1;
        }
        return SERVICE_TYPE;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == MENU_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_menu_item, parent, false);
            return new MenuViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_service_item, parent, false);
            return new ServiceViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == MENU_TYPE) {
            MenuViewHolder menuholder = (MenuViewHolder) holder;
            menuholder.right_menu_title.setText(getMenuByPosition(position).getMenuName());
            menuholder.right_menu_layout.setContentDescription(position + "");
        } else {
            final ServiceViewHolder serviceholder = (ServiceViewHolder) holder;

            final Services services = getDishByPosition(position);

            Picasso.with(mContext).load(services.getServiceImage()).into(serviceholder.right_service_image_iv);
            serviceholder.right_service_name_tv.setText(services.getServiceName());
            serviceholder.right_service_price_tv.setText(FormatMoney.INSTANCE.formatMoney().format((double)services.getServicePrice()) + "" + services.getServiceType());
            serviceholder.right_service_layout.setContentDescription(position + "");

            int count = 0;
            if (servicesCart.getServicesSingleMap().containsKey(services)) {
                count = servicesCart.getServicesSingleMap().get(services);
            }
            if (count <= 0) {
                serviceholder.right_service_remove_iv.setVisibility(View.GONE);
                serviceholder.right_service_account_tv.setVisibility(View.GONE);
            } else {
                serviceholder.right_service_remove_iv.setVisibility(View.VISIBLE);
                serviceholder.right_service_account_tv.setVisibility(View.VISIBLE);
                serviceholder.right_service_account_tv.setText(count + "");
            }
            serviceholder.right_service_add_iv.setOnClickListener(view -> {
                if (servicesCart.addServicesSingle(services)) {
                    notifyItemChanged(position);
                    if (servicesCartImp != null)
                        servicesCartImp.add(view, position);
                }
            });

            serviceholder.right_service_remove_iv.setOnClickListener(view -> {
                if (servicesCart.subServicesSingle(services)) {
                    notifyItemChanged(position);
                    if (servicesCartImp != null)
                        servicesCartImp.remove(view, position);
                }
            });
        }
    }

    private ServiceMenu getMenuByPosition(int position) {
        int sum = 0;
        for (ServiceMenu menu : mMenuList) {
            if (position == sum) {
                return menu;
            }
            sum += menu.getServicesList().size() + 1;
        }
        return null;
    }

    private Services getDishByPosition(int position) {
        for (ServiceMenu menu : mMenuList) {
            if (position > 0 && position <= menu.getServicesList().size()) {
                return menu.getServicesList().get(position - 1);
            } else {
                position -= menu.getServicesList().size() + 1;
            }
        }
        return null;
    }

    public ServiceMenu getMenuOfMenuByPosition(int position) {
        for (ServiceMenu menu : mMenuList) {
            if (position == 0) return menu;
            if (position > 0 && position <= menu.getServicesList().size()) {
                return menu;
            } else {
                position -= menu.getServicesList().size() + 1;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public ServicesCartImp getServicesCartImp() {
        return servicesCartImp;
    }

    public void setServicesCartImp(ServicesCartImp servicesCartImp) {
        this.servicesCartImp = servicesCartImp;
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout right_menu_layout;
        private TextView right_menu_title;

        MenuViewHolder(View itemView) {
            super(itemView);
            right_menu_layout = itemView.findViewById(R.id.right_menu_item);
            right_menu_title = itemView.findViewById(R.id.right_menu_tv);
        }
    }

    private class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView right_service_image_iv;
        private TextView right_service_name_tv;
        private TextView right_service_price_tv;
        private LinearLayout right_service_layout;
        private ImageView right_service_remove_iv;
        private ImageView right_service_add_iv;
        private TextView right_service_account_tv;

        ServiceViewHolder(View itemView) {
            super(itemView);
            right_service_image_iv = itemView.findViewById(R.id.right_service_image);
            right_service_name_tv = itemView.findViewById(R.id.right_service_name);
            right_service_price_tv = itemView.findViewById(R.id.right_service_price);
            right_service_layout = itemView.findViewById(R.id.right_service_item);
            right_service_remove_iv = itemView.findViewById(R.id.right_service_remove);
            right_service_add_iv = itemView.findViewById(R.id.right_service_add);
            right_service_account_tv = itemView.findViewById(R.id.right_service_account);
        }

    }
}
