package com.example.bahary.robabikia;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bahary.robabikia.R;
import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.model.MainModel;
import com.example.bahary.robabikia.recyclerview.NewsAdapter;
import com.example.bahary.robabikia.utils.Connectors;
import com.example.bahary.robabikia.utils.Constants;
import com.example.bahary.robabikia.utils.Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import java.net.CookieHandler;
import java.util.ArrayList;

public class FavouritsActivity extends Activity {
    public NewsAdapter newsAdapter;
    public ArrayList<Articles> mArticlesArrayList, mArrayfromFB;
    private FirebaseAuth mAuth;
    private String user_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewnews);
        Hawk.init(this).build();
        mArticlesArrayList = new ArrayList<Articles>();
        mArrayfromFB = new ArrayList<Articles>();
        /*mArticlesArrayList = new ArrayList<>();
        mArrayfromFB = new ArrayList<>();*/
        mArticlesArrayList = Hawk.get(Constants.mFavourit);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        newsAdapter = new NewsAdapter(this, mArrayfromFB);
        recyclerView.setAdapter(newsAdapter);
        Helper.printLog("loginState",Hawk.get(Constants.loginflag)+""+Hawk.get(Constants.UserID));

        if ((Hawk.get(Constants.loginflag)+"").trim().equals("1")) {

                user_id = Hawk.get(Constants.UserID);
                Helper.printLog("UseID","In");

            ////////////////////////////////
            ////////////////////////////////
            ////////////////////////////////
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            try {
                final DatabaseReference myRef = database.getReference(user_id).child("post");
                // myRef.orderByChild("")

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                            Log.i("tmz", "" + chidSnap.toString());//gives the value for given keyname
                            String key = chidSnap.getKey();
                            Articles x = new Articles();
                            x.setDescription(chidSnap.child("description").getValue() + "");
                            x.setPublishedAt(chidSnap.child("publishedAt").getValue() + "");
                            x.setTitle(chidSnap.child("title").getValue() + "");
                            x.setUrlToImage(chidSnap.child("urlToImage").getValue() + "");
                            mArrayfromFB.add(x);
                            newsAdapter.notifyDataSetChanged();
                            //Log.i("FireBase", "" + x.getTitle());//gives the value for given keyname
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("tttt", "" + databaseError.toString());//gives the value for given keyname


                    }
                });
            } catch (Exception e) {

            }

            if (((mArticlesArrayList) == null) && ((mArrayfromFB) == null)) {
                Toast.makeText(this, "there is no thing to show", Toast.LENGTH_SHORT).show();
            } else {
                newsAdapter.notifyDataSetChanged();

            }
            newsAdapter.notifyDataSetChanged();
            Log.i("00000", "null");

            if (mArrayfromFB == null) {
                Log.i("11111", "null");
            }
        }
        else{

            if (mArticlesArrayList == null) {
                Helper.printText(getApplicationContext(), "There is no thing to Show");
            } else {
                Log.i("33333", "null");

                for (int i = 0; i < mArticlesArrayList.size(); i++) {
                    Log.i("DB", mArticlesArrayList.get(i).getAuthor() + mArticlesArrayList.get(i).getTitle());
                    if (mArticlesArrayList.get(i).getAuthor() != (Hawk.get(Constants.mEmail_Key))) {
                        mArticlesArrayList.remove(i);
                    }
                }
                mArrayfromFB.addAll(mArticlesArrayList);
                newsAdapter.notifyDataSetChanged();
            }
        }
    }

}