package com.anas.fishday.screens.productdetails;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomePresenter;
import com.anas.fishday.screens.productdetails.interfaces.ProductDetailsPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailsModel extends BaseModel {

    private AppService appService;
    private ProductDetailsPresenter presenter;
    private CompositeDisposable disposable;

    public ProductDetailsModel(ProductDetailsPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        disposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    @Override
    protected void clearDisposable() {
        disposable.clear();
    }

    public void getProductDetail(String product_id) {
        Disposable productDetailDisposable = appService.getProductDetail(product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestProductsSuccess, this::onRequestProductsError);
        disposable.add(productDetailDisposable);
    }

    private void onRequestProductsSuccess(ResponseEntity<Product> responseEntity) {
        presenter.onGettingProductDetailSucceed(responseEntity);
    }
    private void onRequestProductsError(Throwable throwable) {
        presenter.onGettingDetailFailed(throwable.getMessage());
    }
}
