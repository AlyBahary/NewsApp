package com.example.bahary.robabikia.categories;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bahary.robabikia.R;
import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.model.MainModel;
import com.example.bahary.robabikia.recyclerview.NewsAdapter;
import com.example.bahary.robabikia.utils.Connectors;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportsFragment extends Fragment implements Connectors.ErrorCallback, Connectors.LoadCallback {
    public NewsAdapter newsAdapter;
    public ArrayList<Articles> mArticlesArrayList;
    public SportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recyclerviewnews, container, false);

        mArticlesArrayList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        newsAdapter = new NewsAdapter(this.getActivity(), mArticlesArrayList);

        recyclerView.setAdapter(newsAdapter);

        Connectors connectors = new Connectors(this, this);
        connectors.Connection(0);

       return view;
    }

    @Override
    public void onError(Throwable error) {

    }

    @Override
    public void onComplete(Response<MainModel> response) {
        mArticlesArrayList.addAll(Connectors.list(response,getContext()));
        newsAdapter.notifyDataSetChanged();
    }
}
  /*   Retrofit retrofit = new Retrofit.Builder().baseUrl(Connectors.getNewsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        Connectors.getNewsServices allPostsServices = retrofit.create(Connectors.getNewsServices.class);
        allPostsServices.getPosts().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                Toast.makeText(getContext(), "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                ArrayList<Articles> test = (ArrayList<Articles>) response.body().getArticles();
                if (response.body().getArticles() == null) {
                    Toast.makeText(getContext(), "there is nothing to show", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = response.body().getArticles().size() - 1; i >= 0; i--) {

                        mArticlesArrayList.add(test.get(i));
                        newsAdapter.notifyDataSetChanged();
                    }
                }
            }


            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
     */   //////////////////////
