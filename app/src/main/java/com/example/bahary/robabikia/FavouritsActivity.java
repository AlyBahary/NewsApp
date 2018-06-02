package com.example.bahary.robabikia;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bahary.robabikia.R;
import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.recyclerview.NewsAdapter;
import com.example.bahary.robabikia.utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class FavouritsActivity extends Activity {
    public NewsAdapter newsAdapter;
    public ArrayList<Articles> mArticlesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewnews);
        Hawk.init(this).build();
        mArticlesArrayList = new ArrayList<>();
        mArticlesArrayList = Hawk.get(Constants.mFavourit);
        if ((mArticlesArrayList).equals(null)) {
            Toast.makeText(this, "there is no thing to show", Toast.LENGTH_SHORT).show();
        } else {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RV);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
            newsAdapter = new NewsAdapter(this, mArticlesArrayList);
            recyclerView.setAdapter(newsAdapter);
        }
    }
}
