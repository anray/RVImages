package com.rstudio.rvimages.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.rstudio.rvimages.R;
import com.rstudio.rvimages.utils.ItemTouchHelperAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by anray on 10.08.2016.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> implements ItemTouchHelperAdapter {


    private static final String TAG = "ImagesAdapter";

    private List<String> mImages;
    private Context mContext;
    private ImagesViewHolder.CustomClickListener mCustomClickListener;
    private int mCount = 0;

    private Toast mToast;

    private ArrayList<String> mLoadedImages = new ArrayList<String>();


    public ImagesAdapter(List<String> images, Context context, ImagesViewHolder.CustomClickListener customClickListener) {
        mImages = images;
        this.mCustomClickListener = customClickListener;
        this.mContext = context;
    }

    @Override
    public ImagesAdapter.ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);

        return new ImagesViewHolder(convertView, mCustomClickListener);
    }

    @Override
    public void onBindViewHolder(final ImagesAdapter.ImagesViewHolder holder, int position) {


        final String imageUrl = mImages.get(position);


        Picasso
                .with(mContext)
                .load(imageUrl)
                .error(holder.mDummy)
                .placeholder(holder.mDummy)
                //.resize(768, 512)
                .fit()
                //.centerCrop()
                //.networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "user profile photo load from cache successful");

                        showNotificationLoadingFinished(imageUrl);

                    }

                    @Override
                    public void onError() {

                    }
                });


    }

    private void showNotificationLoadingFinished(String imageUrl) {

        //checks if such URL was already loaded or not
        if (!mLoadedImages.contains(imageUrl)) {
            mLoadedImages.add(imageUrl);
            mCount += 1;

            //show toast for just loaded image
            if (mToast == null) {
                mToast = Toast.makeText(mContext, mContext.getString(R.string.loaded_image_notification) + mCount, Toast.LENGTH_SHORT);
            }
            mToast.setText(mContext.getString(R.string.loaded_image_notification) + mCount);
                    mToast.show();

            //show alert when everything is loaded
            if (mCount == mImages.size()) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setMessage(mContext.getString(R.string.loaded_all_images_notification));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }

    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected Drawable mDummy;
        protected ImageView mImageView;

        private CustomClickListener mListener;


        public ImagesViewHolder(View itemView, CustomClickListener customClickListener) {
            super(itemView);
            this.mListener = customClickListener;

            mImageView = (ImageView) itemView.findViewById(R.id.image_iv);
            mDummy = mImageView.getContext().getResources().getDrawable(R.drawable.user_bg);
            mImageView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                //click logic
            }
        }

        public interface CustomClickListener {

            void onImageItemClickListener(int position);

        }

    }


    //region for swipes and drags


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mImages, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mImages, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        //return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mImages.remove(position);
        notifyItemRemoved(position);
    }

    //endregion
}

