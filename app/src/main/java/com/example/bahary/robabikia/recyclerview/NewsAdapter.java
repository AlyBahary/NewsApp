package com.example.bahary.robabikia.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bahary.robabikia.EmptyActivty;
import com.example.bahary.robabikia.R;
import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pc on 2/21/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Articles> news;


    public NewsAdapter(Context context, ArrayList<Articles> newslist) {

        this.context = context;
        this.news = newslist;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {

        final Articles N = news.get(position);
        if (N.getTitle() == null) {
            holder.Title.setVisibility(View.GONE);
        } else {

            holder.Title.setText(N.getTitle());
        }
        if (N.getDescription() == null) {
            holder.Description.setVisibility(View.GONE);
        } else {
            holder.Description.setText(N.getDescription() + "");
        }
        if (N.getUrlToImage() == null||N.getUrlToImage().equals("")) {
            holder.Img.setVisibility(View.GONE);
        } else {
            Picasso.with(context).load(N.getUrlToImage()).fit().placeholder(R.drawable.bg_spinner).fit().into(holder.Img);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {N.getTitle(), N.getDescription(), N.getUrl(), N.getPublishedAt(), N.getUrlToImage()};
                Bundle bundle = new Bundle();
                bundle.putStringArray(Constants.mBundlearticle, strings);

                //context.startActivity(intent);
                Intent intent1 = new Intent(context, EmptyActivty.class);
                intent1.putExtras(bundle);
                context.startActivity(intent1);
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.emptyContainer, myFragment).addToBackStack(null).commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Description;
        ImageView Img;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.title);
            Description = (TextView) itemView.findViewById(R.id.description);
            Img = itemView.findViewById(R.id.postimageView);
            item = itemView.findViewById(R.id.blaaaaa);
        }
    }
}
