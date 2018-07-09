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
    private int FlagFireBase;


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
        String user_id = mAuth.getCurrentUser().getUid();

        ////////////////////////////////
        ////////////////////////////////
        ////////////////////////////////
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final Articles articlesfromFB = new Articles();

        final DatabaseReference myRef = database.getReference(user_id).child("post");
        // myRef.orderByChild("")

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    Log.i("tmz", "" + chidSnap);//gives the value for given keyname
                    String key = chidSnap.getKey();

                    Articles x = new Articles();
                    x.setDescription(chidSnap.child("description").getValue() + "");
                    x.setPublishedAt(chidSnap.child("publishedAt").getValue() + "");
                    x.setTitle(chidSnap.child("title").getValue() + "");
                    x.setUrlToImage(chidSnap.child("urlToImage").getValue() + "");
                    mArrayfromFB.add(x);
                    //Log.i("FireBase", "" + x.getTitle());//gives the value for given keyname

                }

                Helper.printText(getApplicationContext(), "Online Data");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Helper.printText(getApplicationContext(), "Offline Data");
                if (mArticlesArrayList.isEmpty()) {
                    Helper.printText(getApplicationContext(), "There is no thing to Show");
                }
                mArrayfromFB.addAll(mArticlesArrayList);

            }
        });

        /////////////////////
        if (((mArticlesArrayList).isEmpty()) && ((mArrayfromFB).isEmpty())) {
            Toast.makeText(this, "there is no thing to show", Toast.LENGTH_SHORT).show();
        } else {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RV);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
            newsAdapter = new NewsAdapter(this, mArrayfromFB);
            recyclerView.setAdapter(newsAdapter);
            newsAdapter.notifyDataSetChanged();

        }
        newsAdapter.notifyDataSetChanged();

    }

}
       /*if (mArticlesArrayList != null) {
                for (int ii = 0; ii < mArticlesArrayList.size(); ii++) {
                    if (mArticlesArrayList.get(ii).getAuthor().equals(user_id)) {
                        mArrayfromFB.add(mArticlesArrayList.get(ii));
                    }
                }
            }*/