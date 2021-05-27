package com.anas.fishday.screens.main.fragments.home.adapters;

import android.content.Context;
//import android.databinding.BindingAdapter;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.entities.Image;
import com.anas.fishday.entities.Product;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnProductClickListener;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;
import com.anas.fishday.utils.ImageDownloader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private Context context;
    private List<Product> productList = new ArrayList<>();
    private OnProductClickListener onProductClickListener;
    private static ImageDownloader imageDownloader;

    public ProductsAdapter(Context context, ImageDownloader downloader, OnProductClickListener listener) {
        this.context = context;
        this.imageDownloader = downloader;
        this.onProductClickListener = listener;
        productList.clear();
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding view = DataBindingUtil
                .inflate(layoutInflater, R.layout.item_product, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product.getQuantity().equals("kilo")) {
            holder.kiloLayout.setVisibility(View.VISIBLE);
            holder.pieceLayout.setVisibility(View.GONE);
            if(product.getPromotionKiloPrice() != null && Float.parseFloat(product.getPromotionKiloPrice()) != 0) {
                 product.setReal_Kiloprice(String.valueOf(Float.parseFloat(product.getKiloPrice()) - Float.parseFloat(product.getPromotionKiloPrice())));
                 product.setOrigin_Kiloprice(product.getKiloPrice());
                 holder.productKiloCancelPriceTv.setVisibility(View.VISIBLE);
                 holder.productKiloCancelPriceTv.setPaintFlags(holder.productKiloCancelPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                product.setReal_Kiloprice(product.getKiloPrice());
                holder.productKiloCancelPriceTv.setVisibility(View.GONE);
            }
        } else if(product.getQuantity().equals("piece")) {
            holder.kiloLayout.setVisibility(View.GONE);
            holder.pieceLayout.setVisibility(View.VISIBLE);
            if(product.getPromotionPiecePrice() != null && Float.parseFloat(product.getPromotionPiecePrice()) != 0) {
                product.setReal_Pieceprice(String.valueOf(Float.parseFloat(product.getPiecePrice()) - Float.parseFloat(product.getPromotionPiecePrice())));
                product.setOrigin_Pieceprice(product.getPiecePrice());
                holder.productPieceCancelPriceTv.setVisibility(View.VISIBLE);
                holder.productPieceCancelPriceTv.setPaintFlags(holder.productPieceCancelPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                product.setReal_Pieceprice(product.getPiecePrice());
                holder.productPieceCancelPriceTv.setVisibility(View.GONE);
            }
        } else {
            holder.kiloLayout.setVisibility(View.VISIBLE);
            holder.pieceLayout.setVisibility(View.VISIBLE);
            if(Float.parseFloat(product.getKiloPrice()) == 0) {
                holder.kiloLayout.setVisibility(View.GONE);
            } else {
                if(product.getPromotionKiloPrice() != null && Float.parseFloat(product.getPromotionKiloPrice()) != 0) {
                    product.setReal_Kiloprice(String.valueOf(Float.parseFloat(product.getKiloPrice()) - Float.parseFloat(product.getPromotionKiloPrice())));
                    product.setOrigin_Kiloprice(product.getKiloPrice());
                    holder.productKiloCancelPriceTv.setVisibility(View.VISIBLE);
                    holder.productKiloCancelPriceTv.setPaintFlags(holder.productKiloCancelPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    product.setReal_Kiloprice(product.getKiloPrice());
                    holder.productKiloCancelPriceTv.setVisibility(View.GONE);
                }
            }
            if(Float.parseFloat(product.getPiecePrice()) == 0) {
                holder.pieceLayout.setVisibility(View.GONE);
            } else {
                if(product.getPromotionPiecePrice() != null && Float.parseFloat(product.getPromotionPiecePrice()) != 0) {
                    product.setReal_Pieceprice(String.valueOf(Float.parseFloat(product.getPiecePrice()) - Float.parseFloat(product.getPromotionPiecePrice())));
                    product.setOrigin_Pieceprice(product.getPiecePrice());
                    holder.productPieceCancelPriceTv.setVisibility(View.VISIBLE);
                    holder.productPieceCancelPriceTv.setPaintFlags(holder.productPieceCancelPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    product.setReal_Pieceprice(product.getPiecePrice());
                    holder.productPieceCancelPriceTv.setVisibility(View.GONE);
                }
            }
        }
        if(FishDayStorage.getAppLanguage().equals(Constant.LANGUAGE_EN)) {
            holder.soldoutIv.setImageResource(R.drawable.soldout_en);
        } else {
            holder.soldoutIv.setImageResource(R.drawable.soldout_ar);
        }
        if(product.getQuantity_count() > 0) {
            holder.soldoutIv.setVisibility(View.GONE);
        } else {
            holder.soldoutIv.setVisibility(View.VISIBLE);
        }
        holder.bind(product, onProductClickListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    @BindingAdapter("loadProductImage")
    public static void loadProductImage(ImageView imageView, List<Image> images) {
        if (images != null && images.size() > 0) {

            imageDownloader.loadImage(images.get(0).getSmall(), imageView);
        }
    }



    class ProductsViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;
        public LinearLayout kiloLayout, pieceLayout;
        public TextView productKiloCancelPriceTv, productPieceCancelPriceTv;
        public ImageView soldoutIv;

        public ProductsViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.viewDataBinding = dataBinding;
            kiloLayout = (LinearLayout) dataBinding.getRoot().findViewById(R.id.kiloLayout);
            pieceLayout = (LinearLayout) dataBinding.getRoot().findViewById(R.id.pieceLayout);
            soldoutIv = (ImageView) dataBinding.getRoot().findViewById(R.id.soldoutIv);
            productKiloCancelPriceTv = (TextView) dataBinding.getRoot().findViewById(R.id.productKiloCancelPriceTv);
            productPieceCancelPriceTv = (TextView) dataBinding.getRoot().findViewById(R.id.productPieceCancelPriceTv);
        }

        public void bind(Object obj, OnProductClickListener onProductClickListener) {
            viewDataBinding.setVariable(BR.product, obj);
            viewDataBinding.setVariable(BR.onProductClickListener, onProductClickListener);
        }

    }

}