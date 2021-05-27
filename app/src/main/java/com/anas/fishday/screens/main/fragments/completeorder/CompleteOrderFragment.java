package com.anas.fishday.screens.main.fragments.completeorder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentCompleteOrderBinding;
import com.anas.fishday.entities.AddressResponse;
import com.anas.fishday.entities.GeoAddress;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.cartactivity.CartActivity;
import com.anas.fishday.screens.confirmregister.ConfirmationActivity;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.main.activity.interfaces.MainInteractor;
import com.anas.fishday.screens.main.fragments.completeorder.interfaces.CompleteOrderInteractor;
import com.anas.fishday.screens.main.fragments.completeorder.interfaces.CompleteOrderPresenter;
import com.anas.fishday.screens.orderstatus.OrderStatusActivity;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.system.SystemPermissions;
import com.anas.fishday.utils.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.Gson;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

public class CompleteOrderFragment extends FishDayFragment implements View.OnClickListener, CompleteOrderInteractor {

    private static final String LOCATION_PERMISSION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final int PLACE_PICKER_REQUEST = 1;

    private FishDayActivity activity;
    private FragmentCompleteOrderBinding binding;
    private CompleteOrderPresenter presenter;
    private SystemPermissions permissions;
    private String address;
    private String mobileNumber;
    private String notes;
    private double latitude;
    private double longitude;
    private boolean isRequestAddress = false;


    private Integer payment_visa = 1;
    RadioGroup paymentRadioGroup;

    CompositeDisposable compositeDisposable;

    public CompleteOrderFragment() {
        // Required empty public constructor
    }

    public static CompleteOrderFragment newInstance() {
        CompleteOrderFragment fragment = new CompleteOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (FishDayActivity) getActivity();
        permissions = new SystemPermissions(activity);
        presenter = new CompleteOrderPresenterImpl(this);
        compositeDisposable = new CompositeDisposable();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_order, container, false);
        binding.setVariable(BR.onClickListener, this);
        initToolbar();
        setUserData();
        fetchAddress();

