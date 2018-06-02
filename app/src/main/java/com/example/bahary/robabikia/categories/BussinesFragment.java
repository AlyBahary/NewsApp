package com.example.bahary.robabikia.categories;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
public class BussinesFragment extends Fragment implements Connectors.ErrorCallback, Connectors.LoadCallback {
    public NewsAdapter newsAdapter;
    public ArrayList<Articles> mArticlesArrayList;


    public BussinesFragment() {
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
        connectors.Connection(1);
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
