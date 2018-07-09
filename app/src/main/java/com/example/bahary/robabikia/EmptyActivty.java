package com.example.bahary.robabikia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bahary.robabikia.utils.Constants;
import com.example.bahary.robabikia.utils.Helper;

public class EmptyActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptycontainer);
        Bundle bundle = getIntent().getExtras();
        String[] article = bundle.getStringArray(Constants.mBundlearticle);
        //Helper.printText(this ,article[1]);
        PostsFragment postsfragment=new PostsFragment();
        postsfragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.emptyContainer,postsfragment).commit();



    }
}
