package com.example.bahary.robabikia.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bahary.robabikia.PostActivity;
import com.example.bahary.robabikia.R;
import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.utils.Constants;
import com.example.bahary.robabikia.utils.Helper;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.BindView;

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
        holder.Title.setText(N.getTitle());
        holder.Description.setText(N.getDescription() + "");
        Picasso.with(context).load(N.getUrlToImage()).centerCrop().placeholder(R.drawable.bg_spinner).fit().into(holder.Img);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]strings={N.getTitle(),N.getDescription(),N.getUrl(),N.getPublishedAt(),N.getUrlToImage()};
                Intent intent = new Intent(context, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray(Constants.mBundlearticle, strings);
                intent.putExtras(bundle);
                context.startActivity(intent);
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
