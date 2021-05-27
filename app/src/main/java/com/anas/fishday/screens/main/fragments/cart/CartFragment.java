package com.anas.fishday.screens.main.fragments.cart;


//import android.databinding.DataBindingUtil;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentCartBinding;
import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.cartactivity.CartActivity;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.main.fragments.cart.adapters.CartAdapter;
import com.anas.fishday.screens.main.fragments.cart.interfaces.CartInteractor;
import com.anas.fishday.screens.main.fragments.cart.interfaces.CartPresenter;
import com.anas.fishday.screens.main.fragments.cart.interfaces.OnOrderItemClickListener;
import com.anas.fishday.screens.main.fragments.completeorder.CompleteOrderFragment;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.ImageDownloader;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends FishDayFragment implements CartInteractor, CartGetInteractor,
        OnOrderItemClickListener, View.OnClickListener {


    private CartPresenter presenter;
    private CartGetPresenter cartGetPresenter;
    private FishDayActivity fishDayActivity;
    private FragmentCartBinding binding;
    private Order order;
    private int orderId;

    private RecyclerView.LayoutManager productsLayoutManager;
    private CartAdapter cartAdapter;

    private ImageDownloader imageDownloader;
    private double subTotal;
    private double delivery;
    private double total;

    private OnFragmentAttachedListener onFragmentAttachedListener;

    private OrderItem deleted_orderItem;

    private Boolean order_now = false; //  true when click "Order Now" button

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishDayActivity = (FishDayActivity) getActivity();
        imageDownloader = FishDayApplication.getWebServiceComponent().getImageDownloader();
        presenter = new CartPresenterImpl(this);
        cartGetPresenter = new CartGetPresenterImpl(this);
        orderId = FishDayStorage.getOrder().getId();
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).toolbar.setTitle(R.string.cart);
        else if (getActivity() instanceof CartActivity)
            ((CartActivity) getActivity()).toolbar.setTitle(R.string.cart);

        deleted_orderItem = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_cart, container, false);
        binding.setVariable(BR.order, order);
        binding.setVariable(BR.cartOnClickListener, this);
        binding.executePendingBindings();
        initCartRecyclerView();
        getCart();
        return binding.getRoot();
    }

    private void initCartRecyclerView() {
        cartAdapter = new CartAdapter(fishDayActivity, imageDownloader, this);
        productsLayoutManager = new LinearLayoutManager(fishDayActivity);
        binding.cartRecyclerView.setLayoutManager(productsLayoutManager);
        binding.cartRecyclerView.setAdapter(cartAdapter);
    }

    private void getCart() {
        fishDayActivity.showProgressDialog();
        cartGetPresenter.getCart();
    }

    private void deleteOrderItem(int orderItemId) {
        presenter.deleteOrderItem(orderItemId);
    }

    @Override
    public void onIncreaseQuantityClick(OrderItem orderItem, int index) {
        int newQuantity = orderItem.getQuantity() + 1;
        double newTotalPrice = newQuantity * Double.parseDouble(orderItem.getUnitPrice());
        orderItem.setQuantity(newQuantity);
        orderItem.setTotalPrice(String.valueOf(newTotalPrice));
        cartAdapter.updateSingleOrderItem(index, orderItem);
        updateOrderTotalPrice(orderItem, true, false);
        order_now = false;
        Order order = new Order();
        order.setOrderItems(cartAdapter.getOrderItems());
//        presenter.placeOrder(orderId, order);
        presenter.updateCarItem(order);
    }

    @Override
    public void onDecreaseQuantityClick(OrderItem orderItem, int index) {
        if (orderItem.getQuantity() > 1) {
            int newQuantity = orderItem.getQuantity() - 1;
            double newTotalPrice = newQuantity * Double.parseDouble(orderItem.getUnitPrice());
            orderItem.setQuantity(newQuantity);
            orderItem.setTotalPrice(String.valueOf(newTotalPrice));
            cartAdapter.updateSingleOrderItem(index, orderItem);
            updateOrderTotalPrice(orderItem, false, false);
            order_now = false;
            Order order = new Order();
            order.setOrderItems(cartAdapter.getOrderItems());
//            presenter.placeOrder(orderId, order);
            presenter.updateCarItem(order);
        }
    }

    @Override
    public void onDeleteItemClick(OrderItem orderItem, int index) {
        deleted_orderItem = orderItem;
        deleteOrderItem(orderItem.getId());
        cartAdapter.removeOrderItem(index);
    }

    @Override
    public void showErrorOnGettingCart(String message) {
        fishDayActivity.showSnackBar(binding.cartCoordinator, message);
        fishDayActivity.dismissProgressDialog();
    }

    @Override
    public void handleGettingCart(Order order) {
//        order = cartResponseEntity.getEntity();
        cartAdapter.setOrderItems(order.getOrderItems());
        fishDayActivity.dismissProgressDialog();
        subTotal = Double.parseDouble(order.getSubTotal());
        total = Double.parseDouble(order.getTotal());
        delivery = Double.parseDouble(order.getDelivery());
        binding.subTotalTv.setText(String.format(getString(R.string.sar_format), order.getSubTotal()));
        binding.deliveryTv.setText(String.format(getString(R.string.sar_format), order.getDelivery()));
        binding.totalTv.setText(String.format(getString(R.string.sar_format), order.getTotal()));
    }

    @Override
    public void showErrorForPlacingOrder(String message, ResponseEntity<Order> cartResponseEntity) {
        Log.d("Place Order", "FAILED");
        if(cartResponseEntity.getProduct() == null) {
            fishDayActivity.showSnackBar(binding.cartCoordinator, message);
        } else {
//            if(order_now) {
//                Product product = cartResponseEntity.getProduct();
//                message = "There is(are) " + product.getQuantity_count() + " items. '" + product.getName() + "'. Please reduce count or delete corresponding item.";
//                fishDayActivity.showSnackBar(binding.cartCoordinator, message);
//            }
        }

        fishDayActivity.dismissProgressDialog();
    }

    @Override
    public void showSuccessForPlacingOrder(ResponseEntity<Order> cartResponseEntity) {
        Log.d("Place Order", "SUCCESS");
//        fishDayActivity.showSnackBar(binding.cartCoordinator, cartResponseEntity.getMessage());

        fishDayActivity.dismissProgressDialog();
        fishDayActivity.replaceFragment(CompleteOrderFragment.newInstance());

//        if (order_now) {
//            fishDayActivity.dismissProgressDialog();
//            fishDayActivity.replaceFragment(CompleteOrderFragment.newInstance());
//        } else {
//            FishDayStorage.saveOrder(cartResponseEntity.getEntity());
//        }

    }

    @Override
    public void showErrorForDeletingOrderItem(String message) {
        fishDayActivity.showSnackBar(binding.cartCoordinator, message);
        fishDayActivity.dismissProgressDialog();

    }

    @Override
    public void showSuccessForDeletingOrderItem(ResponseEntity<Order> cartResponseEntity) {
        order = cartResponseEntity.getEntity();
        FishDayStorage.saveOrder(order);
        onFragmentAttachedListener.updateCartItems(order);
        updateOrderTotalPrice(deleted_orderItem, false, true);
        fishDayActivity.showSnackBar(binding.cartCoordinator, cartResponseEntity.getMessage());
        fishDayActivity.dismissProgressDialog();

    }

    @Override
    public void showErrorForUpdateOrderItem(String message) {

    }

    @Override
    public void showSuccessForUpdateOrderItem(Product product) {

    }

    private void updateOrderTotalPrice(OrderItem orderItem, boolean increase, boolean total_delete) {
        double unitPrice = Double.parseDouble(orderItem.getUnitPrice());
        if(total_delete) {
            subTotal -= unitPrice * orderItem.getQuantity();
        } else {
            if (increase)
                subTotal += unitPrice;
            else
                subTotal -= unitPrice;
        }
        Order order = FishDayStorage.getOrder();
        delivery = Double.parseDouble(order.getDelivery());
        total = subTotal + delivery;

        binding.subTotalTv.setText(String.format(getString(R.string.sar_format), String.valueOf(subTotal)));
        binding.deliveryTv.setText(String.format(getString(R.string.sar_format), String.valueOf(delivery)));
        binding.totalTv.setText(String.format(getString(R.string.sar_format), String.valueOf(total)));

        deleted_orderItem = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderNowBtn:
                order_now = true;
                if (cartAdapter.getOrderItems() != null && cartAdapter.getOrderItems().size() > 0) {
                    if (total >= 100) {
                        Order order = new Order();
                        order.setOrderItems(cartAdapter.getOrderItems());
                        presenter.placeOrder(order);
                        fishDayActivity.showProgressDialog();
                    } else {
                        fishDayActivity.showSnackBar(binding.cartCoordinator, R.string.error_cart_less_than_100);
                    }
                } else {
                    fishDayActivity.showSnackBar(binding.cartCoordinator, R.string.error_cart_is_empty);
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAttachedListener) {
            onFragmentAttachedListener = (OnFragmentAttachedListener) context;
        }
    }

    public interface OnFragmentAttachedListener {
        void updateCartItems(Order order);
    }
}
