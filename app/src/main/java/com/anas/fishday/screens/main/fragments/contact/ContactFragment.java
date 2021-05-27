package com.anas.fishday.screens.main.fragments.contact;


import android.content.Context;
import android.content.Intent;
//import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentContactBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.storage.FishDayStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends FishDayFragment implements View.OnClickListener, CartGetInteractor {


    private FragmentContactBinding binding;
    private FishDayActivity activity;

    private OnFragmentAttachedListener onFragmentAttachedListener;
    private CartGetPresenter presenter;

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        ContactFragment contactFragment = new ContactFragment();
        return contactFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (FishDayActivity) getActivity();
        presenter = new CartGetPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        binding.setVariable(BR.onClickListener, this);
        initToolbar();
        presenter.getCart();
        return binding.getRoot();
    }

    private void initToolbar() {
        ((MainActivity) getActivity()).toolbar.setTitle(R.string.contact);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contactWhatsApp:
                Uri uri = Uri.parse("whatsapp://send?phone=+966" + getString(R.string.contact_mobile));
                Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW, uri);

                if (whatsAppIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(whatsAppIntent);
                else
                    activity.showSnackBar(binding.contactCoordinator, "No WhatsApp");
                break;

            case R.id.contactFacebook:
                Uri facebookUri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/يوم-السمك-515788785460014");
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, facebookUri);

                if (facebookIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(facebookIntent);
                else
                    activity.showSnackBar(binding.contactCoordinator, "No Facebook");
                break;
            case R.id.contactInstagram:
                Uri instagramUri = Uri.parse("https://www.instagram.com/fishday_ksa/");
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW, instagramUri);
                startActivity(instagramIntent);
                break;
            case R.id.contactSnap:
                Uri snapUri = Uri.parse("https://www.snapchat.com/add/fishday_ksa");
                Intent snapIntent = new Intent(Intent.ACTION_VIEW, snapUri);
                startActivity(snapIntent);
                break;
            case R.id.contactTwitter:
                Uri twitterUri = Uri.parse("https://twitter.com/fishday_ksa");
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, twitterUri);
                startActivity(twitterIntent);
                break;
            case R.id.telTv:
                Uri telUri = Uri.parse("tel:00966" + binding.telTv.getText());
                Intent telIntent = new Intent(Intent.ACTION_DIAL, telUri);
                startActivity(telIntent);
                break;
            case R.id.mobileNumberTv:
                Uri mobileUri = Uri.parse("tel:00966" + binding.mobileNumberTv.getText()
                        .toString().substring(1));
                Intent mobileIntent = new Intent(Intent.ACTION_DIAL, mobileUri);
                startActivity(mobileIntent);
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

    @Override
    public void showErrorOnGettingCart(String message) {

    }

    @Override
    public void handleGettingCart(Order order) {
        FishDayStorage.saveOrder(order);
        onFragmentAttachedListener.updateCartItems(order);
    }

    public interface OnFragmentAttachedListener {
        void updateCartItems(Order order);
    }
}
