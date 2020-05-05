package com.kodetr.elaundry.wiget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kodetr.elaundry.TransactionActivity;
import com.kodetr.elaundry.R;
import com.kodetr.elaundry.adapter.PopupServiceAdapter;
import com.kodetr.elaundry.imp.ServicesCartImp;
import com.kodetr.elaundry.model.ServicesCart;
import com.kodetr.elaundry.utils.FormatMoney;


public class ServicesCartDialog extends Dialog implements View.OnClickListener, ServicesCartImp {

    private LinearLayout linearLayout, bottomLayout, clearLayout, bottomBack;
    private ServicesCart servicesCart;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private RecyclerView recyclerView;
    private PopupServiceAdapter dishAdapter;
    private ShopCartDialogImp shopCartDialogImp;
    private Button btnSelectPayment;

    public ServicesCartDialog(Context context, ServicesCart servicesCart, int themeResId) {
        super(context, themeResId);
        this.servicesCart = servicesCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);
        linearLayout = findViewById(R.id.linearlayout);
        bottomBack = findViewById(R.id.bottom_back);
        clearLayout = findViewById(R.id.clear_layout);
        bottomLayout = findViewById(R.id.services_cart_bottom);
        totalPriceTextView = findViewById(R.id.services_cart_total_tv);
        totalPriceNumTextView = findViewById(R.id.services_cart_total_num);
        recyclerView = findViewById(R.id.recycleview);
        btnSelectPayment = findViewById(R.id.btn_select_payment);
        bottomLayout.setOnClickListener(this);
        bottomBack.setOnClickListener(this);
        clearLayout.setOnClickListener(this);
        btnSelectPayment.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishAdapter = new PopupServiceAdapter(getContext(), servicesCart);
        recyclerView.setAdapter(dishAdapter);
        dishAdapter.setServicesCartImp(this);
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(1000);
    }

    @Override
    public void dismiss() {
        animationHide(1000);
    }

    @SuppressLint("SetTextI18n")
    private void showTotalPrice() {
        if (servicesCart != null && servicesCart.getServicesTotalPrice() > 0) {
            totalPriceTextView.setVisibility(View.VISIBLE);
            totalPriceTextView.setText(FormatMoney.INSTANCE.formatMoney().format((double) servicesCart.getServicesTotalPrice()));
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText("" + servicesCart.getServicesAccount());


        } else {
            totalPriceTextView.setVisibility(View.GONE);
            totalPriceNumTextView.setVisibility(View.GONE);
        }
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 0, 1000).setDuration(mDuration)
        );
        animatorSet.start();

        if (shopCartDialogImp != null) {
            shopCartDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ServicesCartDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.services_cart_bottom:
            case R.id.bottom_back:
                this.dismiss();
                break;
            case R.id.clear_layout:
                clear();
                break;
            case R.id.btn_select_payment:
                Intent intent = new Intent(getContext(), TransactionActivity.class);
                intent.putExtra("total_price", totalPriceTextView.getText());
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();
        if (servicesCart.getServicesAccount() == 0) {
            this.dismiss();
        }
    }

    public ShopCartDialogImp getShopCartDialogImp() {
        return shopCartDialogImp;
    }

    public void setShopCartDialogImp(ShopCartDialogImp shopCartDialogImp) {
        this.shopCartDialogImp = shopCartDialogImp;
    }

    public interface ShopCartDialogImp {
        void dialogDismiss();
    }

    public void clear() {
        servicesCart.clear();
        showTotalPrice();
        if (servicesCart.getServicesAccount() == 0) {
            this.dismiss();
        }
    }
}
