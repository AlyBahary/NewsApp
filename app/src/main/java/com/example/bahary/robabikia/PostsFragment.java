package com.example.bahary.robabikia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahary.robabikia.model.Articles;
import com.example.bahary.robabikia.recyclerview.NewsAdapter;
import com.example.bahary.robabikia.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

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

    public PostsFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_post, container, false);
        ButterKnife.bind(this, view);
        Hawk.init(getContext()).build();
        //Bundle bundle = getActivity().getIntent().getExtras();
        //articles = bundle.getStringArray(Constants.mBundlearticle);
        articles = getArguments().getStringArray(Constants.mBundlearticle);
        String[] Date = articles[3].split("T");
        intitle.setText(articles[0] + "");
        indescription.setText(articles[1] + "");
        indate2.setText(Date[0] + "");
        indate1.setText(Date[1] + "");
        Picasso.with(getContext()).load(articles[4]).fit().placeholder(R.drawable.bg_spinner).into(inimage);
/////////////////////////////////
        if (Hawk.contains(Constants.mFavourit)) {
            articlesArrayList = Hawk.get(Constants.mFavourit);
            for (int i = 0; i < articlesArrayList.size(); i++) {
                if ((articlesArrayList.get(i).getTitle()).equals(articles[0])) {
                    instar.setVisibility(View.GONE);
                }
            }
//////////////////////////////////
        }

        instar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!Hawk.contains(Constants.mFavourit))) {

                } else {
                    articlesArrayList = Hawk.get(Constants.mFavourit);

                }
                mAuth = FirebaseAuth.getInstance();
                String user_id = mAuth.getCurrentUser().getUid();
                Articles article = new Articles();
                article.setTitle(articles[0]);
                article.setDescription(articles[1]);
                article.setPublishedAt(articles[3]);
                article.setUrlToImage(articles[4]);
                article.setAuthor(Hawk.get(Constants.mEmail_Key)+"");
                Log.i("ay", user_id);

                articlesArrayList.add(article);
                Hawk.put(Constants.mFavourit, articlesArrayList);
                Toast.makeText(getContext(), "it's Added to your favourit list ", Toast.LENGTH_SHORT).show();
                String substring=articles[0].replaceAll(".,'-_;>,<:","w");
                DatabaseReference current_user_db = FirebaseDatabase.getInstance()
                        .getReference().child(user_id).child("post").push();
                HashMap map = new HashMap();
                map.put("title", (articles[0]));
                map.put("description", (articles[1]));
                map.put("publishedAt", (articles[3]));
                map.put("urlToImage", (articles[4]));
                current_user_db.setValue(map);
                instar.setVisibility(View.GONE);
            }
        });


        return view;

    }

}
