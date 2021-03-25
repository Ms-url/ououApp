package com.example.oo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oo.util.GlobalData;
import com.example.oo.R;

import java.util.List;


public class CommonsAdapter extends RecyclerView.Adapter<CommonsAdapter.ViewHolder> {
    private List<GlobalData> mdata;
    private Context mcontext;


    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public CommonsAdapter(List<GlobalData> mdata) {
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_body, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        if (mcontext == null) {
            mcontext = parent.getContext();
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

     //   Glide.with(mcontext).load(usefulData.getCollect()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}
