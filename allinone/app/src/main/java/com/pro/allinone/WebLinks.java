package com.pro.allinone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WebLinks extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private RecyclerView mRV;
    private FirestoreRecyclerAdapter adapter;
    TextView trackTV;
    FirestoreRecyclerOptions<WebModel> options;
    Query query;
    String mob = "", vNam = "", cdate = "";
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference ref = firebaseFirestore.collection("WebLinks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weblinks);

        // ((AppCompatActivity) this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        trackTV = (TextView) findViewById(R.id.trackTV);
        //trackTV.setText(" Today's token for you ...  "  + " / " + mob);

        cdate = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss", Locale.getDefault()).format(new Date());
        mRV = findViewById(R.id.recyclerView4);

        query = ref.whereEqualTo("isE", "1")
                .whereEqualTo("isD", "0");

        options = new FirestoreRecyclerOptions.Builder<WebModel>()
                .setQuery(query, WebModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<WebModel, ItemViewHolder>(options) {
            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_web, parent, false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull WebModel model) {
                //      holder.itemView.setBackgroundColor(Color.YELLOW);//bbefaa

                //holder.qIV.setImageResource(R.drawable.qm1);
                //holder.qIV.setImageResource(R.drawable.qm1);
                holder.type.setText(model.getType());
                holder.nam.setText(model.getNam());
                holder.link.setText(model.getLink());
                holder.act.setText(model.getAct());
                holder.cat.setText(model.getCat());
                holder.des.setText(model.getDes());

                //  trackTV.setText(" Today's Items: ");

            }
        };
        mRV.setHasFixedSize(true);
        // mRV.getItemDecorationCount();
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(adapter);
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TextView vName, address, dist, stime, state, mob;
        TextView nam, des, cat, link, act, type;
        ImageView qIV;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nam = (TextView) itemView.findViewById(R.id.usernameTV);
            cat = (TextView) itemView.findViewById(R.id.userphTV);
            act = (TextView) itemView.findViewById(R.id.timeTV);
            des = (TextView) itemView.findViewById(R.id.userstatusTV);
            link = (TextView) itemView.findViewById(R.id.hideTV);
            type = (TextView) itemView.findViewById(R.id.tokTV);
            qIV = (ImageView) itemView.findViewById(R.id.userIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (type.getText().toString().equals("1")) {

                Intent intent = new Intent(WebLinks.this, WebActivity1.class);
                intent.putExtra("tok", link.getText() + "");
                startActivity(intent);
            } else if (type.getText().toString().equals("2")) {
                Intent intent = new Intent(WebLinks.this, WebActivity2.class);
                intent.putExtra("tok", link.getText() + "");
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Sorry! its currently unavailable...", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void processsearch(String s) {
        Query q=firebaseFirestore.collection("WebLinks").whereEqualTo("isE","1")
                .whereEqualTo("isD","0")
                .orderBy("nam").
                        startAt(s).endAt(s+"\uf8ff");

        FirestoreRecyclerOptions<WebModel> op=new FirestoreRecyclerOptions.Builder<WebModel>()
                .setQuery(q,WebModel.class)
                .build();
        adapter=new FirestoreRecyclerAdapter<WebModel, ItemViewHolder>(op) {
            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_web,parent,false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull WebModel model) {
                holder.type.setText(model.getType());
                holder.nam.setText(model.getNam());
                holder.link.setText(model.getLink());
                holder.act.setText(model.getAct());
                holder.cat.setText(model.getCat());
                holder.des.setText(model.getDes());
            }
        };
        mRV.setHasFixedSize(true);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        mRV.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}


