package com.anas.fishday.screens.productdetails.dialog;

import android.content.Context;
//import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.DialogCreateOrderItemBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.Product;
import com.anas.fishday.screens.productdetails.dialog.interfaces.CreateOrderItemInteractor;
import com.anas.fishday.screens.productdetails.dialog.interfaces.CreateOrderItemPresenter;
import com.anas.fishday.screens.productdetails.dialog.interfaces.OnCreateOrderDialogClickListener;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anas on 2/24/2018.
 */

public class CreateOrderItemDialogFragment extends DialogFragment implements CreateOrderItemInteractor, OnCreateOrderDialogClickListener {

    private DialogCreateOrderItemBinding orderBinding;
    private FishDayActivity activity;
    private Product product;
    private int quantity = 1;
    private int orderId;

    private CreateOrderItemPresenter presenter;

    private OnCreateOrderItemSuccessListener onCreateOrderItemSuccessListener;
    private OnCreateOrderItemFailureListener onCreateOrderItemFailureListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (FishDayActivity) getActivity();
        presenter = new CreateOrderItemPresenterImpl(this);
        product = (Product) getArguments().getSerializable(Constant.PRODUCT);
        orderId = FishDayStorage.getOrder().getId();
        Log.e("Order ID ", String.valueOf(orderId));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        orderBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_order_item,
                container, false);
        orderBinding.setVariable(BR.createOrderProduct, product);
        orderBinding.setVariable(BR.createOrderOnClickListener, this);
        orderBinding.executePendingBindings();
        initViews();
        return orderBinding.getRoot();
    }

    private void initViews() {
        orderBinding.quantityTv.setText(String.valueOf(quantity));
        if(product.getQuantity().equals("piece")) {
            String unit_array[] = {getResources().getString(R.string.per_piece)};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, unit_array);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            orderBinding.typeSpinner.setAdapter(spinnerArrayAdapter);
            orderBinding.methodTv.setVisibility(View.GONE);
            orderBinding.cuttingWaysSpinner.setVisibility(View.GONE);
        } else if(product.getQuantity().equals("kilo")) {
            String unit_array[] = {getResources().getString(R.string.per_kilo)};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, unit_array);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            orderBinding.typeSpinner.setAdapter(spinnerArrayAdapter);
//            orderBinding.methodTv.setVisibility(View.GONE);
//            orderBinding.cuttingWaysSpinner.setVisibility(View.GONE);
        } else {
            String unit_array[] = {getResources().getString(R.string.per_kilo), getResources().getString(R.string.per_piece)};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, unit_array);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            orderBinding.typeSpinner.setAdapter(spinnerArrayAdapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onIncreaseQuantityClick() {
        quantity++;
        orderBinding.quantityTv.setText(String.valueOf(quantity));
        Log.e("Quantity increase ", quantity + "");
    }

    @Override
    public void onDecreaseQuantityClick() {
        if (quantity > 1) {
            quantity--;
            orderBinding.quantityTv.setText(String.valueOf(quantity));
            Log.e("Quantity decrease ", quantity + "");
        }
    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    @Override
    public void onAddToCartClick(Product product) {

//        if (FishDayStorage.getOrder().getStatus() == Constant.ORDER_STATUS_CART) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setOrderId(orderId);
            orderItem.setQuantity(quantity);
            if(product.getQuantity().equals("kilo")) {
                orderItem.setQuantityType(0);
            } else if(product.getQuantity().equals("piece")) {
                orderItem.setQuantityType(1);
            } else {
                switch (orderBinding.typeSpinner.getSelectedItemPosition()) {
                    case 0:
//                  orderItem.setQuantityType(Constant.QUANTITY_TYPE_KG);
                        orderItem.setQuantityType(orderBinding.typeSpinner.getSelectedItemPosition());
                        break;
                    case 1:
//                  orderItem.setQuantityType(Constant.QUANTITY_TYPE_PIECE);
                        orderItem.setQuantityType(orderBinding.typeSpinner.getSelectedItemPosition());
                        break;
                }
            }
//        switch (orderBinding.cuttingWaysSpinner.getSelectedItemPosition()){
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//        }
            orderItem.setCuttingWay(orderBinding.cuttingWaysSpinner.getSelectedItemPosition());
            presenter.createOrderItem(orderItem);
            activity.showProgressDialog();

//        }else {
//            activity.showToast(R.string.error_already_have_cart);
//        }

    }

    public void setOnCreateOrderItemSuccessListener(OnCreateOrderItemSuccessListener listener) {
        this.onCreateOrderItemSuccessListener = listener;
    }

    public void setOnCreateOrderItemFailureListener(OnCreateOrderItemFailureListener listener) {
        this.onCreateOrderItemFailureListener = listener;
    }

    @Override
    public void onCreateOrderItemFailure(String message) {
//        activity.showToast(R.string.item_added_to_cart);
        activity.dismissProgressDialog();
        onCreateOrderItemFailureListener.onCreateOrderItemFailure(message);
        dismiss();
    }

    @Override
    public void onCreateOrderItemSuccess(Order order) {
        activity.showToast(R.string.item_added_to_cart);
        activity.dismissProgressDialog();
        FishDayStorage.saveOrder(order);
        onCreateOrderItemSuccessListener.onCreateOrderItemSuccess(order);

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.PRICE, order.getOrderItems().get(0).getTotalPrice());
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, order.getOrderItems().get(0).getOrderId());
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, order.getOrderItems().get(0).getProduct().getName());
        eventValue.put(AFInAppEventParameterName.CURRENCY, "SAR");
        eventValue.put(AFInAppEventParameterName.QUANTITY, order.getOrderItems().get(0).getQuantity());
        AppsFlyerLib.getInstance().trackEvent(getContext(), AFInAppEventType.ADD_TO_CART, eventValue);

        dismiss();
    }

    public interface OnCreateOrderItemSuccessListener{
        void onCreateOrderItemSuccess(Order order);
    }

    public interface OnCreateOrderItemFailureListener {
        void onCreateOrderItemFailure(String message);
    }
}
