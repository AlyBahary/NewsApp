package com.example.bahary.robabikia.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.model.MainModel;
import com.google.gson.Gson;

import java.net.InetAddress;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Connectors {


    private LoadCallback mLoadCallback;
    private ErrorCallback mErrorCallback;

    public interface LoadCallback {

        void onComplete(Response<MainModel> response);

    }


    public interface ErrorCallback {

        void onError(Throwable error);

    }
    public Connectors(LoadCallback mLoadCallback, ErrorCallback mErrorCallback) {
        this.mLoadCallback = mLoadCallback;
        this.mErrorCallback = mErrorCallback;
    }

    public interface getNewsServices {
        String BaseURL = Constants.mBase_Url;

        @GET(Constants.mCategory_Sports)
        Call<MainModel> getPosts();

        @GET(Constants.mCategory_bussines)
        Call<MainModel> getPostsBussines();

        @GET(Constants.mCategory_technology)
        Call<MainModel> getPostsTechnology();
    }

    public void Connection(int flag) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Connectors.getNewsServices.BaseURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        Connectors.getNewsServices allPostsServices = retrofit.create(Connectors.getNewsServices.class);
        if (flag == 0) {
            allPostsServices.getPosts().enqueue(new Callback<MainModel>() {
                @Override
                public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                    mLoadCallback.onComplete(response);
                }


                @Override
                public void onFailure(Call<MainModel> call, Throwable t) {
                mErrorCallback.onError(t);
                }
            });


        }
        else if (flag == 1) {
            allPostsServices.getPostsBussines().enqueue(new Callback<MainModel>() {
                @Override
                public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                    mLoadCallback.onComplete(response);

                }

                @Override
                public void onFailure(Call<MainModel> call, Throwable t) {
                    mErrorCallback.onError(t);

                }
            });
        }
        else if (flag == 2) {
            allPostsServices.getPostsTechnology().enqueue(new Callback<MainModel>() {
                @Override
                public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                    mLoadCallback.onComplete(response);

                }

                @Override
                public void onFailure(Call<MainModel> call, Throwable t) {
                    mErrorCallback.onError(t);

                }
            });
        }
    }
public static ArrayList<Articles> list(Response<MainModel> response, Context context){
    ArrayList<Articles> test = (ArrayList<Articles>) response.body().getArticles();
    ArrayList<Articles> mArticlesArrylist = new ArrayList<>();
    if (response.body().getArticles() == null) {
        Toast.makeText(context, "there is nothing to show", Toast.LENGTH_SHORT).show();
    } else {
        for (int i = response.body().getArticles().size() - 1; i >= 0; i--) {

            mArticlesArrylist.add(test.get(i));
        }

    }
    return  mArticlesArrylist;
}

}
