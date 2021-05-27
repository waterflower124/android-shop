package com.anas.fishday.screens.main.activity;

//import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityMainBinding;
import com.anas.fishday.entities.ItemMenu;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.User;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.functions.lastorder.LastOrderInteractor;
import com.anas.fishday.functions.lastorder.LastOrderPresenter;
import com.anas.fishday.functions.lastorder.LastOrderPresenterImpl;
import com.anas.fishday.screens.main.activity.interfaces.MainInteractor;
import com.anas.fishday.screens.main.activity.interfaces.MainPresenter;
import com.anas.fishday.screens.main.activity.interfaces.OnMenuSelectedListener;
import com.anas.fishday.screens.main.activity.menu.MenuAdapter;
import com.anas.fishday.screens.main.fragments.about.AboutFragment;
import com.anas.fishday.screens.main.fragments.cart.CartFragment;
import com.anas.fishday.screens.main.fragments.contact.ContactFragment;
import com.anas.fishday.screens.main.fragments.home.HomeFragment;
import com.anas.fishday.screens.main.fragments.myorders.MyOrdersFragment;
import com.anas.fishday.screens.main.fragments.services.ServicesFragment;
import com.anas.fishday.screens.orderstatus.OrderStatusActivity;
import com.anas.fishday.screens.productdetails.ProductDetailsActivity;
import com.anas.fishday.screens.splash.SplashActivity;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;
import com.anas.fishday.utils.MenuUtil;

import java.util.List;

public class MainActivity extends FishDayActivity implements MainInteractor, CartGetInteractor, LastOrderInteractor, OnMenuSelectedListener,
        HomeFragment.OnFragmentAttachedListener, AboutFragment.OnFragmentAttachedListener, MyOrdersFragment.OnFragmentAttachedListener,
        ServicesFragment.OnFragmentAttachedListener, ContactFragment.OnFragmentAttachedListener, CartFragment.OnFragmentAttachedListener {

    private MainPresenter presenter;
    private CartGetPresenter cartGetPresenter;
    private LastOrderPresenter lastOrderPresenter;
    private ActivityMainBinding binding;
    private MenuAdapter menuAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public Toolbar toolbar;
    Boolean payment_status = false;
    Boolean cart_menu_click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
        cartGetPresenter = new CartGetPresenterImpl(this);
        lastOrderPresenter = new LastOrderPresenterImpl(this);
        initToolbar();
        initMenuItems();
        showHomeFragment();

        showProgressDialog();

        Uri uri = getIntent().getData();
        if(uri != null) {
            String uri_string = uri.toString();
            Log.d("333333333333", uri_string);

            if(uri_string.contains("appurwaycallback")) {
                payment_status = true;
                getLastOrder();
            } else {
                payment_status = false;
                String[] splitStr = uri_string.split("/");
                String product_id = splitStr[splitStr.length - 1];

                Bundle bundle = new Bundle();
                bundle.putString(Constant.PRODUCT_ID, String.valueOf(product_id));
                bundle.putInt("meterPercentage", FishDayStorage.getMeterPercentage());
                startActivity(ProductDetailsActivity.class, bundle);
            }
        } else {
            payment_status = false;

        }

    }

    private void getCart() {
        cartGetPresenter.getCart();
    }

    private void getLastOrder() {
        lastOrderPresenter.getLastOrder();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCart();
//        Uri uri = getIntent().getData();
//        if(uri != null) {
//            Log.d("11111111111", "this is url != nul part");
//            payment_status = true;
//            getCart();
//        } else {
//            Log.d("11111111111", "this is url == nul part");
//            payment_status = false;
//            getCart();
//        }
    }

    private void initToolbar() {
        toolbar = binding.appBarMain.toolbar.toolbar;
        toolbar.setTitle(R.string.home);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.appBarMain.toolbar.cartLayout.setOnClickListener(view -> {
            cart_menu_click = true;
            toolbar.setTitle(R.string.cart);
            replaceFragmentBackStack(CartFragment.newInstance());
        });
    }

    private void initMenuItems() {
        List<ItemMenu> itemMenuList = MenuUtil.getMenu(this);
        menuAdapter = new MenuAdapter(itemMenuList, this, this);
        layoutManager = new LinearLayoutManager(this);
        binding.menuRecyclerView.setLayoutManager(layoutManager);
        binding.menuRecyclerView.setAdapter(menuAdapter);
    }

    private void showHomeFragment() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        replaceFragment(homeFragment);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuSelected(View view, ItemMenu itemMenu) {
        binding.drawerLayout.closeDrawer(Gravity.START);
        switch (itemMenu.getPosition()) {
            case Constant.MENU_HOME:
                cart_menu_click = false;
                toolbar.setTitle(R.string.home);
                replaceFragment(HomeFragment.newInstance());
                break;
            case Constant.MENU_MY_ORDERS:
                cart_menu_click = false;
                toolbar.setTitle(R.string.my_orders);
                replaceFragmentBackStack(MyOrdersFragment.newInstance());
                break;
            case Constant.MENU_CART:
                cart_menu_click = true;
                getLastOrder();
                break;
            case Constant.MENU_SERVICES:
                cart_menu_click = false;
                toolbar.setTitle(R.string.our_services);
                replaceFragmentBackStack(ServicesFragment.newInstance());
                break;
            case Constant.MENU_ABOUT:
                cart_menu_click = false;
                toolbar.setTitle(R.string.about);
                replaceFragmentBackStack(AboutFragment.newInstance());
                break;
            case Constant.MENU_CONTACT:
                cart_menu_click = false;
                toolbar.setTitle(R.string.contact);
                replaceFragmentBackStack(ContactFragment.newInstance());
                break;
            case Constant.MENU_LANGUAGE:
                cart_menu_click = false;
                if (FishDayStorage.getAppLanguage().equals(Constant.LANGUAGE_EN)) {
                    updateLanguage(Constant.LANGUAGE_AR);
                } else {
                    updateLanguage(Constant.LANGUAGE_EN);
                }
                startActivity(SplashActivity.class);
                finishAffinity();
                break;
            case Constant.MENU_LOGOUT:
                cart_menu_click = false;
                logout();
//                FishDayStorage.clearData();
//                startActivity(SplashActivity.class, true);
                break;
            default:
                toolbar.setTitle(R.string.home);
                replaceFragment(HomeFragment.newInstance());
                break;
        }
    }

    private void logout() {
        presenter.logout();
    }

    @Override
    public void handleLogout(User user) {
        FishDayStorage.clearData();
        startActivity(SplashActivity.class, true);
    }

    @Override
    public void showErrorLogout(String message) {
        FishDayStorage.clearData();
        startActivity(SplashActivity.class, true);
    }

