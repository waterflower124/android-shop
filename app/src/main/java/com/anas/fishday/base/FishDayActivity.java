package com.anas.fishday.base;

import android.app.Dialog;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import android.support.design.widget.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.fishday.R;
import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.storage.FishDayStorage;

import java.util.Locale;

/**
 * Created by Anas on 2/15/2018.
 */

public class FishDayActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.loading));
    }

    public void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivity(Class cls, boolean finish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (finish)
            finish();
    }

    public void startActivityFinishAll(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finishAffinity();
    }

    public void startActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void startActivity(Class cls, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
        if (finish)
            finish();
    }

    protected Bundle getBundle() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getBundleExtra("bundle");
            if (bundle != null) {
                return bundle;
            }
        }
        return null;
    }

    public void replaceFragment(FishDayFragment baseFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, baseFragment);
        fragmentTransaction.commit();
    }

    public void replaceFragmentBackStack(FishDayFragment baseFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, baseFragment).addToBackStack("");
        fragmentTransaction.commit();
    }

    public void showSnackBar(CoordinatorLayout coordinatorLayout, String message) {
//        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
        showDialog(message);
    }

    public void showSnackBar(CoordinatorLayout coordinatorLayout, int resId) {
//        Snackbar.make(coordinatorLayout, resId, Snackbar.LENGTH_SHORT).show();
        showDialog(getString(resId));
    }

    public void showDialog(String msg){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView text = dialog.findViewById(R.id.messageTv);
        text.setText(msg);

        Button dialogButton = dialog.findViewById(R.id.okBtn);
        dialogButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage(getString(R.string.loading));
        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void showProgressDialog(int resId) {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage(getString(resId));
//        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    protected void updateLanguage(String language){
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language.toLowerCase());
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);

        FishDayStorage.setAppLanguage(language);
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
}
