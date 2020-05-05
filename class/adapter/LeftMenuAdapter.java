package com.kodetr.elaundry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.kodetr.elaundry.R;
import com.kodetr.elaundry.model.ServiceMenu;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeftMenuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ServiceMenu> mMenuList;
    private int mSelectedNum;
    private List<onItemSelectedListener> mSelectedListenerList;

    public interface onItemSelectedListener {
        void onLeftItemSelected(int postion, ServiceMenu menu);
    }

    public void addItemSelectedListener(onItemSelectedListener listener) {
        if (mSelectedListenerList != null)
            mSelectedListenerList.add(listener);
    }

    public void removeItemSelectedListener(onItemSelectedListener listener) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty())
            mSelectedListenerList.remove(listener);
    }

    public LeftMenuAdapter(Context mContext, ArrayList<ServiceMenu> mMenuList) {
        this.mContext = mContext;
        this.mMenuList = mMenuList;
        this.mSelectedNum = -1;
        this.mSelectedListenerList = new ArrayList<>();
        if (mMenuList.size() > 0)
            mSelectedNum = 0;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_menu_item, parent, false);
        return new LeftMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        ServiceMenu serviceMenu = mMenuList.get(position);
        LeftMenuViewHolder viewHolder = (LeftMenuViewHolder) holder;

        viewHolder.menuImage.setImageResource(serviceMenu.getMenuImage());

        if (mSelectedNum == position) {
            viewHolder.menuLayout.setSelected(true);
            viewHolder.menuImage.setColorFilter(Color.parseColor("#FFFFFF"));
        } else {
            viewHolder.menuLayout.setSelected(false);
            viewHolder.menuImage.setColorFilter(Color.parseColor("#0288D1"));
        }
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    public void setSelectedNum(int selectedNum) {
        if (selectedNum < getItemCount() && selectedNum >= 0) {
            this.mSelectedNum = selectedNum;
            notifyDataSetChanged();
        }
    }

    public int getSelectedNum() {
        return mSelectedNum;
    }

    private class LeftMenuViewHolder extends RecyclerView.ViewHolder {

        ImageView menuImage;
        LinearLayout menuLayout;

        LeftMenuViewHolder(final View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.left_menu_iv_image);
            menuLayout = itemView.findViewById(R.id.left_menu_item);

            menuLayout.setOnClickListener(view -> {
                int clickPosition = getAdapterPosition();
//                    setSelectedNum(clickPosition);
                notifyItemSelected(clickPosition);

            });
        }
    }

    private void notifyItemSelected(int position) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty()) {
            for (onItemSelectedListener listener : mSelectedListenerList) {
                listener.onLeftItemSelected(position, mMenuList.get(position));
            }
        }
    }
}
