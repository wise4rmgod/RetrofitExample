package com.example.wise4rmgod.retrofitexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wise4rmgod.retrofitexample.R;
import com.example.wise4rmgod.retrofitexample.model.Result;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Result> movies;
    public Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;



        public MyViewHolder(View view) {
            super(view);
            //initialize buttons and TextViews
            textView =  view.findViewById(R.id.textview);

        }
    }

    //constructor
    public ListAdapter(Context mContext, List<Result> movies) {
        this.movies = movies;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //set layout to itemView using Layout inflater
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofnames, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder  holder, final int position) {

        final Result moviesz = movies.get(position);
           holder.textView.setText(moviesz.getTitle());

        //Glide.with(mContext).load(moviesz.getPoster_path()).into(holder.img);
        // "http://i.imgur.com/DvpvklR.png"
        /** Picasso.get().load(moviesz.getPoster_path())
         .fit()
         .memoryPolicy(MemoryPolicy.NO_CACHE)
         .error(R.drawable.errorimage)
         .into(holder.img);
         // .memoryPolicy(MemoryPolicy.NO_CACHE)
         // .resize(500, 500)  **/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"its a testing project ",Toast.LENGTH_SHORT).show();

              /**  Intent i = new Intent(mContext, Result.class);
                //pass data though intent using puExtra
                i.putExtra("title", moviesz.getCreated_date());
                i.putExtra("overview",moviesz.getAbstract());
                i.putExtra("date", moviesz.getSection());
                i.putExtra("rating", moviesz.getItem_type());
                i.putExtra("imgurl",moviesz.getUrl());
                i.putExtra("id",moviesz.getTitle());
                mContext.startActivity(i);  **/

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
