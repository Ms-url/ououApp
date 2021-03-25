package com.example.oo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oo.R;
import com.example.oo.util.GlobalData;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mlayoutInflater;
    private Context mcontext;
    private List<GlobalData> mdata;
    private List<String> mpath;

    private final static int ITEM_HEADER = 0;
    private final static int ITEM_HEADER_2 = 1;
    private final static int ITEM_CONTENT = 2;
    private final static int ITEM_FOOT = 3;

    private int mHeader = 1;
    private int mChoice = 1;
    private int mfoot = 1;

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;

        public HeaderViewHolder(View view) {
            super(view);
            banner = view.findViewById(R.id.main_banner);
        }
    }

    static class ChoiceViewHolder extends RecyclerView.ViewHolder {
        public ChoiceViewHolder(View view) {
            super(view);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }


    public MainAdapter(List<GlobalData> mdata, List<String> mpath) {
        this.mdata = mdata;
        this.mpath = mpath;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
            mlayoutInflater = LayoutInflater.from(mcontext);
        }

        if (viewType == ITEM_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_banner, parent, false);
            final HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);


            return headerViewHolder;
        }
        if (viewType == ITEM_HEADER_2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_circle_choice, parent, false);
            final ChoiceViewHolder choiceViewHolder = new ChoiceViewHolder(view);


            return choiceViewHolder;
        }
        if (viewType == ITEM_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_body, parent, false);
            final ViewHolder viewHolder = new ViewHolder(view);


            return viewHolder;
        }
        if (viewType == ITEM_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_foot, parent, false);
            final FootViewHolder footViewHolder = new FootViewHolder(view);


            return footViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //   Data data = mdata.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            ((HeaderViewHolder) holder).banner.setImageLoader(new MyLoader());
            ((HeaderViewHolder) holder).banner.setImages(mpath).start();
        }
        if (holder instanceof ChoiceViewHolder) {

        }
        if (holder instanceof ViewHolder) {

        }
        if (holder instanceof FootViewHolder) {

        }
    }


    @Override
    public int getItemCount() {
        return mdata.size() + mHeader + mfoot+ mChoice;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != 0 && position < mHeader) {
            return ITEM_HEADER;
        }
        if (mChoice != 0 && position == mChoice) {
            return ITEM_HEADER_2;
        }

        if (mfoot != 0 && position >= mdata.size() + mHeader +1) {
            return ITEM_FOOT;
        }
        return ITEM_CONTENT;
    }

    public class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
