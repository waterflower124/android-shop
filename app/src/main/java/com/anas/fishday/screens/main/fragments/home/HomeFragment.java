package com.anas.fishday.screens.main.fragments.home;


import android.content.Context;
//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.R;
import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentHomeBinding;
import com.anas.fishday.entities.Category;
import com.anas.fishday.entities.Meter;
import com.anas.fishday.entities.Offer;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.cartactivity.CartActivity;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.main.fragments.home.adapters.CategoriesAdapter;
import com.anas.fishday.screens.main.fragments.home.adapters.OffersPagerAdapter;
import com.anas.fishday.screens.main.fragments.home.adapters.ProductsAdapter;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomeInteractor;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomePresenter;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnCategorySelectedListener;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnOfferClickListener;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnProductClickListener;
import com.anas.fishday.screens.meter.MeterActivity;
import com.anas.fishday.screens.productdetails.ProductDetailsActivity;
import com.anas.fishday.screens.productdetails.dialog.CreateOrderItemDialogFragment;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;
import com.anas.fishday.utils.ImageDownloader;

import java.util.List;

public class HomeFragment extends FishDayFragment implements HomeInteractor, CartGetInteractor, OnProductClickListener,
        OnCategorySelectedListener, CreateOrderItemDialogFragment.OnCreateOrderItemSuccessListener, CreateOrderItemDialogFragment.OnCreateOrderItemFailureListener,
        OnOfferClickListener {

    private FishDayActivity fishDayActivity;
    private FragmentHomeBinding binding;
    private HomePresenter presenter;
    private CartGetPresenter presenter_cart;

    private RecyclerView.LayoutManager categoriesLayoutManager;
    private CategoriesAdapter categoriesAdapter;

    private RecyclerView.LayoutManager productsLayoutManager;
    private ProductsAdapter productsAdapter;

    private RecyclerView.LayoutManager offersLayoutManager;
    //    private OffersAdapter offersAdapter;
    private OffersPagerAdapter offersPagerAdapter;

    private int lastCategoryIdSelected = 1;
    private Category lastCategorySelected;

    private ImageDownloader imageDownloader;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int meterPercentage;
    private int offerViewPagerPosition = 1;
    private OnFragmentAttachedListener onFragmentAttachedListener;

    private Handler handler = new Handler();
    private Runnable runnable;

    private static final int DELAY = 3000;
    private boolean isMove;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishDayActivity = (FishDayActivity) getActivity();
        imageDownloader = FishDayApplication.getWebServiceComponent().getImageDownloader();
        presenter = new HomePresenterImpl(this);
        presenter_cart = new CartGetPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        initToolbar();
        initCategoriesRecyclerView();
        initProductRecyclerView();
        initOffersRecyclerView();
        getCategories();
        getOffers();
        getMeter();
        initMeterAnimation();
        presenter_cart.getCart();
        return binding.getRoot();
    }

    float dX, dY;

    private void initMeterAnimation() {
        binding.pointerSpeedometer.setOnTouchListener((view, event) -> {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:

                    view.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                    isMove = true;
                    break;
                case MotionEvent.ACTION_UP:
                    if (isMove)
                        isMove = false;
                    else
                        view.performClick();
                    break;
                default:
                    return false;
            }
            return true;
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAttachedListener) {
            onFragmentAttachedListener = (OnFragmentAttachedListener) context;
        }
    }



    private void initToolbar() {
        ((MainActivity) getActivity()).toolbar.setTitle(R.string.home);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void getProducts(int categoryId) {
        presenter.getProducts(categoryId);
    }

    private void getOffers() {
        presenter.getOffers();
    }

    private void getCategories() {
        fishDayActivity.showProgressDialog();
        presenter.getCategories();
    }

    private void getMeter() {
        presenter.getMeter();
    }

    private void initCategoriesRecyclerView() {
        categoriesAdapter = new CategoriesAdapter(this);
        categoriesLayoutManager = new LinearLayoutManager(fishDayActivity, LinearLayoutManager.HORIZONTAL, false);
        binding.categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    private void initProductRecyclerView() {
        productsAdapter = new ProductsAdapter(fishDayActivity, imageDownloader, this);
        productsLayoutManager = new GridLayoutManager(fishDayActivity, 2);
        binding.productsRecyclerView.setNestedScrollingEnabled(false);
        binding.productsRecyclerView.setLayoutManager(productsLayoutManager);
        binding.productsRecyclerView.setAdapter(productsAdapter);
    }

    private void initOffersRecyclerView() {
//        offersAdapter = new OffersAdapter(fishDayActivity, imageDownloader, this);
//        offersLayoutManager = new LinearLayoutManager(fishDayActivity,
//                LinearLayoutManager.HORIZONTAL, false);
//        binding.offersRecyclerView.setLayoutManager(offersLayoutManager);
//        binding.offersRecyclerView.setAdapter(offersAdapter);


        offersPagerAdapter = new OffersPagerAdapter(fishDayActivity, this);
        binding.viewPager.setAdapter(offersPagerAdapter);
    }

    @Override
    public void showErrorForGettingProducts(String message) {
        fishDayActivity.dismissProgressDialog();
        fishDayActivity.showSnackBar(binding.homeCoordinator, message);
    }

    @Override
    public void showProducts(ResponseEntity<List<Product>> productResponseEntity) {
        fishDayActivity.dismissProgressDialog();
        if (productResponseEntity != null && productResponseEntity.getEntity() != null &&
                productResponseEntity.getEntity().size() > 0)
            binding.descriptionTv.setText(productResponseEntity.getEntity().get(0).getCategory().getDescription());
        productsAdapter.setProductList(productResponseEntity.getEntity());
    }

    @Override
    public void showErrorForGettingOffers(String message) {
        fishDayActivity.dismissProgressDialog();
        fishDayActivity.showSnackBar(binding.homeCoordinator, message);
    }

    @Override
    public void showOffers(ResponseEntity<List<Offer>> offersResponseEntity) {
        fishDayActivity.dismissProgressDialog();
        if (offersResponseEntity.getEntity() != null && offersResponseEntity.getEntity().size() > 0) {
//            offersAdapter.setOfferList(productResponseEntity.getEntity());
            offersPagerAdapter.setOffers(offersResponseEntity.getEntity());
            runnable = () -> {
                if (offerViewPagerPosition < offersPagerAdapter.getCount()) {
                    binding.viewPager.setCurrentItem(offerViewPagerPosition);
                    offerViewPagerPosition++;
                } else {
                    binding.viewPager.setCurrentItem(0);
                    offerViewPagerPosition = 1;

                }
                handler.postDelayed(runnable, DELAY);
            };
            handler.postDelayed(runnable, DELAY);
        } else {
            binding.viewPager.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorForGettingCategories(String message) {
        fishDayActivity.dismissProgressDialog();
        fishDayActivity.showSnackBar(binding.homeCoordinator, message);
    }

    @Override
    public void showCategories(ResponseEntity<List<Category>> categoriesResponseEntity) {
        fishDayActivity.dismissProgressDialog();
        categoriesAdapter.setCategories(categoriesResponseEntity.getEntity());
        lastCategorySelected = categoriesResponseEntity.getEntity().get(0);
        lastCategoryIdSelected = lastCategorySelected.getId();
        lastCategorySelected.setSelected(true);
        categoriesAdapter.notifyDataSetChanged();
        getProducts(lastCategoryIdSelected);
        fishDayActivity.dismissProgressDialog();
    }

    @Override
    public void showErrorForGettingMeter(String message) {
        fishDayActivity.dismissProgressDialog();
        fishDayActivity.showSnackBar(binding.homeCoordinator, message);
    }

    @Override
    public void showMeter(ResponseEntity<Meter> responseEntity) {
        fishDayActivity.dismissProgressDialog();
        meterPercentage = responseEntity.getEntity().getPercentage();
        binding.pointerSpeedometer.speedTo(meterPercentage);
        FishDayStorage.setMeterPercentage(meterPercentage);

        binding.pointerSpeedometer.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("Meter", responseEntity.getEntity().getPercentage());
            fishDayActivity.startActivity(MeterActivity.class, bundle);
        });
    }

    @Override
    public void onProductClick(Product product) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constant.PRODUCT, product);
        bundle.putString(Constant.PRODUCT_ID, String.valueOf(product.getId()));
        bundle.putInt("meterPercentage", meterPercentage);
        fishDayActivity.startActivity(ProductDetailsActivity.class, bundle);
    }

    @Override
    public void onAddToCartClick(Product product) {
        if(product.getQuantity_count() > 0) {
            CreateOrderItemDialogFragment orderDialogFragment = new CreateOrderItemDialogFragment();
            orderDialogFragment.setOnCreateOrderItemSuccessListener(this);
            orderDialogFragment.setOnCreateOrderItemFailureListener(this);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.PRODUCT, product);
            orderDialogFragment.setArguments(bundle);
            orderDialogFragment.show(fishDayActivity.getSupportFragmentManager(), "createOrder");
        }
    }

    @Override
    public void onCategorySelected(Category category) {
        int categoryId = category.getId();
        if (lastCategoryIdSelected != categoryId) {
            getProducts(categoryId);
            lastCategoryIdSelected = categoryId;

            if (lastCategorySelected != null) {
                lastCategorySelected.setSelected(false);
                category.setSelected(true);
                categoriesAdapter.notifyDataSetChanged();
                lastCategorySelected = category;
            } else {
                lastCategorySelected = category;
                category.setSelected(true);
                categoriesAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateOrderItemSuccess(Order order) {
        fishDayActivity.dismissProgressDialog();
        FishDayStorage.saveOrder(order);
        try {
            onFragmentAttachedListener.updateCartItems(order);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        fishDayActivity.startActivity(CartActivity.class);
    }

    @Override
    public void onCreateOrderItemFailure(String message) {
        fishDayActivity.dismissProgressDialog();
        fishDayActivity.showSnackBar(binding.homeCoordinator, message);
    }

    @Override
    public void onOfferClick(Offer offer) {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(offer.getLink()));

//        Intent browserIntent = new Intent();
//        browserIntent.setAction(Intent.ACTION_VIEW);
//        browserIntent.setDataAndType(Uri.parse(offer.getLink()), "image/*");
//        startActivity(browserIntent);
    }

    public interface OnFragmentAttachedListener {
        void updateCartItems(Order order);
    }


    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void showErrorOnGettingCart(String message) {
        fishDayActivity.dismissProgressDialog();
    }

    @Override
    public void handleGettingCart(Order order) {
        fishDayActivity.dismissProgressDialog();
        FishDayStorage.saveOrder(order);
        onFragmentAttachedListener.updateCartItems(order);
    }

}