//    private void handleCartClick(Order order) {
//        if (FishDayStorage.getUser() != null) {
//            if (order == null) {
//                if (FishDayStorage.getOrder().getStatus() == Constant.ORDER_STATUS_CART) {
//                    toolbar.setTitle(R.string.cart);
//                    replaceFragmentBackStack(CartFragment.newInstance());
//                } else {
//                    startActivity(OrderStatusActivity.class);
//                }
//            } else {
//                // order equals null
//            }
//        } else {
//            startActivity(LoginActivity.class, true);
//        }
//    }

    @Override
    public void updateCartItems(Order order) {
        try {
            if(order.getOrderItems() != null) {
                binding.appBarMain.toolbar.numOfItemsTv.setText(String.valueOf(order.getOrderItems().size()));
            } else {
                binding.appBarMain.toolbar.numOfItemsTv.setText(String.valueOf(0));
            }
//            if(order.getStatus() == Constant.ORDER_STATUS_CART) {
//                binding.appBarMain.toolbar.numOfItemsTv.setText(String.valueOf(order.getOrderItems().size()));
//            } else {
//                binding.appBarMain.toolbar.numOfItemsTv.setText(String.valueOf(0));
//            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorOnGettingCart(String message) {
        cart_menu_click = false;
        dismissProgressDialog();
    }

    @Override
    public void handleGettingCart(Order order) {
        dismissProgressDialog();
        FishDayStorage.saveOrder(order);
        updateCartItems(order);
    }

    @Override
    public void showErrorOnGettingLastOrder(String message) {

    }

    @Override
    public void handleGettingLastOrder(Order order) {
        try {
//            binding.appBarMain.toolbar.numOfItemsTv.setText(String.valueOf(order.getOrderItems().size()));
//            if(payment_status && order.getStatus() != Constant.ORDER_STATUS_CART) {
//                payment_status = false;
//                startActivity(OrderStatusActivity.class);
//            } else if(payment_status && order.getStatus() == Constant.ORDER_STATUS_CART) {
//                payment_status = false;
//                Toast.makeText(this, R.string.payment_error, Toast.LENGTH_LONG).show();
//                replaceFragmentBackStack(CompleteOrderFragment.newInstance());
//            } else if(cart_menu_click) {
//                handleCartClick();
//                cart_menu_click = false;
//            }
            if(order == null) {
                toolbar.setTitle(R.string.cart);
                replaceFragmentBackStack(CartFragment.newInstance());
            } else {
                startActivity(OrderStatusActivity.class);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
