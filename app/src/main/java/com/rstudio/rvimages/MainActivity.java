package com.rstudio.rvimages;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.rstudio.rvimages.adapters.ImagesAdapter;
import com.rstudio.rvimages.utils.SimpleItemTouchHelperCallback;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICTURES_AMOUNT = 20;
    private static final String TAG = "MainActivity";
    private ImagesAdapter mImagesAdapter;
    private RecyclerView mRecyclerView;
    private List<String> mLinks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinks = new ArrayList<String>();
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636585987.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577423.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577444.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577414.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577360.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577390.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577335.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_14663657738.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577328.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146636577338.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146615196221.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146585083491.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_14658508221.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146585074597.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146585074520.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_14658507459.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146585074560.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146585074559.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146567864231.jpg");
        mLinks.add("http://www.anekdotov-mnogo.ru/image-prikol//smeshnie_kartinki_146547791598.jpg");


        mRecyclerView = (RecyclerView) findViewById(R.id.image_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mImagesAdapter = new ImagesAdapter(mLinks, getApplicationContext(), new ImagesAdapter.ImagesViewHolder.CustomClickListener() {
            @Override
            public void onImageItemClickListener(int position) {

            }
        });
        mRecyclerView.setAdapter(mImagesAdapter);

        //region ====== attached for swipes and drags
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mImagesAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        //endregion

    }



}
