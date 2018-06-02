package com.example.bahary.robabikia;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.model.Source;
import com.example.bahary.robabikia.utils.Constants;
import com.example.bahary.robabikia.utils.Helper;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends Activity {
    ArrayList<Articles> articlesArrayList = new ArrayList<>();

    @BindView(R.id.individualtitle)
    TextView intitle;
    @BindView(R.id.individualdescription)
    TextView indescription;
    @BindView(R.id.individualimage)
    ImageView inimage;
    @BindView(R.id.individualstar)
    ImageView instar;
    @BindView(R.id.individualdate1)
    TextView indate1;
    @BindView(R.id.individualdate2)
    TextView indate2;
    String[] articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        Hawk.init(this).build();
        Bundle bundle = getIntent().getExtras();
        articles = bundle.getStringArray(Constants.mBundlearticle);
        String[] Date = articles[3].split("T");
        intitle.setText(articles[0] + "");
        indescription.setText(articles[1] + "");
        indate2.setText(Date[0] + "");
        indate1.setText(Date[1] + "");
        Picasso.with(this).load(articles[4]).fit().placeholder(R.drawable.bg_spinner).into(inimage);
/////////////////////////////////
        if (Hawk.contains(Constants.mFavourit)) {
            articlesArrayList = Hawk.get(Constants.mFavourit);
            for(int i=0;i<articlesArrayList.size();i++){
                if((articlesArrayList.get(i).getTitle()).equals(articles[0])){
                    instar.setVisibility(View.GONE);
                }
            }
//////////////////////////////////
        }
        instar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!Hawk.contains(Constants.mFavourit))) {
                    Articles article = new Articles();
                    article.setTitle(articles[0]);
                    article.setDescription(articles[1]);
                    article.setPublishedAt(articles[3]);
                    article.setUrlToImage(articles[4]);
                    articlesArrayList.add(article);
                    Hawk.put(Constants.mFavourit, articlesArrayList);
                } else {
                    articlesArrayList = Hawk.get(Constants.mFavourit);
                    Articles article = new Articles();
                    article.setTitle(articles[0]);
                    article.setDescription(articles[1]);
                    article.setPublishedAt(articles[3]);
                    article.setUrlToImage(articles[4]);
                    articlesArrayList.add(article);
                    Hawk.put(Constants.mFavourit, articlesArrayList);

                }
                Toast.makeText(PostActivity.this, "it's Added to your favourit list ", Toast.LENGTH_SHORT).show();

                instar.setVisibility(View.GONE);
            }
        });


    }
}