        return binding.getRoot();
    }

    private void setUserData() {
        if (FishDayStorage.getUser() != null) {
            String mobileNumber = FishDayStorage.getUser().getMobileNumber();
            if (mobileNumber != null && !mobileNumber.isEmpty()) {
                this.mobileNumber = mobileNumber;
                binding.mobileNumberEt.setText(mobileNumber);
            }
        }
    }

    private void initToolbar() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).toolbar.setTitle(R.string.complete_order);
        else
            ((CartActivity) getActivity()).toolbar.setTitle(R.string.complete_order);
    }

    private void fetchAddress() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean permitted = permissions.checkPermission(LOCATION_PERMISSION);
            if (permitted) {
                getCurrentPlaceLocation();
            } else {
                requestPermissions(new String[]{LOCATION_PERMISSION},
                        SystemPermissions.LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            getCurrentPlaceLocation();
        }
    }

    private void getCurrentAddress() {
        activity.showProgressDialog(R.string.fetching_current_address);
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            activity.showToast(R.string.error_gps_disabled);
            activity.dismissProgressDialog();
        } else {
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            activity.dismissProgressDialog();
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            if (!isRequestAddress) {
                                getAddressFromGeoCoderAPI();
                            }
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
//                        activity.dismissProgressDialog();
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
//                        activity.dismissProgressDialog();
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
//                        activity.dismissProgressDialog();
                    }
                });

            } catch (SecurityException e) {
//                activity.dismissProgressDialog();
            }
        }


    }

    private void getAddressFromGeoCoderAPI() {
        presenter.getAddress(latitude, longitude);
    }

    @Override
    public void showErrorForGettingAddress() {
        activity.dismissProgressDialog();
        activity.showSnackBar(binding.completeOrderCoordinator, R.string.error_failed_to_get_address);
    }

    @Override
    public void showAddress(AddressResponse addressResponse) {
        GeoAddress geoAddress = addressResponse.getGeoAddressList().get(0);
        String add = geoAddress.getFormattedAddress();
        binding.addressEt.setText(add);
        isRequestAddress = true;
        activity.dismissProgressDialog();
    }

    @Override
    public void showErrorForCompletingOrder(String message) {
        activity.dismissProgressDialog();
        activity.showSnackBar(binding.completeOrderCoordinator, message);
    }

    @Override
    public void goToOrderStatus(ResponseEntity<Order> responseEntity) {
        activity.dismissProgressDialog();
        FishDayStorage.saveOrder(responseEntity.getEntity());
        activity.startActivity(OrderStatusActivity.class, true);
//        activity.showSnackBar(binding.completeOrderCoordinator, responseEntity.getMessage());
    }

    @Override
    public void openPaymentLink(ResponseEntity<Order> responseEntity) {
        String url = responseEntity.getTarget_url();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void goToConfirmationScreen(ResponseEntity<Order> responseEntity) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.IS_GUEST, true);

        bundle.putString(Constant.MOBILE_NUMBER, binding.mobileNumberEt.getText().toString());
        bundle.putInt("payment_visa", payment_visa);
        bundle.putString("target_url", responseEntity.getTarget_url());
        activity.startActivity(ConfirmationActivity.class, bundle);
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.completeOrderBtn:
                completeOrder();
                break;
            case R.id.getCurrentAddressBtn:
                fetchAddress();
                break;
            case R.id.visaRadioButton:
                payment_visa = 1;
                break;
            case R.id.cashdeliveryRadioButton:
                payment_visa = 0;
                break;
        }
    }



    private void completeOrder() {
        String number = binding.mobileNumberEt.getText().toString();

        if (number.startsWith("0") || number.startsWith("+")) {
            number = number.substring(1);
        }

        address = binding.addressEt.getText().toString();
        notes = binding.notesEt.getText().toString();
        String fullName = binding.nameEt.getText().toString();
        if (!fullName.isEmpty() && number != null && !number.isEmpty() && address != null && !address.isEmpty()) {
            activity.showProgressDialog();
            int orderId = FishDayStorage.getOrder().getId();
            OrderNew orderNew = FishDayStorage.getOrderNew();
            orderNew.setUserFullName(fullName);
            orderNew.setUserPhoneNumber(number);
            orderNew.setAddress(address);
            orderNew.setAddressLat(latitude);
            orderNew.setAddressLon(longitude);
            if(payment_visa == 1) {
                orderNew.setPayment_method("mada");
            } else {
                orderNew.setPayment_method("cash");
            }
            if (notes != null) {
                orderNew.setNotes(notes);
            }
            FishDayStorage.saveOrderNew(orderNew);
            presenter.completeOrder(orderNew);
        } else {
            activity.showSnackBar(binding.completeOrderCoordinator, R.string.error_mobile_address_required);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SystemPermissions.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentPlaceLocation();
            } else {
                activity.showSnackBar(binding.completeOrderCoordinator, "Access denied");
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                updatePlace(data);
//            }
//        }
        if ((requestCode == PLACE_PICKER_REQUEST) && (resultCode == RESULT_OK)) {
            Place place = PingPlacePicker.getPlace(data);
            if (place != null) {
                updatePlace(place);
//                Toast.makeText(getContext(), "You selected the place: " + ((Place) place).getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void updatePlace(Intent data) {
//        Place place = PlacePicker.getPlace(activity, data);
//        address = place.getAddress().toString();
//        latitude = place.getLatLng().latitude;
//        longitude = place.getLatLng().longitude;
//        binding.addressEt.setText(place.getAddress());
//    }

    private void updatePlace(Place place) {
        address = place.getAddress().toString();
        latitude = place.getLatLng().latitude;
        longitude = place.getLatLng().longitude;
        binding.addressEt.setText(place.getAddress());
    }

    public void getCurrentPlaceLocation() {
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        try {
//            startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
        builder.setAndroidApiKey("AIzaSyBOSF44W1Z42oyLc0yq5Z_cRA7HkBL2XnY")
                .setShouldReturnActualLatLng(true)
                .setMapsApiKey("AIzaSyBOSF44W1Z42oyLc0yq5Z_cRA7HkBL2XnY");
        // If you want to set a initial location rather then the current device location.
        // NOTE: enable_nearby_search MUST be true.
        // builder.setLatLng(new LatLng(37.4219999, -122.0862462))

        try {
            Intent placeIntent = builder.build(getActivity());
            startActivityForResult(placeIntent, PLACE_PICKER_REQUEST);
        }
        catch (Exception ex) {
            // Google Play services is not available...
        }
    }

}
