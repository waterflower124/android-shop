package com.anas.fishday.screens.productdetails;

//import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityProductDetailsBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.cartactivity.CartActivity;
import com.anas.fishday.screens.meter.MeterActivity;
import com.anas.fishday.screens.orderstatus.OrderStatusActivity;
import com.anas.fishday.screens.productdetails.adapter.ImagePagerAdapter;
import com.anas.fishday.screens.productdetails.dialog.CreateOrderItemDialogFragment;
import com.anas.fishday.screens.productdetails.interfaces.OnImageClickListener;
import com.anas.fishday.screens.productdetails.interfaces.ProductDetailsInteractor;
import com.anas.fishday.screens.productdetails.interfaces.ProductDetailsPresenter;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends FishDayActivity implements ProductDetailsInteractor, CartGetInteractor,
        View.OnClickListener, OnImageClickListener, CreateOrderItemDialogFragment.OnCreateOrderItemSuccessListener, CreateOrderItemDialogFragment.OnCreateOrderItemFailureListener {

    private FishDayActivity fishDayActivity;
    private ActivityProductDetailsBinding detailsBinding;
    private Product product;
    private String product_id;
    private ImagePagerAdapter pagerAdapter;
    private int meterPercentage;
    private CartGetPresenter presenter;
    private ProductDetailsPresenter productDetailsPresenter;

    Boolean cart_button_click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);

        fishDayActivity = (FishDayActivity) this;

        Bundle bundle = getBundle();
        if (bundle != null) {
            product_id = bundle.getString(Constant.PRODUCT_ID);
            meterPercentage = bundle.getInt("meterPercentage");
        }

        productDetailsPresenter = new ProductDetailsPresenterImpl(this);
        presenter = new CartGetPresenterImpl(this);

        fishDayActivity.showProgressDialog();

        productDetailsPresenter.getProductDetail(product_id);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cart_button_click = false;
        presenter.getCart();
    }

    private void setDataToView() {

        detailsBinding.setVariable(BR.productDetails, product);
        detailsBinding.setVariable(BR.productDetailsOnClickListener, this);
        detailsBinding.executePendingBindings();

        pagerAdapter = new ImagePagerAdapter(this, product.getImages(), this);
        detailsBinding.imageViewPager.setAdapter(pagerAdapter);

        detailsBinding.pointerSpeedometer.speedPercentTo(meterPercentage);
        detailsBinding.pointerSpeedometer.setOnClickListener(v -> {
            Bundle meterBundle = new Bundle();
            meterBundle.putInt("Meter", meterPercentage);
            startActivity(MeterActivity.class, meterBundle);
        });
        if(product.getQuantity().equals("piece")) {
            detailsBinding.priceKiloLayout.setVisibility(View.GONE);
            if(product.getPromotionPiecePrice() != null && Float.parseFloat(product.getPromotionPiecePrice()) != 0) {
                detailsBinding.originPiecePriceTextView.setVisibility(View.VISIBLE);
                detailsBinding.originPieceUnitTextView.setVisibility(View.VISIBLE);
                detailsBinding.originPiecePriceTextView.setPaintFlags(detailsBinding.originPiecePriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                detailsBinding.originPiecePriceTextView.setVisibility(View.GONE);
                detailsBinding.originPieceUnitTextView.setVisibility(View.GONE);
            }
        } else if(product.getQuantity().equals("kilo")) {
            detailsBinding.pricePieceLayout.setVisibility(View.GONE);
            if(product.getPromotionKiloPrice() != null && Float.parseFloat(product.getPromotionKiloPrice()) != 0) {
                detailsBinding.originKiloPriceTextView.setVisibility(View.VISIBLE);
                detailsBinding.originKiloUnitTextView.setVisibility(View.VISIBLE);
                detailsBinding.originKiloPriceTextView.setPaintFlags(detailsBinding.originKiloPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                detailsBinding.originKiloPriceTextView.setVisibility(View.GONE);
                detailsBinding.originKiloUnitTextView.setVisibility(View.GONE);
            }
        } else {
            if(Float.parseFloat(product.getKiloPrice()) == 0) {
                detailsBinding.priceKiloLayout.setVisibility(View.GONE);
            } else {
                detailsBinding.priceKiloLayout.setVisibility(View.VISIBLE);
                if(product.getPromotionPiecePrice() != null && Float.parseFloat(product.getPromotionPiecePrice()) != 0) {
                    detailsBinding.originPiecePriceTextView.setVisibility(View.VISIBLE);
                    detailsBinding.originPieceUnitTextView.setVisibility(View.VISIBLE);
                    detailsBinding.originPiecePriceTextView.setPaintFlags(detailsBinding.originPiecePriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    detailsBinding.originPiecePriceTextView.setVisibility(View.GONE);
                    detailsBinding.originPieceUnitTextView.setVisibility(View.GONE);
                }
            }
            if(Float.parseFloat(product.getPiecePrice()) == 0) {
                detailsBinding.priceKiloLayout.setVisibility(View.GONE);
            } else {
                detailsBinding.priceKiloLayout.setVisibility(View.VISIBLE);
                if(product.getPromotionKiloPrice() != null && Float.parseFloat(product.getPromotionKiloPrice()) != 0) {
                    detailsBinding.originKiloPriceTextView.setVisibility(View.VISIBLE);
                    detailsBinding.originKiloUnitTextView.setVisibility(View.VISIBLE);
                    detailsBinding.originKiloPriceTextView.setPaintFlags(detailsBinding.originKiloPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    detailsBinding.originKiloPriceTextView.setVisibility(View.GONE);
                    detailsBinding.originKiloUnitTextView.setVisibility(View.GONE);
                }
            }
        }


        if(FishDayStorage.getAppLanguage().equals(Constant.LANGUAGE_EN)) {
            detailsBinding.soldoutIv.setImageResource(R.drawable.soldout_en);
        } else {
            detailsBinding.soldoutIv.setImageResource(R.drawable.soldout_ar);
        }
        if(product.getQuantity_count() > 0) {
            detailsBinding.soldoutIv.setVisibility(View.GONE);
        } else {
            detailsBinding.soldoutIv.setVisibility(View.VISIBLE);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = detailsBinding.toolbar.toolbar;
        detailsBinding.toolbar.toolbar.setTitle(product.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailsBinding.toolbar.cartLayout.setVisibility(View.VISIBLE);
        detailsBinding.toolbar.cartLayout.setOnClickListener(view -> {cart_button_click = true; presenter.getCart();});
        try {
            Order order = FishDayStorage.getOrder();
            if(order.getStatus() == Constant.ORDER_STATUS_CART) {
                detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(order.getOrderItems().size()));
            } else {
                detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(0));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderNowBtn:
                if(product.getQuantity_count() > 0) {
                    CreateOrderItemDialogFragment orderDialogFragment = new CreateOrderItemDialogFragment();
                    orderDialogFragment.setOnCreateOrderItemSuccessListener(this);
                    orderDialogFragment.setOnCreateOrderItemFailureListener(this);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.PRODUCT, product);
                    orderDialogFragment.setArguments(bundle);
                    orderDialogFragment.show(getSupportFragmentManager(), "createOrder");
                }
                break;
        }
    }

    @Override
    public void onImageClick(String image) {
        Bundle bundle = new Bundle();
        bundle.putString("image", image);
//        startActivity(ImageViewerActivity.class, bundle);
    }

    @Override
    public void onCreateOrderItemSuccess(Order order) {
        FishDayStorage.saveOrder(order);
        try {
            if(order.getStatus() == Constant.ORDER_STATUS_CART) {
                detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(order.getOrderItems().size()));
            } else {
                detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(0));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        startActivity(CartActivity.class);
    }

    @Override
    public void onCreateOrderItemFailure(String message) {
        fishDayActivity.showSnackBar(detailsBinding.productDetailsCoordinator, message);
    }

    @Override
    public void showErrorOnGettingCart(String message) {

    }

    @Override
    public void handleGettingCart(Order order) {
        FishDayStorage.saveOrder(order);
        if(cart_button_click) {
            cart_button_click = false;
            if (order.getStatus() == Constant.ORDER_STATUS_CART) {
                startActivity(CartActivity.class);
            } else {
                startActivity(OrderStatusActivity.class);
            }
        } else {
            if(order.getStatus() == Constant.ORDER_STATUS_CART) {
                detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(order.getOrderItems().size()));
            } else {
                detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(0));
            }
        }
    }

    @Override
    public void showErrorForGettingProductDetail(String message) {
        fishDayActivity.dismissProgressDialog();
    }

    @Override
    public void showProductDetail(ResponseEntity<Product> productResponseEntity) {

        fishDayActivity.dismissProgressDialog();

        product = productResponseEntity.getEntity();
        Double real_kiloprice = 0.0;
        if(product.getKiloPrice() != null && !product.getKiloPrice().isEmpty()) {
            real_kiloprice = Double.parseDouble(product.getKiloPrice()) - Double.parseDouble(product.getPromotionKiloPrice());
            product.setReal_Kiloprice(String.valueOf(real_kiloprice));
            product.setOrigin_Kiloprice(product.getKiloPrice());
        }
        Double real_pieceprice = 0.0;
        if(product.getPiecePrice() != null && !product.getPiecePrice().isEmpty()) {
            real_pieceprice = Double.parseDouble(product.getPiecePrice()) - Double.parseDouble(product.getPromotionPiecePrice());
            product.setReal_Pieceprice(String.valueOf(real_pieceprice));
            product.setOrigin_Pieceprice(product.getPiecePrice());
        }

        initToolbar();
        setDataToView();

        Double product_price = 0.0;
        if(product.getQuantity().equals("piece")) {
            product_price = Double.parseDouble(product.getPiecePrice());
        } else if(product.getQuantity().equals("kilo")) {
            product_price = Double.parseDouble(product.getKiloPrice());
        } else {
            if(Float.parseFloat(product.getKiloPrice()) != 0) {
                product_price = Double.parseDouble(product.getKiloPrice());
            } else if(Float.parseFloat(product.getPiecePrice()) != 0) {
                product_price = Double.parseDouble(product.getPiecePrice());
            }
        }

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.PRICE, product_price);
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, product.getId());
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, product.getName());
        eventValue.put(AFInAppEventParameterName.CURRENCY, "SAR");
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.CONTENT_VIEW, eventValue);
    }
}
