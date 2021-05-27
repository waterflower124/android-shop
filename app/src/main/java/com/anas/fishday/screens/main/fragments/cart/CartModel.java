package com.anas.fishday.screens.main.fragments.cart;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.RequestEntity;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.fragments.cart.interfaces.CartPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/27/2018.
 */

public class CartModel extends BaseModel {

    private AppService appService;
    private CartPresenter presenter;
    private CompositeDisposable disposable;

    public CartModel(CartPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        disposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void placeOrder(Order order){
        Disposable placeOrderDisposable = appService.placeOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPlaceOrderSuccess, this::onPlaceOrderError);
        disposable.add(placeOrderDisposable);
    }
    private void onPlaceOrderSuccess(ResponseEntity<Order> responseEntity) {
        presenter.onPlacingOrderSucceed(responseEntity);
    }
    private void onPlaceOrderError(Throwable throwable) {
        presenter.onPlacingOrderFailed(throwable.getMessage(), null);
    }

    public void deleteOrderItem(int orderItemId){
        Disposable placeOrderDisposable = appService.deleteOrderItem(orderItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDeleteOrderItemSuccess, this::onDeleteOrderItemError);
        disposable.add(placeOrderDisposable);
    }
    private void onDeleteOrderItemSuccess(ResponseEntity<Order> responseEntity) {
        presenter.onDeleteOrderItemSucceed(responseEntity);
    }
    private void onDeleteOrderItemError(Throwable throwable) {
        presenter.onDeleteOrderItemFailed(throwable.getMessage());
    }

    public void updateOrderItem(Order order){
        Disposable updateOrderDisposable = appService.updateOrderItem(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdateOrderItemSuccess, this::onUpdateOrderItemError);
        disposable.add(updateOrderDisposable);
    }
    private void onUpdateOrderItemSuccess(ResponseEntity<Product> responseEntity) {
        presenter.onUpdateItemSucceed(responseEntity.getProduct());
    }
    private void onUpdateOrderItemError(Throwable throwable) {
        presenter.onUpdateOrderItemFailed(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        disposable.clear();
    }
}
